package com.neu.controller;

import com.neu.bean.HttpResponseEntity;
import com.neu.dao.entity.Account;
import com.neu.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 租户对用户进行管理
 */
@RestController
@RequestMapping("/tenant")
public class TenantController {

    @Autowired
    private AccountService accountService;

    /**
     * 查询用户列表
     * @param tenantId 该租户id
     * @return 返回list类型
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET,headers = "Accept=Application/json")
    public HttpResponseEntity queryAllUser(@RequestParam("tenantId") String tenantId){

        return accountService.queryAllUser(tenantId);
    }

    /**
     * 添加用户
     * @param account 需要填写userName,name,phone,password
     * @return 添加状态
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST,headers = "Accept=Application/json")
    public HttpResponseEntity addUser(@RequestBody Account account,@RequestParam("tenantId") String tenantId){

        return accountService.addUser(account,tenantId);
    }

    /**
     * 停用租户账户
     * @param userName 账户名
     * @param phone 手机号
     * @return 状态
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity delete(@RequestParam("userName") String userName, @RequestParam("phone") String phone){

        return accountService.delete(userName,phone);
    }

    /**
     * 启用租户账户
     * @param userName 账户名
     * @param phone 手机号
     * @return 状态
     */
    @RequestMapping(value = "/recover",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity recover(@RequestParam("userName") String userName, @RequestParam("phone") String phone){

        return accountService.recover(userName,phone);
    }

    /**
     * 修改租户信息
     * @param account 修改后的租户信息，要求：userName不能改
     * @return 修改状态
     */
    @RequestMapping(value = "/modify",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity modify(@RequestBody Account account){

        return accountService.modify(account);
    }
}
