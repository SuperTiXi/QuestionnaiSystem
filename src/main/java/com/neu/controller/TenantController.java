package com.neu.controller;

import com.neu.bean.HttpResponseEntity;
import com.neu.dao.entity.Account;
import com.neu.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
     * @param map 需要填写userName,name,phone,password
     * @return 添加状态
     */
    @RequestMapping(value = "/insert",method = RequestMethod.POST,headers = "Accept=Application/json")
    public HttpResponseEntity addUser(@RequestBody Map<String,Object> map){
        String tenantId = (String) map.get("tenantId");
        Account account = new Account();
        account.setUserName((String) map.get("username"));
        account.setName((String) map.get("name"));
        account.setPassword((String) map.get("password"));
        account.setPhone((String) map.get("phone"));
        account.setInfo((String) map.get("info"));
        return accountService.addUser(account,tenantId);
    }

    /**
     * 停用用户账户
     * @return 状态
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity delete(@RequestBody Map<String,Object> map){
        String userName = (String) map.get("userName");
        String phone = (String) map.get("phone");

        return accountService.delete(userName,phone);
    }

    /**
     * 启用用户账户
     * @param map
     * @return 状态
     */
    @RequestMapping(value = "/recover",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity recover(@RequestBody Map<String,Object> map){
        String userName = (String) map.get("userName");
        String phone = (String) map.get("phone");
        return accountService.recover(userName,phone);
    }

    /**
     * 修改用户信息
     * @param account 修改后的租户信息，要求：userName不能改
     * @return 修改状态
     */
    @RequestMapping(value = "/modify",method = RequestMethod.POST,headers = "Accept=application/json")
    public HttpResponseEntity modify(@RequestBody Account account){

        return accountService.modify(account);
    }

    /**
     * 租户进行支付
     * @param tenantId 租户id
     * @param money 付款数目
     * @return 付款状态
     */
    @RequestMapping(value = "/pay",method = RequestMethod.GET,headers = "Accept=application/json")
    public HttpResponseEntity pay(@RequestParam("tenantId") String tenantId,@RequestParam("money") double money){

        return accountService.pay(tenantId,money);
    }
}
