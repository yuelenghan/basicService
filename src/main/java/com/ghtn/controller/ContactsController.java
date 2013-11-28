package com.ghtn.controller;

import com.ghtn.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
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
public class ContactsController extends BaseController {

    private static Log log = LogFactory.getLog(ContactsController.class);

    @RequestMapping("/open")
    public String open() {
        return "contacts";
    }

    @RequestMapping("/addContacts")
    @ResponseBody
    public ResultMessage addContacts(Long pid, String name, String idCard, String phone, String email)
            throws Exception {
        // TODO : 从session的到租户信息，设置到map中传递过去
        Map<String, String> params = new HashMap<>();
        params.put("contactsType.id", pid + "");
        params.put("name", name);
        params.put("idCard", idCard);
        params.put("phone", phone);
        params.put("email", email);

        String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("contacts.addContacts"),
                params, "post");
        if (!StringUtil.isNullStr(msg) && msg.trim().equals(ConstantUtil.SUCCESS)) {
            return new ResultMessage(1, msg);
        } else {
            return new ResultMessage(-1, msg);
        }
    }

    @RequestMapping("/updateContacts")
    @ResponseBody
    public ResultMessage updateContacts(Long id, String name, String idCard, String phone, String email)
            throws Exception {
        // TODO : 从session的到租户信息，设置到map中传递过去
        Map<String, String> params = new HashMap<>();
        params.put("id", id + "");
        params.put("name", name);
        params.put("idCard", idCard);
        params.put("phone", phone);
        params.put("email", email);

        String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("contacts.updateContacts"),
                params, "post");
        if (!StringUtil.isNullStr(msg) && msg.trim().equals(ConstantUtil.SUCCESS)) {
            return new ResultMessage(1, msg);
        } else {
            return new ResultMessage(-1, msg);
        }
    }

    @RequestMapping("/removeContacts")
    @ResponseBody
    public ResultMessage removeContacts(Long id) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("id", id + "");
        String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("contacts.removeContacts"),
                params, "post");
        if (!StringUtil.isNullStr(msg) && msg.trim().equals(ConstantUtil.SUCCESS)) {
            return new ResultMessage(1, msg);
        } else {
            return new ResultMessage(-1, msg);
        }
    }

    @RequestMapping("/getContactsByPage")
    @ResponseBody
    public Object getContactsByPage(Long contactsTypeId, Integer page, Integer rows) throws Exception {
        log.debug("page ======" + page + " , rows ======" + rows);
        Map<String, String> params = new HashMap<>();
        params.put("id", contactsTypeId + "");
        params.put("page", page + "");
        params.put("rows", rows + "");
        return HttpClientUtil.sendRequest(HttpClientUtil.getUrl("contacts.getContactsByPage"),
                params, "post");
    }

    @RequestMapping("/uploadFile")
    @ResponseBody
    public ResultMessage uploadFile(@RequestParam("fileUpload") CommonsMultipartFile file, HttpSession session)
            throws Exception {
        ResultMessage resultMessage = FileUtil.uploadFile(file);
        session.setAttribute("fileName", resultMessage.getMsg());
        return resultMessage;
    }

    @RequestMapping("/batchImportContacts")
    @ResponseBody
    public ResultMessage batchImportContacts(HttpSession session, Long contactsTypeId) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("fileName", session.getAttribute("fileName").toString());
        params.put("id", contactsTypeId + "");
        // TODO : 从session中取得租户信息，并设置租户id
        String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("contacts.batchImportContacts"),
                params, "post");
        if (!StringUtil.isNullStr(msg) && msg.trim().equals(ConstantUtil.SUCCESS)) {
            return new ResultMessage(1, msg);
        } else {
            return new ResultMessage(-1, msg);
        }
    }

    @RequestMapping("/downloadTemplate")
    @ResponseBody
    public ResultMessage downloadTemplate(String fileName, HttpServletResponse response) throws Exception {
        return FileUtil.downloadFile(fileName, response);
    }
}
