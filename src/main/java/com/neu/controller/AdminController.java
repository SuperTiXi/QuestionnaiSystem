package com.neu.controller;

import com.neu.bean.HttpResponseEntity;
import com.neu.dao.entity.Account;
import com.neu.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AccountService accountService;

    /**
     * 查询数据库中的租户
     * @return 查询状态
     * 返回的数据是list类型，我建议使用jQuery中的dataTable插件来实现分页及查询功能
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET,headers = "Accept=application/json")
    public HttpResponseEntity queryAllTenant(){

        return accountService.queryAllTenant();
    }

    /**
     * 管理员添加用户
     * @param account 填写的信息，包括username，name，password，phone
     * @return  添加状态
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity addTenant(@RequestBody Account account){

        return accountService.addTenant(account);
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
}
