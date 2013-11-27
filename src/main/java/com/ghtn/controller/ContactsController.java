package com.ghtn.controller;

import com.ghtn.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;
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

    @RequestMapping("/addContacts")
    @ResponseBody
    public ResultMessage addContacts(Long pid, String name, String idCard, String phone, String email) {
        // TODO : 从session的到租户信息，设置到map中传递过去
        Map<String, String> params = new HashMap<>();
        params.put("contactsType.id", pid + "");
        params.put("name", name);
        params.put("idCard", idCard);
        params.put("phone", phone);
        params.put("email", email);

        try {
            String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("contacts.addContacts"),
                    params, "post");
            if (!StringUtil.isNullStr(msg) && msg.trim().equals(ConstantUtil.SUCCESS)) {
                return new ResultMessage(1, msg);
            } else {
                return new ResultMessage(-1, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMessage(-1, e.toString());
        }
    }

    @RequestMapping("/updateContacts")
    @ResponseBody
    public ResultMessage updateContacts(Long id, String name, String idCard, String phone, String email) {
        // TODO : 从session的到租户信息，设置到map中传递过去
        Map<String, String> params = new HashMap<>();
        params.put("id", id + "");
        params.put("name", name);
        params.put("idCard", idCard);
        params.put("phone", phone);
        params.put("email", email);

        try {
            String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("contacts.updateContacts"),
                    params, "post");
            if (!StringUtil.isNullStr(msg) && msg.trim().equals(ConstantUtil.SUCCESS)) {
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
            if (!StringUtil.isNullStr(msg) && msg.trim().equals(ConstantUtil.SUCCESS)) {
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

    @RequestMapping("/uploadFile")
    @ResponseBody
    public ResultMessage uploadFile(@RequestParam("fileUpload") CommonsMultipartFile file, HttpSession session) {
        ResultMessage resultMessage = FileUtil.uploadFile(file);
        session.setAttribute("fileName", resultMessage.getMsg());
        return resultMessage;
    }

    @RequestMapping("/batchImportContacts")
    @ResponseBody
    public ResultMessage batchImportContacts(HttpSession session, Long contactsTypeId) {
        Map<String, String> params = new HashMap<>();
        params.put("fileName", session.getAttribute("fileName").toString());
        params.put("id", contactsTypeId + "");
        // TODO : 从session中取得租户信息，并设置租户id
        try {
            String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("contacts.batchImportContacts"),
                    params, "post");
            if (!StringUtil.isNullStr(msg) && msg.trim().equals(ConstantUtil.SUCCESS)) {
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
