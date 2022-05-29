package com.rare_earth_track.admin.service.impl;

import com.rare_earth_track.admin.bean.IdentifyType;
import com.rare_earth_track.admin.bean.RetUserUpdatePasswordByAuthCodeParam;
import com.rare_earth_track.admin.service.RetUserAuthService;
import com.rare_earth_track.common.exception.Asserts;
import com.rare_earth_track.mgb.mapper.RetUserAuthMapper;
import com.rare_earth_track.mgb.model.RetUserAuth;
import com.rare_earth_track.mgb.model.RetUserAuthExample;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hhoa
 * @date 2022/5/29
 **/
@Service
@RequiredArgsConstructor
public class RetUserAuthServiceImpl implements RetUserAuthService {
    private final RetUserAuthMapper userAuthMapper;
    @Override
    public List<RetUserAuth> getUserAuth(Long userId){
        RetUserAuthExample userAuthExample = new RetUserAuthExample();
        userAuthExample.createCriteria().
                andUserIdEqualTo(userId).
                andIdentityTypeEqualTo(IdentifyType.USERNAME.value());
        List<RetUserAuth> retUserAuths = userAuthMapper.selectByExample(userAuthExample);
        if (retUserAuths == null || retUserAuths.size() == 0){
            Asserts.fail("没有该验证方式");
        }
        return retUserAuths;
    }
    @Override
    public RetUserAuth getUserAuth(Long userId, IdentifyType identifyType) {
        RetUserAuthExample userAuthExample = new RetUserAuthExample();
        userAuthExample.createCriteria().
                andUserIdEqualTo(userId).
                andIdentityTypeEqualTo(identifyType.value());
        List<RetUserAuth> retUserAuths = userAuthMapper.selectByExample(userAuthExample);
        if (retUserAuths == null || retUserAuths.size() == 0){
            Asserts.fail("没有该验证方式");
        }
        return retUserAuths.get(0);
    }

    @Override
    public RetUserAuth getUserAuth(IdentifyType identifyType, String identifier) {
        RetUserAuthExample userAuthExample = new RetUserAuthExample();
        userAuthExample.createCriteria().
                andIdentifierEqualTo(identifier).
                andIdentityTypeEqualTo(identifyType.value());
        List<RetUserAuth> retUserAuths = userAuthMapper.selectByExample(userAuthExample);
        if (retUserAuths == null || retUserAuths.size() == 0){
            Asserts.fail("没有该用户");
        }
        return retUserAuths.get(0);
    }

    @Override
    public boolean exists(IdentifyType identifyType, String identifier){
        RetUserAuthExample userAuthExample = new RetUserAuthExample();
        userAuthExample.createCriteria().
                andIdentityTypeEqualTo(identifyType.value()).
                andIdentifierEqualTo(identifier);
        List<RetUserAuth> retUserAuths = userAuthMapper.selectByExample(userAuthExample);
        return retUserAuths != null && retUserAuths.size() != 0;
    }

    @Override
    public void bind(Long id, IdentifyType identifyType) {
        RetUserAuth userAuth = new RetUserAuth();
        userAuth.setUserId(id);
        userAuth.setIdentityType(identifyType.value());
        userAuthMapper.insert(userAuth);
    }


    private void updateCredential(Long userId, String credential){
        List<RetUserAuth> userAuths = getUserAuth(userId);
        for (RetUserAuth userAuth : userAuths){
            userAuth.setCredential(credential);
            int i = userAuthMapper.updateByPrimaryKey(userAuth);
            if (i == 0){
                Asserts.fail("更新失败");
            }
        }
    }

    @Override
    public void updateCredential(RetUserUpdatePasswordByAuthCodeParam passwordParam) {
        switch (passwordParam.getIdentifyType()) {
            case EMAIL -> {
                RetUserAuthExample userAuthExample = new RetUserAuthExample();
                userAuthExample.createCriteria().
                        andIdentityTypeEqualTo(passwordParam.getIdentifyType().value()).
                        andIdentifierEqualTo(passwordParam.getIdentifier());
                List<RetUserAuth> retUserAuths = userAuthMapper.selectByExample(userAuthExample);
                if (retUserAuths == null || retUserAuths.size() == 0){
                    Asserts.fail("没有该邮箱或者密码");
                }
                RetUserAuth userAuth = retUserAuths.get(0);
                updateCredential(userAuth.getUserId(), passwordParam.getPassword());
            }
            case PHONE -> {
            }
        }
    }

    @Override
    public void deleteUserAuth(Long userId) {
        RetUserAuthExample userAuthExample = new RetUserAuthExample();
        userAuthExample.createCriteria().
                andUserIdEqualTo(userId);
        List<RetUserAuth> retUserAuths = userAuthMapper.selectByExample(userAuthExample);
        for (RetUserAuth userAuth : retUserAuths){
            userAuthMapper.deleteByPrimaryKey(userAuth.getId());
        }
    }

    @Override
    public RetUserAuth getUserAuth(String identifier, String credential) {
        RetUserAuthExample userAuthExample = new RetUserAuthExample();
        userAuthExample.createCriteria().
                andIdentifierEqualTo(identifier).
                andCredentialEqualTo(credential);
        List<RetUserAuth> retUserAuths = userAuthMapper.selectByExample(userAuthExample);
        if (retUserAuths == null || retUserAuths.size() == 0){
            Asserts.fail("没有该验证方式");
        }
        if (retUserAuths.size() > 1){
            Asserts.fail("有多个验证方式");
        }
        return retUserAuths.get(0);
    }
}
