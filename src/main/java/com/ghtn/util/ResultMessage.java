package com.ghtn.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: Administrator
 * Date: 13-11-12
 * Time: 上午8:59
 */
public class ResultMessage {
    private Integer code;
    private String msg;
    private List resultList;
    private Map resultMap;
    private Set resultSet;

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

    public List getResultList() {
        return resultList;
    }

    public void setResultList(List resultList) {
        this.resultList = resultList;
    }

    public Map getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map resultMap) {
        this.resultMap = resultMap;
    }

    public Set getResultSet() {
        return resultSet;
    }

    public void setResultSet(Set resultSet) {
        this.resultSet = resultSet;
    }

    public static ResultMessage checkMsg(String msg) {
        if (!StringUtil.isNullStr(msg) && msg.trim().equals(ConstantUtil.SUCCESS)) {
            return new ResultMessage(1, msg);
        } else {
            return new ResultMessage(-1, msg);
        }
    }
}


