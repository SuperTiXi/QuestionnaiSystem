package com.neu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.bean.HttpResponseEntity;
import com.neu.dao.entity.Group;

public interface GroupService extends IService<Group> {

    HttpResponseEntity queryAllGroup(String userId);

    HttpResponseEntity addGroup(Group group, String userId);

    HttpResponseEntity delete(String groupId);

    HttpResponseEntity recover(String groupId);

    HttpResponseEntity modify(Group group);
}
