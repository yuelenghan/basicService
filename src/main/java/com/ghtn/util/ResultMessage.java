package com.ghtn.util;

/**
 * User: Administrator
 * Date: 13-11-12
 * Time: 上午8:59
 */
public class ResultMessage {
    private Integer code;
    private String msg;

    public ResultMessage() {
    }

    public ResultMessage(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
