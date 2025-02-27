package com.rare_earth_track.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.rare_earth_track.admin.bean.PageInfo;
import com.rare_earth_track.admin.bean.RetFactoryJob;
import com.rare_earth_track.admin.bean.RetMemberParam;
import com.rare_earth_track.admin.service.RetFactoryService;
import com.rare_earth_track.admin.service.RetMemberJobService;
import com.rare_earth_track.admin.service.RetMemberService;
import com.rare_earth_track.common.exception.Asserts;
import com.rare_earth_track.mgb.mapper.RetMemberMapper;
import com.rare_earth_track.mgb.model.RetFactory;
import com.rare_earth_track.mgb.model.RetMember;
import com.rare_earth_track.mgb.model.RetMemberExample;
import com.rare_earth_track.mgb.model.RetMemberJob;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hhoa
 * @date 2022/5/24
 **/

@Service
public class RetMemberServiceImpl implements RetMemberService {
    private final RetMemberMapper memberMapper;
    private final RetFactoryService factoryService;

    @Lazy
    public RetMemberServiceImpl(RetMemberMapper memberMapper, RetFactoryService factoryService, RetMemberJobService memberJobService) {
        this.memberMapper = memberMapper;
        this.factoryService = factoryService;
        this.memberJobService = memberJobService;
    }

    private final RetMemberJobService memberJobService;

    private RetMemberExample getMemberExample(RetMember member){
        RetMemberExample memberExample = new RetMemberExample();
        if (member != null) {
            RetMemberExample.Criteria criteria = memberExample.createCriteria();
            if (member.getPhone() != null){
                criteria.andPhoneEqualTo(member.getPhone());
            }
            if (member.getId() != null){
                criteria.andIdEqualTo(member.getId());
            }
            if (member.getNickname() != null){
                criteria.andNicknameLike(member.getNickname());
            }
            if (member.getJobId() != null){
                criteria.andJobIdEqualTo(member.getJobId());
            }
            if (member.getFactoryId() != null){
                criteria.andFactoryIdEqualTo(member.getFactoryId());
            }
            if (member.getUserId() != null){
                criteria.andUserIdEqualTo(member.getUserId());
            }
        }
        return memberExample;
    }
    @Override
    public List<RetMember> list(PageInfo pageInfo, RetMember member) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        RetMemberExample memberExample = getMemberExample(member);
        return memberMapper.selectByExample(memberExample);
    }

    @Override
    public Long addMember(RetMember member) {
        int insert = memberMapper.insertSelective(member);
        if (insert == 0){
            Asserts.fail("插入失败");
        }
        return member.getId();
    }


    @Override
    public void deleteMember(Long memberId) {
        int i = memberMapper.deleteByPrimaryKey(memberId);
        if (i == 0){
            Asserts.fail("删除失败");
        }
    }
    @Override
    public RetMember getMember(Long memberId) {
        RetMember retMember = memberMapper.selectByPrimaryKey(memberId);
        if (retMember == null){
            Asserts.fail("没有该成员");
        }
        return retMember;
    }

    @Override
    public void updateMember(Long factoryId, Long userId, RetMemberParam memberParam) {
        RetMember member = getMember(factoryId, userId);
        member.setJobId(memberParam.getJobId());
        member.setNickname(memberParam.getNickname());
        member.setPhone(memberParam.getPhone());
        int i = memberMapper.updateByPrimaryKeySelective(member);
        if (i == 0){
            Asserts.fail("修改失败");
        }
    }

    @Override
    public List<RetMember> getFactoryMembers(Long factoryId) {
        RetMemberExample memberExample = new RetMemberExample();
        memberExample.createCriteria().andFactoryIdEqualTo(factoryId);
        return memberMapper.selectByExample(memberExample);
    }

    @Override
    public List<RetFactoryJob> getFactoryJobsByUserId(Long userId) {
        RetMemberExample memberExample = new RetMemberExample();
        memberExample.createCriteria().
                andUserIdEqualTo(userId);
        List<RetMember> members = memberMapper.selectByExample(memberExample);
        List<RetFactoryJob> factoryJobs = new ArrayList<>();
        for (RetMember member : members){
            RetFactory factoryByFactoryId = factoryService.getFactoryByFactoryId(member.getFactoryId());
            RetMemberJob job = memberJobService.getJob(member.getJobId());
            RetFactoryJob factoryJob = new RetFactoryJob(factoryByFactoryId.getName(), job.getName());
            factoryJobs.add(factoryJob);
        }
        return factoryJobs;
    }

    @Override
    public void deleteMembersByUserId(Long userId) {
        RetMemberExample memberExample = new RetMemberExample();
        memberExample.createCriteria().andUserIdEqualTo(userId);
        List<RetMember> members = memberMapper.selectByExample(memberExample);
        for (RetMember member : members){
            int i = memberMapper.deleteByPrimaryKey(member.getId());
            if (i == 0){
                Asserts.fail("删除失败");
            }
        }
    }

    @Override
    public List<RetMember> getMember(RetMember member){
        RetMemberExample memberExample = getMemberExample(member);
        return memberMapper.selectByExample(memberExample);
    }
    @Override
    public void deleteMembers(RetMember member){
        RetMemberExample memberExample = getMemberExample(member);
        int i = memberMapper.deleteByExample(memberExample);
        if (i == 0){
            Asserts.fail("删除失败");
        }
    }

    @Override
    public RetMember getMember(Long factoryId, Long userId){
        RetMemberExample memberExample = new RetMemberExample();
        memberExample.createCriteria().
                andFactoryIdEqualTo(factoryId).
                andUserIdEqualTo(userId);
        List<RetMember> members = memberMapper.selectByExample(memberExample);
        if (members.size() == 0){
            Asserts.fail("没有该成员");
        }
        return members.get(0);
    }
    @Override
    public void deleteMember(Long factoryId, Long userId) {
        RetMember member = getMember(factoryId, userId);
        deleteMember(member.getId());
    }
}
