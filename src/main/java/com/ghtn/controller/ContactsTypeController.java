package com.ghtn.controller;

import com.ghtn.util.HttpClientUtil;
import com.ghtn.util.ResultMessage;
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
public class ContactsTypeController extends BaseController {

    private static Log log = LogFactory.getLog(ContactsTypeController.class);

    @RequestMapping("/getContactsTypeTree")
    @ResponseBody
    public Object getContactsTypeTree() throws Exception {
        // TODO : 设置租户参数
        return HttpClientUtil.sendRequest(HttpClientUtil.getUrl("contactsType.getContactsTypeTree"),
                null, "post");
    }

    @RequestMapping("/removeContactsType")
    @ResponseBody
    public ResultMessage removeContactsType(Long id) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("id", id + "");
        log.debug("id ============= " + id);
        String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("contactsType.removeContactsType"),
                params, "post");
        return ResultMessage.checkMsg(msg);
    }

    @RequestMapping("/updateContactsType")
    @ResponseBody
    public ResultMessage updateContactsType(Long id, String name) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("id", id + "");
        params.put("name", name);
        log.debug("id ====" + id + " , name ====" + name);
        String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("contactsType.updateContactsType"),
                params, "post");
        return ResultMessage.checkMsg(msg);
    }

    @RequestMapping("/addChild")
    @ResponseBody
    public ResultMessage addChild(Long id, String name) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("id", id + "");
        params.put("name", name);
        String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("contactsType.addChild"),
                params, "post");
        return ResultMessage.checkMsg(msg);
    }
}
