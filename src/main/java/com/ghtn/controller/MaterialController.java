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
 * Date: 13-11-27
 * Time: 下午4:17
 */
@Controller
@RequestMapping("/material")
public class MaterialController extends BaseController {

    private static Log log = LogFactory.getLog(MaterialController.class);

    @RequestMapping("/open")
    public String open() {
        return "material";
    }

    @RequestMapping("/getMaterialByPage")
    @ResponseBody
    public String getMaterialByPage(Long materialTypeId, String type, Integer page, Integer rows) throws Exception {
        log.debug("page ======" + page + " , rows ======" + rows);
        Map<String, String> params = new HashMap<>();
        params.put("id", materialTypeId + "");
        params.put("type", type);
        params.put("page", page + "");
        params.put("rows", rows + "");
        return HttpClientUtil.sendRequest(HttpClientUtil.getUrl("material.getMaterialByPage"),
                params, "post");
    }

    @RequestMapping("/addMaterial")
    @ResponseBody
    public ResultMessage addMaterial(Long materialTypeId, String title, String text, String type) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("materialType.id", materialTypeId + "");
        params.put("title", title);
        params.put("text", text);
        params.put("type", type);

        String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("material.addMaterial"),
                params, "post");
        return ResultMessage.checkMsg(msg);
    }

    @RequestMapping("/removeMaterial")
    @ResponseBody
    public ResultMessage removeMaterial(Long id) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("id", id + "");

        String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("material.removeMaterial"),
                params, "post");
        return ResultMessage.checkMsg(msg);
    }

    @RequestMapping("/getMaterial")
    @ResponseBody
    public String getMaterial(Long id) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("id", id + "");
        return HttpClientUtil.sendRequest(HttpClientUtil.getUrl("material.getMaterial"),
                params, "post");
    }

    @RequestMapping("/updateMaterial")
    @ResponseBody
    public ResultMessage updateMaterial(Long id, String title, String text) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("id", id + "");
        params.put("title", title);
        params.put("text", text);
        String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("material.updateMaterial"),
                params, "post");
        return ResultMessage.checkMsg(msg);
    }
}
