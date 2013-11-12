package com.ghtn.controller;

import com.ghtn.util.HttpClientUtil;
import com.ghtn.util.ResultMessage;
import com.ghtn.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-11-11
 * Time: 下午5:28
 */
@Controller
@RequestMapping("/contacts/")
public class ContactsController {

    private static Log log = LogFactory.getLog(ContactsController.class);
    private Map<String, String> params = new HashMap<>();

    @RequestMapping("addContacts")
    @ResponseBody
    public ResultMessage addContacts(String name, String idCard) {
        params.put("name", name);
        params.put("idCard", idCard);

        try {
            String url = HttpClientUtil.getUrl("contacts.addContacts");
            String msg = HttpClientUtil.sendRequest(url, params, "post");
            if (!StringUtil.isNullStr(msg) && msg.trim().equals("success")) {
                return new ResultMessage(1, msg);
            } else {
                return new ResultMessage(-1, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMessage(-1, e.toString());
        }
    }
}
