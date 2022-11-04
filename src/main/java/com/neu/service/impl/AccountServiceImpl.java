package com.neu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.bean.HttpResponseEntity;
import com.neu.common.utils.*;
import com.neu.dao.AccountMapper;
import com.neu.dao.TenantToUserMapper;
import com.neu.dao.UserToGroupMapper;
import com.neu.dao.entity.Account;
import com.neu.dao.entity.Group;
import com.neu.dao.entity.TenantToUser;
import com.neu.dao.entity.UserToGroup;
import com.neu.service.AccountService;
import com.neu.service.GroupService;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.neu.common.Constants.*;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    TenantToUserMapper tenantToUserMapper;

    @Autowired
    UserToGroupMapper userToGroupMapper;

    @Autowired
    GroupService groupService;
    @Override
    public HttpResponseEntity loginByUserName(String userName, String password) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        if (CommonUtils.stringIsEmpty(userName)||CommonUtils.stringIsEmpty(password)) {
            httpResponseEntity.setCode(LOGIN_FAIL_CODE);
            httpResponseEntity.setMessage(EMPTY_ERROR);
            return httpResponseEntity;
        }
        Account account = query().eq("user_name", userName).eq("password", password).eq("state",1).one();

        if(BeanUtil.isEmpty(account)){
            httpResponseEntity.setCode(LOGIN_FAIL_CODE);
            httpResponseEntity.setMessage(LOGIN_FAIL_MESSAGE);
            return httpResponseEntity;
        }

        httpResponseEntity.setCode(LOGIN_SUCCESS_CODE);
        httpResponseEntity.setMessage(LOGIN_SUCCESS_MESSAGE);
        httpResponseEntity.setData(account);
        return httpResponseEntity;
    }


    @Override
    public HttpResponseEntity sendCode(String phone) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        String code = RandomUtil.randomNumbers(6);

        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + phone, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);

        log.error("验证码为"+code);

        if (RegexUtils.isPhoneInvalid(phone)) {
            // 如果不符合，返回错误信息
            httpResponseEntity.setCode(LOGIN_FAIL_CODE);
            httpResponseEntity.setMessage(PHONE_ERROR);
            return httpResponseEntity;
        }

        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "2b09f40137ca41968285e0a8c5d0bd13";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phone);
        querys.put("param", "**code**:"+code+",**minute**:2");
        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            httpResponseEntity.setCode(CODE_SEND_FAIL_CODE);
            httpResponseEntity.setMessage(CODE_SEND_FAIL_MESSAGE);

            return httpResponseEntity;
        }

        httpResponseEntity.setCode(CODE_SEND_SUCCESS_CODE);
        httpResponseEntity.setMessage(CODE_SEND_SUCCESS_MESSAGE);

        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity loginByPhone(String phone ,String code) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        if (CommonUtils.stringIsEmpty(phone)||CommonUtils.stringIsEmpty(code)) {
            httpResponseEntity.setCode(LOGIN_FAIL_CODE);
            httpResponseEntity.setMessage(EMPTY_ERROR);
            return httpResponseEntity;
        }
        Account account = query().eq("phone", phone).eq("state",1).one();

        if (RegexUtils.isPhoneInvalid(phone)) {
            httpResponseEntity.setCode(LOGIN_FAIL_CODE);
            httpResponseEntity.setMessage(PHONE_ERROR);
            return httpResponseEntity;
        }

        //从redis中取出验证码
        String redisCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);

        if(StrUtil.isEmpty(redisCode)){
            httpResponseEntity.setCode(LOGIN_FAIL_CODE);
            httpResponseEntity.setMessage(CODE_DIE_MESSAGE);
            return httpResponseEntity;
        }

        if(!redisCode.equals(code)){
            httpResponseEntity.setCode(LOGIN_FAIL_CODE);
            httpResponseEntity.setMessage(CODE_ERROR_MESSAGE);

            return httpResponseEntity;
        }

        httpResponseEntity.setCode(LOGIN_SUCCESS_CODE);
        httpResponseEntity.setMessage(LOGIN_SUCCESS_MESSAGE);
        httpResponseEntity.setData(account);

        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity register(Account body) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
//        检测是否有空字段
        String userName = body.getUserName();
        String name = body.getName();
        Integer identity = body.getIdentity();
        String phone = body.getPhone();
        String password = body.getPassword();
        String info = body.getInfo();//密保问题，用json字符串传过来
        if (CommonUtils.stringIsEmpty(userName)||CommonUtils.stringIsEmpty(name)
                ||identity==null||CommonUtils.stringIsEmpty(phone)||CommonUtils.stringIsEmpty(password)||CommonUtils.stringIsEmpty(info)){
            httpResponseEntity.setCode(REGISTER_FAIL_CODE);
            httpResponseEntity.setMessage(REGISTER_FAIL_EMPTY);
            return httpResponseEntity;
        }

//        手机号去重
        Account account = query().eq("phone", phone).one();
        if (account!=null) {
            httpResponseEntity.setCode(REGISTER_FAIL_CODE);
            httpResponseEntity.setMessage(REGISTER_FAIL_EXIST_PHONE);
            return httpResponseEntity;
        }
//        userName去重
        account = query().eq("user_name", userName).one();
        if (account!=null) {
            httpResponseEntity.setCode(REGISTER_FAIL_CODE);
            httpResponseEntity.setMessage(REGISTER_FAIL_EXIST_USERNAME);
            return httpResponseEntity;
        }
//        ID采用UUID
        body.setId(UUIDUtil.getOneUUID());
        body.setState(1);

        boolean flag = save(body);
        if (flag) {
            httpResponseEntity.setCode(REGISTER_SUCCESS_CODE);
            httpResponseEntity.setMessage(REGISTER_SUCCESS_MESSAGE);
        }else {
            httpResponseEntity.setCode(REGISTER_FAIL_CODE);
            httpResponseEntity.setMessage(REGISTER_FAIL_MESSAGE);
        }

        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity queryAllTenant() {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        List<Account> tenants = query()
                .eq("identity", 1)
//                .eq("state",1)
                .list();

        if(tenants.isEmpty()){
            httpResponseEntity.setCode(QUERY_FAIL_CODE);
            httpResponseEntity.setMessage(QUERY_FAIL_MESSAGE);
            return httpResponseEntity;
        }

        httpResponseEntity.setCode(QUERY_SUCCESS_CODE);
        httpResponseEntity.setMessage(QUERY_SUCCESS_MESSAGE);
        httpResponseEntity.setData(tenants);

        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity addTenant(Account account) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        account.setId(UUIDUtil.getOneUUID());
        account.setState(1);
        account.setIdentity(1);

        try {

            save(account);
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
    public HttpResponseEntity delete(String userName, String phone) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
        //使用phone修改状态
        if(StrUtil.isBlank(userName)) {

            updateWrapper.eq("phone", phone);
            updateWrapper.set("state", 0);
        } else {
            //使用userName修改状态
            updateWrapper.eq("user_name",userName);
            updateWrapper.set("state",0);

        }
        boolean update = update(updateWrapper);
        if(!update){
            httpResponseEntity.setCode(DELETE_FAIL_CODE);
            httpResponseEntity.setMessage(DELETE_FAIL_MESSAGE);
            return httpResponseEntity;
        }

        httpResponseEntity.setCode(DELETE_SUCCESS_CODE);
        httpResponseEntity.setMessage(DELETE_SUCCESS_MESSAGE);
        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity recover(String userName, String phone) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
        //使用phone修改状态
        if(StrUtil.isBlank(userName)) {
            updateWrapper.eq("phone", phone);
            updateWrapper.set("state", 1);
        } else {
            //使用userName修改状态
            updateWrapper.eq("user_name",userName);
            updateWrapper.set("state",1);

        }
        boolean update = update(updateWrapper);

        httpResponseEntity.setCode(update?RECOVER_SUCCESS_CODE:RECOVER_FAIL_CODE);
        httpResponseEntity.setMessage(update?RECOVER_SUCCESS_MESSAGE:RECOVER_FAIL_MESSAGE);

        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity securityQuestions(String userName) {
        Account account = query().eq("user_name", userName).one();
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        if (account==null) {
            httpResponseEntity.setCode(QUERY_FAIL_CODE);
            httpResponseEntity.setMessage(QUERY_FAIL_MESSAGE);
            return httpResponseEntity;
        }

        httpResponseEntity.setCode(QUERY_SUCCESS_CODE);
        httpResponseEntity.setMessage(QUERY_SUCCESS_MESSAGE);
        JSONObject securityQuestions = (JSONObject) JSON.parse(account.getInfo());

        if (securityQuestions==null) {
            httpResponseEntity.setCode(QUERY_FAIL_CODE);
            httpResponseEntity.setMessage(QUERY_FAIL_MESSAGE);
            return httpResponseEntity;
        }
        System.out.println(securityQuestions);
        Map<String, String> map = JsonUtils.jsonToMAp(securityQuestions);
        httpResponseEntity.setData(map);
        return httpResponseEntity;
    }



    @Override
    public HttpResponseEntity modify(Account account) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_name",account.getUserName());
        boolean update = update(account, updateWrapper);

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
    public HttpResponseEntity queryAllUser(String tenantId) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        List<String> userIds = tenantToUserMapper.queryUserByTenant(tenantId);

        if(userIds.isEmpty()){
            httpResponseEntity.setCode(QUERY_FAIL_CODE);
            httpResponseEntity.setCode(QUERY_FAIL_MESSAGE);

            return httpResponseEntity;
        }
        List<Account> accounts = new ArrayList<>();
        for(String userId : userIds){
            if(!StrUtil.isBlank(userId)){
                Account one = query().eq("id", userId).one();
                accounts.add(one);
            }

        }

        httpResponseEntity.setCode(QUERY_SUCCESS_CODE);
        httpResponseEntity.setCode(QUERY_SUCCESS_MESSAGE);
        httpResponseEntity.setData(accounts);

        return httpResponseEntity;
    }

    @Override
    public HttpResponseEntity addUser(Account account, String tenantId) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        account.setId(UUIDUtil.getOneUUID());
        TenantToUser tenantToUser = new TenantToUser(tenantId, account.getId());
        tenantToUserMapper.insert(tenantToUser);
        account.setState(1);
        account.setIdentity(2);

        try {

            save(account);
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
    public HttpResponseEntity queryAllGroup(String userId) {

        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        List<String> groupIds = userToGroupMapper.queryGroupByUser(userId);
        if(groupIds.isEmpty()){
            httpResponseEntity.setCode(QUERY_FAIL_CODE);
            httpResponseEntity.setCode(QUERY_FAIL_MESSAGE);

            return httpResponseEntity;
        }

        List<Group> groups = new ArrayList<>();
        for(String groupId : groupIds){
            if(!StrUtil.isBlank(userId)){
                Group group = groupService.query().eq("group_id", groupId).one();
                groups.add(group);
            }

        }

        httpResponseEntity.setCode(QUERY_SUCCESS_CODE);
        httpResponseEntity.setCode(QUERY_SUCCESS_MESSAGE);
        httpResponseEntity.setData(groups);

        return httpResponseEntity;

    }

}
