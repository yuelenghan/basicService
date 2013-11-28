package com.ghtn.controller;

import com.ghtn.util.ResultMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 处理异常信息
 * User: Administrator
 * Date: 13-11-28
 * Time: 上午8:58
 */
public class BaseController {

    private static Log log = LogFactory.getLog(BaseController.class);

    @ExceptionHandler
    @ResponseBody
    public ResultMessage handleException(Exception e) {
        log.error("------------发生异常！----------------");
        log.error("Caused by : " + e.getCause());
        log.error("Message : " + e.getMessage());
        return new ResultMessage(-1, e.toString());
    }
}
