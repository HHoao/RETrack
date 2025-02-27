package com.rare_earth_track.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.rare_earth_track.admin.bean.MailType;
import com.rare_earth_track.admin.bean.PageInfo;
import com.rare_earth_track.admin.bean.RetFactoryParam;
import com.rare_earth_track.admin.bean.RetMemberParam;
import com.rare_earth_track.admin.service.*;
import com.rare_earth_track.common.exception.Asserts;
import com.rare_earth_track.mgb.mapper.RetFactoryMapper;
import com.rare_earth_track.mgb.model.*;
import com.rare_earth_track.security.util.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hhoa
 * @date 2022/5/24
 **/
@Service
@RequiredArgsConstructor
public class RetFactoryServiceImpl implements RetFactoryService {
    private final RetFactoryMapper factoryMapper;
    private final RetMemberService memberService;

    private final RetProductService productService;
    private final RetFactoryUserRelationService factoryUserRelationService;

    @Override
    public List<RetFactory> list(PageInfo pageInfo, RetFactory factory) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        return getFactories(factory);
    }

    @Override
    public Long addFactory(RetFactoryParam factoryParam) {
        RetFactory newFactory = new RetFactory();
        BeanUtil.copyProperties(factoryParam, newFactory);
        int insert = factoryMapper.insert(newFactory);
        if (insert == 0){
            Asserts.fail("插入失败");
        }
        return newFactory.getId();
    }

    @Override
    public void updateFactory(String factoryName, RetFactoryParam factoryParam) {
        RetFactory factoryByFactoryName = getFactoryByFactoryName(factoryName);
        RetFactory newFactory = new RetFactory();
        BeanUtil.copyProperties(factoryParam, newFactory);
        newFactory.setId(factoryByFactoryName.getId());
        int i = factoryMapper.updateByPrimaryKeySelective(newFactory);
        if (i == 0){
            Asserts.fail("更新失败");
        }
    }

    @Override
    public int deleteFactoryByName(String name) {
        RetFactory factoryByFactoryName = getFactoryByFactoryName(name);
        //删除工厂成员
        RetMember member = new RetMember();
        member.setFactoryId(factoryByFactoryName.getId());
        memberService.deleteMembers(member);
        //删除工厂产品
        RetProduct product = new RetProduct();
        product.setFactoryId(factoryByFactoryName.getId());
        productService.deleteProducts(product);
        //删除工厂
        int i = factoryMapper.deleteByPrimaryKey(factoryByFactoryName.getId());
        if (i == 0){
            Asserts.fail("删除失败");
        }
        return i;
    }

    @Override
    public RetFactory getFactoryByFactoryName(String name) {
        RetFactoryExample factoryExample = new RetFactoryExample();
        factoryExample.createCriteria().andNameEqualTo(name);
        List<RetFactory> retFactories = factoryMapper.selectByExample(factoryExample);
        if (retFactories == null || retFactories.size() == 0) {
            Asserts.fail("没有该工厂");
        }
        return retFactories.get(0);
    }

    @Override
    public RetFactory getFactoryByFactoryId(Long id) {
        RetFactory retFactory = factoryMapper.selectByPrimaryKey(id);
        if (retFactory == null){
            Asserts.fail("没有该工厂");
        }
        return retFactory;
    }

    @Override
    public void deleteFactoryMemberByUsername(String factoryName, String username) {
        RetMember member = new RetMember();
        RetFactory factoryByFactoryName = getFactoryByFactoryName(factoryName);
        Long userIdByUsername = factoryUserRelationService.getUserIdByUsername(username);
        member.setUserId(userIdByUsername);
        member.setFactoryId(factoryByFactoryName.getId());
        memberService.deleteMembers(member);
    }

    @Override
    public Long addFactoryMember(Long factoryId, Long userId) {
        RetMember member =new RetMember();
        member.setFactoryId(factoryId);
        member.setUserId(userId);
        return memberService.addMember(member);
    }
    @Override
    public void updateFactoryMember(String factoryName, String username, RetMemberParam memberParam) {
        RetFactory factoryByFactoryName = getFactoryByFactoryName(factoryName);
        Long userIdByUsername = factoryUserRelationService.getUserIdByUsername(username);
        memberService.updateMember(factoryByFactoryName.getId(), userIdByUsername, memberParam);
    }

    @Override
    public List<RetMember> listFactoryMembers(PageInfo pageInfo, String factoryName) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        RetFactory factoryByFactoryName = getFactoryByFactoryName(factoryName);
        return memberService.getFactoryMembers(factoryByFactoryName.getId());
    }

    private RetFactoryExample getFactoryExample(RetFactory factory){
        RetFactoryExample factoryExample = new RetFactoryExample();
        RetFactoryExample.Criteria criteria = factoryExample.createCriteria();
        if (factory.getId() != null){
            criteria.andIdEqualTo(factory.getId());
            return factoryExample;
        }
        if (factory.getEmail() != null){
            criteria.andEmailEqualTo(factory.getEmail());
            return factoryExample;
        }
        if (factory.getAddress() != null){
            criteria.andAddressLike(factory.getAddress());
        }
        if (factory.getName() != null){
            criteria.andNameLike(factory.getName());
            return factoryExample;
        }
        if (factory.getPhone() != null){
            criteria.andPhoneEqualTo(factory.getPhone());
            return factoryExample;
        }
        if (factory.getDescription() != null){
            criteria.andDescriptionLike(factory.getDescription());
        }
        return factoryExample;
    }

    @Override
    public List<RetFactory> getFactories(RetFactory factory) {
        RetFactoryExample factoryExample = getFactoryExample(factory);
        return factoryMapper.selectByExample(factoryExample);
    }

    @Override
    public List<RetProduct> listProducts(String factoryName, RetProduct product, PageInfo pageInfo) {
        RetFactory factory = getFactoryByFactoryName(factoryName);
        product.setFactoryId(factory.getId());
        return productService.getProducts(product, pageInfo);
    }
}
