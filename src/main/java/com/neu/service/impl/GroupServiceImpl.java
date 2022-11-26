package com.neu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.bean.HttpResponseEntity;
import com.neu.common.utils.UUIDUtil;
import com.neu.dao.GroupMapper;
import com.neu.dao.GroupToAnswererMapper;
import com.neu.dao.TenantToUserMapper;
import com.neu.dao.UserToGroupMapper;
import com.neu.dao.entity.Account;
import com.neu.dao.entity.Group;
import com.neu.dao.entity.GroupToAnswerer;
import com.neu.dao.entity.UserToGroup;
import com.neu.service.GroupService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.neu.common.Constants.*;
import static com.neu.common.Constants.INSERT_SUCCESS_MESSAGE;

@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {

    @Autowired
    UserToGroupMapper userToGroupMapper;

    @Autowired
    TenantToUserMapper tenantToUserMapper;

    @Autowired
    GroupToAnswererMapper groupToAnswererMapper;

    @Override
    public HttpResponseEntity queryAllGroup(String userId) {

        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        List<String> groupIds = userToGroupMapper.queryGroupByUser(userId);
        if(groupIds.isEmpty()){
            httpResponseEntity.setCode(QUERY_FAIL_CODE);
            httpResponseEntity.setMessage(QUERY_FAIL_MESSAGE);

            return httpResponseEntity;
        }

        List<Group> groups = new ArrayList<>();
        for(String groupId : groupIds){
            if(!StrUtil.isBlank(userId)){
                Group group = query().eq("id", groupId).one();
                groups.add(group);
            }

        }

        httpResponseEntity.setCode(QUERY_SUCCESS_CODE);
        httpResponseEntity.setMessage(QUERY_SUCCESS_MESSAGE);
        httpResponseEntity.setData(groups);

        return httpResponseEntity;

    }

    @Override
    public HttpResponseEntity addGroup(@NotNull Group group, String userId) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        String current = DateUtil.now();
        String tenantId = tenantToUserMapper.queryTenantByUser(userId);
        group.setCreateTime(current);
        group.setId(UUIDUtil.getOneUUID());
        group.setState(1);
        group.setTenantId(tenantId);

        UserToGroup userToGroup = new UserToGroup(userId, group.getId());
        userToGroupMapper.insert(userToGroup);

        try {

            save(group);
        } catch (Exception e) {
            e.printStackTrace();
            httpResponseEntity.setCode(INSERT_FAIL_CODE);
            httpResponseEntity.setMessage(INSERT_FAIL_MESSAGE);
            return httpResponseEntity;
        }

        httpResponseEntity.setCode(INSERT_SUCCESS_CODE);
        httpResponseEntity.setMessage(INSERT_SUCCESS_MESSAGE);

        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity delete(String groupId) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        if(StrUtil.isBlank(groupId)){
            httpResponseEntity.setCode(DELETE_FAIL_CODE);
            httpResponseEntity.setMessage(DELETE_FAIL_MESSAGE);
            return httpResponseEntity;
        }
        UpdateWrapper<Group> updateWrapper = new UpdateWrapper<>();
        try {
            updateWrapper.eq("id",groupId);
            updateWrapper.set("state",0);
            update(updateWrapper);
        } catch (Exception e) {
            httpResponseEntity.setCode(DELETE_FAIL_CODE);
            httpResponseEntity.setMessage(DELETE_FAIL_MESSAGE);
            return httpResponseEntity;
        }

        httpResponseEntity.setCode(DELETE_SUCCESS_CODE);
        httpResponseEntity.setMessage(DELETE_SUCCESS_MESSAGE);
        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity recover(String groupId) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        if(StrUtil.isBlank(groupId)){
            httpResponseEntity.setCode(RECOVER_FAIL_CODE);
            httpResponseEntity.setMessage(RECOVER_FAIL_MESSAGE);
            return httpResponseEntity;
        }

        UpdateWrapper<Group> updateWrapper = new UpdateWrapper<>();
        try {
            updateWrapper.eq("id",groupId);
            updateWrapper.set("state",1);
            update(updateWrapper);

        } catch (Exception e) {
            httpResponseEntity.setCode(RECOVER_FAIL_CODE);
            httpResponseEntity.setMessage(RECOVER_FAIL_MESSAGE);
            return httpResponseEntity;
        }

        httpResponseEntity.setCode(RECOVER_SUCCESS_CODE);
        httpResponseEntity.setMessage(RECOVER_SUCCESS_MESSAGE);
        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity modify(@NotNull Group group) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        UpdateWrapper<Group> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",group.getId());

        boolean update = update(group, updateWrapper);

        if(!update){
            httpResponseEntity.setCode(MODIFY_FAIL_CODE);
            httpResponseEntity.setMessage(MODIFY_FAIL_MESSAGE);

            return  httpResponseEntity;
        }

        httpResponseEntity.setCode(MODIFY_SUCCESS_CODE);
        httpResponseEntity.setMessage(MODIFY_SUCCESS_MESSAGE);

        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity addAnswererToGroup(String answererId, String groupId) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        GroupToAnswerer groupToAnswerer = new GroupToAnswerer(groupId, answererId);
        try {

            groupToAnswererMapper.insert(groupToAnswerer);
        } catch (Exception e) {

            httpResponseEntity.setCode(INSERT_FAIL_CODE);
            httpResponseEntity.setMessage(INSERT_FAIL_MESSAGE);
            return httpResponseEntity;
        }

        httpResponseEntity.setCode(INSERT_SUCCESS_CODE);
        httpResponseEntity.setMessage(INSERT_SUCCESS_MESSAGE);

        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity removeAnswererFromGroup(String answererId, String groupId) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        try {
            groupToAnswererMapper.removeAnswererFromGroup(answererId, groupId);
        } catch (Exception e) {
            e.printStackTrace();
            httpResponseEntity.setCode(DELETE_FAIL_CODE);
            httpResponseEntity.setMessage(DELETE_FAIL_MESSAGE);
            return httpResponseEntity;
        }

        httpResponseEntity.setCode(DELETE_SUCCESS_CODE);
        httpResponseEntity.setMessage(DELETE_SUCCESS_MESSAGE);

        return httpResponseEntity;
    }
}
