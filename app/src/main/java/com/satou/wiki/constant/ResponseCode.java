package com.satou.wiki.constant;

/**
 * Created by Mitsuki on 2018/3/29.
 */

public enum ResponseCode {


    CONTINUE(100, "请求者应当继续提出请求"),
    SWITCH(101, "请求者已要求服务器切换协议，服务器已确认并准备切换"),
    SUCCESS(200, "服务器已成功处理了请求"),
    CREATED(201, "请求成功并且服务器创建了新的资源"),
    ACCEPTED(202, "服务器已接受请求，但尚未处理"),
    UNAUTHORIZED_INFO(203, "服务器已成功处理了请求，但返回的信息可能来自另一来源"),
    NO_CONTENT(204, "服务器成功处理了请求，但没有返回任何内容"),
    RESET_CONTENT(205, "服务器成功处理了请求，但没有返回任何内容"),
    PART_CONTENT(206, "服务器成功处理了部分 GET 请求"),
    MULTIPLE_CHOICES(300, "针对请求，服务器可执行多种操作"),
    PERMANENT_MOVEMENT(301, "请求的网页已永久移动到新位置"),
    TEMPORARY_MOVEMENT(302, "服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求"),
    VIEW_LOCATION(303, "请求者应当对不同的位置使用单独的 GET 请求来检索响应"),
    NO_CHANGE(304, "自从上次请求后，请求的网页未修改过"),
    USE_AGENT(305, "请求者只能使用代理访问请求的网页"),
    TEMPORARY_REDIRECT(307, "服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求"),
    ERROR(400, "服务器不理解请求的语法"),
    UNAUTHORIZED(401, "请求要求身份验证"),
    REFUSE(403, "服务器拒绝请求"),
    NOT_FOUND(404, "服务器找不到请求的网页"),
    METHOD_BAN(405, "禁用请求中指定的方法"),
    UNACCEPTED(406, "无法使用请求的内容特性响应请求的网页"),
    NEED_AGENT(407, "指定请求者应当授权使用代理"),
    TIMEOUT(408, "服务器等候请求时发生超时"),
    CONFLICT(409, "服务器在完成请求时发生冲突"),
    DELETED(410, "请求的资源已永久删除"),
    EFFECTIVE_LENGTH(411, "服务器不接受不含有效内容长度标头字段的请求"),
    NO_CONDITION(412, "服务器未满足请求者在请求中设置的其中一个前提条件"),
    TOO_LARGE(413, "服务器无法处理请求"),
    URL_TOO_LONG(414, "请求的 URI 过长"),
    NOT_SUPPORT_TYPE(415, "请求的格式不受请求页面的支持"),
    BEYOND_SCOPE(416, "页面无法提供请求的范围"),
    UNSATISFIED_EXPECTATION(417, "服务器未满足'期望'请求标头字段的要求"),
    INTERNAL_ERROR(500, "服务器遇到错误"),
    NOT_IMPLEMENTED(501, "服务器不具备完成请求的功能"),
    GATEWAT_ERROR(502, "服务器作为网关或代理，从上游服务器收到无效响应"),
    NOT_AVAILABLE(503, "服务器目前无法使用"),
    GATEWAY_TIMEOUT(504, "服务器作为网关或代理，但是没有及时从上游服务器收到请求"),
    HTTP_NOT_SUPPORT(505, "服务器不支持请求中所用的 HTTP 协议版本");


    private String msg;
    private int code;

    ResponseCode(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public static String getMsgByCode(int code) {
        for (ResponseCode responseCode : ResponseCode.values()) {
            if (responseCode.getCode() == code) {
                return responseCode.msg;
            }
        }
        return null;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
