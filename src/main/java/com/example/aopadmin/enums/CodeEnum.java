package com.example.aopadmin.enums;

/**
 * 说明：状态码enums
 *
 * @version v1.0
 * @date 2020/8/14
 */
public enum CodeEnum {
    request_exception(500, "请求异常"),
    OK(200, "成功"),
    /**
     * 请求参数错误
     */
    BAD_REQUEST_PARAM(400, "请求参数错误"),

    /**
     * 认证失败
     */
    UN_AUTH(401, "认证失败"),

    /**
     * 鉴权拒绝
     */
    FORBIDDEN(403, "鉴权拒绝"),

    /**
     * 资源无法找到
     */
    NOT_FOUND(404, "资源无法找到"),

    /**
     * 请求方法不支持
     */
    METHOD_NOT_ALLOWED(405, "请求方法不支持"),

    /**
     * 请求访问超时
     */
    REQUEST_TIMEOUT(408, "请求访问超时"),

    /**
     * 前置条件不满足
     */
    PRECONDITION_FAILED(412, "前置条件不满足"),

    /**
     * 请求Content-type错误
     */
    UNSUPPORTED_MEDIA_TYPE(415, "请求Content-type错误"),

    /**
     * sql语法错误
     */

    SQL_ERROR(416, "sql语法错误"),


    /**
     * 不再允许修改
     */

    CAN_NOT_CHANGE(417, "不允许修改"),

    /**
     * 服务内部错误
     */
    INTERNAL_ERROR(500, "服务内部错误"),

    /**
     * 获取验证码次数超过限制
     */
    VERIFYCODE_LIMIT(501, "获取验证码次数超过限制"),

    /**
     * 获取验证码过于频繁
     */
    VERIFYCODE_FREQUENTLY(502, "获取验证码过于频繁"),

    /**
     * 验证码无效
     */
    VERIFYCODE_INVALID(503, "验证码无效"),

    /**
     * 接口访问次数过于频繁
     */
    API_ACCESS_FREQUENTLY(504, "接口访问次数过于频繁"),
    /**
     * 手机号已使用
     */
    MOBILE_ALREADY_USED(510, "手机号已使用"),
    /**
     * 手机号不存在
     */
    MOBILE_NON_EXISTENT(511, "手机号不存在"),
    /**
     * 用户需要授权登录
     */
    AUTHORIZATION_LOGIN(512, "用户需要授权登录"),

    /**
     * 邮箱格式错误
     */
    EMAIL_REG_ERROR(513, "邮箱格式错误"),
    /**
     * 邮箱发送频繁
     */
    EMAIL_SEND_FREQUENTLY(514, "邮箱发送频繁"),
    /**
     * 邮箱发送超过上限
     */
    EMAIL_SEND_LIMIT(515, "邮箱发送超过上限"),
    /**
     * 手机号码已绑定
     */
    USER_LOGIN_IN(516, "手机号码已绑定"),
    /**
     * 发送邮件超过限制
     */
    MAIL_LIMIT(520, "发送邮件超过限制"),

    DATA_NOT_FOUND(600, "数据不存在"),
    LIMIT(601, "超过限制"),;
    private int code;
    private String desc;

    CodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
