package com.neu.common;


public class Constants {

    public static final String EMPTY_ERROR = "空值异常";

    /**
     * Description:登录状态
     */
    public static final String LOGIN_SUCCESS_CODE = "666";
    public static final String LOGIN_SUCCESS_MESSAGE = "登录成功";
    public static final String LOGIN_FAIL_CODE = "401";
    public static final String LOGIN_FAIL_MESSAGE = "登录失败";
    public static final String PHONE_ERROR = "手机号格式错误";
//    未登录状态
    public static final String NOT_LOGIN="333";
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
    public static final String QUESTION_ANSWER_KEY = "questionAnswer";


    /**
     * Description:CRUD相关
     */
    public static final String QUERY_SUCCESS_CODE = "666";
    public static final String QUERY_SUCCESS_MESSAGE = "查询成功";
    public static final String QUERY_FAIL_CODE = "114514";
    public static final String QUERY_FAIL_MESSAGE = "数据异常";
    public static final String INSERT_SUCCESS_CODE = "666";
    public static final String INSERT_SUCCESS_MESSAGE = "添加成功";
    public static final String INSERT_FAIL_CODE = "114514";
    public static final String INSERT_FAIL_MESSAGE = "添加失败";
    public static final String DELETE_SUCCESS_CODE = "666";
    public static final String DELETE_SUCCESS_MESSAGE = "删除成功";
    public static final String DELETE_FAIL_CODE = "114514";
    public static final String DELETE_FAIL_MESSAGE = "删除失败";
    public static final String RECOVER_SUCCESS_CODE = "666";
    public static final String RECOVER_SUCCESS_MESSAGE = "启用成功";
    public static final String RECOVER_FAIL_CODE = "114514";
    public static final String RECOVER_FAIL_MESSAGE = "启用失败";
    public static final String MODIFY_SUCCESS_CODE = "666";
    public static final String MODIFY_SUCCESS_MESSAGE = "修改成功";
    public static final String MODIFY_FAIL_CODE = "114514";
    public static final String MODIFY_FAIL_MESSAGE = "修改失败";

    /**
     * Description:计费相关
     */
    public static final double EXPENSE_PER_GROUP = 1;
    public static final double EXPENSE_PER_QUESTIONNAIRE = 0.5;
    public static final double EXPENSE_PER_ANSWER = 0.1;
    public static final String PAY_SUCCESS_CODE = "666";
    public static final String PAY_SUCCESS_MESSAGE = "支付成功";
    public static final String PAY_FAIL_CODE = "114514";
    public static final String PAY_FAIL_MESSAGE = "余额不足";
}

