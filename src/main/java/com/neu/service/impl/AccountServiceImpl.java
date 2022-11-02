package com.neu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.bean.HttpResponseEntity;
import com.neu.common.utils.HttpUtils;
import com.neu.common.utils.RegexUtils;
import com.neu.dao.AccountMapper;
import com.neu.dao.entity.Account;
import com.neu.service.AccountService;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.neu.common.Constants.*;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    private String code;

    @Override
    public HttpResponseEntity loginByUserName(String userName, String password) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        Account account = query().eq("user_name", userName).eq("password", password).one();

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
        code = RandomUtil.randomNumbers(6);

        log.error("验证码为"+code);

        if (RegexUtils.isPhoneInvalid(phone)) {
            // 2.如果不符合，返回错误信息
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
        querys.put("param", "**code**:"+code+",**minute**:5");
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
        Account account = query().eq("phone", phone).one();

        if (RegexUtils.isPhoneInvalid(phone)) {
            httpResponseEntity.setCode(LOGIN_FAIL_CODE);
            httpResponseEntity.setMessage(PHONE_ERROR);
            return httpResponseEntity;
        }

        if(this.code.equals(code)){
            httpResponseEntity.setCode(LOGIN_SUCCESS_CODE);
            httpResponseEntity.setMessage(LOGIN_SUCCESS_MESSAGE);
            httpResponseEntity.setData(account);
            return httpResponseEntity;
        }

        httpResponseEntity.setCode(LOGIN_FAIL_CODE);
        httpResponseEntity.setMessage(LOGIN_FAIL_MESSAGE);

        return httpResponseEntity;
    }


}
