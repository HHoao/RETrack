package com.rare_earth_track.portal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.rare_earth_track.common.exception.Asserts;
import com.rare_earth_track.mgb.mapper.RetPermissionMapper;
import com.rare_earth_track.mgb.model.RetPermission;
import com.rare_earth_track.mgb.model.RetPermissionExample;
import com.rare_earth_track.portal.bean.RetPermissionParam;
import com.rare_earth_track.portal.service.RetMemberJobPermissionRelationService;
import com.rare_earth_track.portal.service.RetPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hhoa
 * @date 2022/5/26
 **/

@Service
@RequiredArgsConstructor
public class RetPermissionServiceImpl implements RetPermissionService {
    private final RetPermissionMapper permissionMapper;
    private final RetMemberJobPermissionRelationService memberJobPermissionRelationService;

    @Override
    public RetPermission getPermission(String permissionName) {
        RetPermissionExample permissionExample = new RetPermissionExample();
        permissionExample.createCriteria().andNameEqualTo(permissionName);
        List<RetPermission> permissions = permissionMapper.selectByExample(permissionExample);
        if (permissions != null && permissions.size() > 0){
            return permissions.get(0);
        }
        return null;
    }

    @Override
    public List<RetPermission> getAllPermissions() {
        return permissionMapper.selectByExample(new RetPermissionExample());
    }

    @Override
    public List<RetPermission> list(Integer from, Integer size) {
        PageHelper.startPage(from, size);
        return getAllPermissions();
    }

    @Override
    public RetPermission getPermission(Long permissionId) {
        RetPermission permission = permissionMapper.selectByPrimaryKey(permissionId);
        if (permission == null){
            Asserts.fail("没有该权限");
        }
        return permission;
    }



    @Override
    public Long addPermission(RetPermissionParam permissionParam) {
        RetPermission permission = new RetPermission();
        BeanUtil.copyProperties(permissionParam, permission);
        int insert = permissionMapper.insertSelective(permission);
        if (insert == 0){
            Asserts.fail("插入权限失败");
        }
        return permission.getId();
    }

    @Override
    public int updatePermission(String permissionName, RetPermissionParam permissionParam) {
        RetPermission permissionByName = getPermission(permissionName);
        RetPermission newPermission = new RetPermission();
        if (permissionByName == null){
            Asserts.fail("没有该权限");
        }
        BeanUtils.copyProperties(permissionParam, newPermission);
        newPermission.setId(permissionByName.getId());
        int successCount = permissionMapper.updateByPrimaryKeySelective(newPermission);
        if (successCount == 0){
            Asserts.fail("更新失败");
        }
        return  successCount;
    }
    @Override
    public int deletePermission(Long permissionId) {
        RetPermission permission = permissionMapper.selectByPrimaryKey(permissionId);
        if (permission == null){
            Asserts.fail("没有该权限");
        }
        memberJobPermissionRelationService.deleteMemberPermission(permissionId);
        return permissionMapper.deleteByPrimaryKey(permissionId);
    }
}
