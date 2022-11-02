package com.neu.common;


public class Constants {

    /**
     * Description:登录状态
     */
    public static final String LOGIN_SUCCESS_CODE = "666";
    public static final String LOGIN_SUCCESS_MESSAGE = "登录成功";
    public static final String LOGIN_FAIL_CODE = "401";
    public static final String LOGIN_FAIL_MESSAGE = "登录失败";
    public static final String PHONE_ERROR = "手机号格式错误";
    /**
     * Description:注册状态
     */
    public static final String REGISTER_FAIL_CODE = "555";
    public static final String REGISTER_SUCCESS_CODE = "666";
    public static final String REGISTER_SUCCESS_MESSAGE = "注册成功";
    public static final String REGISTER_FAIL_EXIST_PHONE="手机号已被注册";
    public static final String REGISTER_FAIL_EXIST_USERNAME = "用户名已被注册";
    public static final String REGISTER_FAIL_EMPTY = "有信息未输入";
    public static final String REGISTER_FAIL_MESSAGE = "注册失败";
    /**
     * Description:验证码
     */
    public static final String CODE_SEND_SUCCESS_CODE = "666";
    public static final String CODE_SEND_FAIL_CODE = "201";
    public static final String CODE_SEND_SUCCESS_MESSAGE = "验证码发送成功";
    public static final String CODE_SEND_FAIL_MESSAGE = "验证码发送失败";
    public static final String CODE_DIE_MESSAGE = "验证码已过期";
    public static final String CODE_ERROR_MESSAGE = "验证码错误";

    /**
     * Description:redis相关
     */

    public static final String LOGIN_CODE_KEY = "login:code";
    public static final long LOGIN_CODE_TTL = 2L;
}

