package com.neu.controller;

import com.neu.bean.HttpResponseEntity;
import com.neu.dao.entity.Group;
import com.neu.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户对所创建的群组进行管理
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    GroupService groupService;

    /**
     * 查询该用户下所有组
     * @param userId 该用户id
     * @return list
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET,headers = "Accept=Application/json")
    public HttpResponseEntity queryAllGroup(@RequestParam("userId") String userId){

        return groupService.queryAllGroup(userId);
    }


    /**
     * 添加群组
     * @param group 群组信息：name,description,userId
     * @param userId 用户Id
     * @return 插入状态
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST,headers = "Accept=Application/json")
    public HttpResponseEntity addGroup(@RequestBody Map<String,Object> map){
        Group group = new Group();
        group.setName((String) map.get("name"));
        group.setDescription((String) map.get("description"));
        String userId = (String) map.get("userId");
        return groupService.addGroup(group,userId);
    }


    /**
     *  停用群组
     * @param groupId 群组id
     * @return 状态
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity delete(@RequestParam("groupId") String groupId){

        return groupService.delete(groupId);
    }

    /**
     * 启用群组
     * @param groupId id
     * @return 状态
     */
    @RequestMapping(value = "/recover",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity recover(@RequestParam("groupId") String groupId){

        return groupService.recover(groupId);
    }

    /**
     * 修改群组信息
     * @param group 修改后的租户信息，只需要带id,name,description三个参数
     * @return 修改状态
     */
    @RequestMapping(value = "/modify",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity modify(@RequestBody Group group){

        return groupService.modify(group);
    }
}
