package com.vi3nty.blog.utils;

public enum  ResponseCode {
    /**
     * 0-成功
     * 1-失败
     * 注册返回值:
     * 21:邮箱为空
     * 22:用户名为空
     * 23:密码为空
     * 24:邮箱重复
     *
     */
    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    NEED_LOGIN(10,"NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT"),
    EMAIL_BLANK(21,"邮箱获取为空"),
    USERNAME_BLANK(22,"用户名获取为空"),
    PASSWORD_BLANK(23,"密码获取为空"),
    EMAIL_REPET(24,"该邮箱已注册！");

    private final int code;
    private final String desc;

    ResponseCode(int code,String desc){
        this.code=code;
        this.desc=desc;
    }

    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }
}
