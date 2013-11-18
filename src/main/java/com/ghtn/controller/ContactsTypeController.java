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
 * Date: 13-11-15
 * Time: 下午4:18
 */
@Controller
@RequestMapping("/contactsType")
public class ContactsTypeController {

    private static Log log = LogFactory.getLog(ContactsTypeController.class);

    @RequestMapping("/getContactsTypeTree")
    @ResponseBody
    public Object getContactsTypeTree() {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("id", 1);
            map.put("text", "test");
            map.put("state", "open");
            return HttpClientUtil.sendRequest(HttpClientUtil.getUrl("contactsType.getContactsTypeTree"),
                    null, "post");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMessage(-1, e.toString());
        }
    }

    @RequestMapping("/removeContactsType")
    @ResponseBody
    public ResultMessage removeContactsType(Long id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id + "");
        log.debug("id ============= " + id);
        try {
            String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("contactsType.removeContactsType"),
                    params, "post");
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
