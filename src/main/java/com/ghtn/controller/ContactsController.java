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
@RequestMapping("/contacts")
public class ContactsController {

    private static Log log = LogFactory.getLog(ContactsController.class);

    @RequestMapping("/open")
    public String open() {
        return "contacts";
    }

    @RequestMapping("/saveContacts")
    @ResponseBody
    public ResultMessage saveContacts(String name, String idCard) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("idCard", idCard);

        try {
            String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("contacts.saveContacts"),
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

    @RequestMapping("/removeContacts")
    @ResponseBody
    public ResultMessage removeContacts(Long id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id + "");
        try {
            String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("contacts.removeContacts"),
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

    @RequestMapping("/getContactsByPage")
    @ResponseBody
    public Object getContactsByPage(Long contactsTypeId, Integer page, Integer rows) {
        try {
            log.debug("page ======" + page + " , rows ======" + rows);
            Map<String, String> params = new HashMap<>();
            params.put("id", contactsTypeId + "");
            params.put("page", page + "");
            params.put("rows", rows + "");
            return HttpClientUtil.sendRequest(HttpClientUtil.getUrl("contacts.getContactsByPage"),
                    params, "post");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMessage(-1, e.toString());
        }
    }

    @RequestMapping("/batchImportContacts")
    @ResponseBody
    public ResultMessage batchImportContacts(String fileName) {
        Map<String, String> params = new HashMap<>();
        params.put("fileName", fileName);

        try {
            String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("contacts.removeContacts"),
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
