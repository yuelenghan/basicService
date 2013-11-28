package com.ghtn.controller;

import com.ghtn.util.ConstantUtil;
import com.ghtn.util.HttpClientUtil;
import com.ghtn.util.ResultMessage;
import com.ghtn.util.StringUtil;
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
@RequestMapping("/materialType")
public class MaterialTypeController extends BaseController {

    @RequestMapping("/listMaterialType")
    @ResponseBody
    public Object listMaterialType() throws Exception {
        // TODO : 设置租户参数
        return HttpClientUtil.sendRequest(HttpClientUtil.getUrl("materialType.listMaterialType"),
                null, "post");
    }

    @RequestMapping("/addMaterialType")
    @ResponseBody
    public ResultMessage addMaterialType(String name) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("materialType.addMaterialType"),
                params, "post");
        if (!StringUtil.isNullStr(msg) && msg.trim().equals("success")) {
            return new ResultMessage(1, msg);
        } else {
            return new ResultMessage(-1, msg);
        }
    }

    @RequestMapping("/removeMaterialType")
    @ResponseBody
    public ResultMessage removeMaterialType(Long id) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("id", id + "");
        String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("materialType.removeMaterialType"),
                params, "post");
        if (!StringUtil.isNullStr(msg) && msg.trim().equals(ConstantUtil.SUCCESS)) {
            return new ResultMessage(1, msg);
        } else {
            return new ResultMessage(-1, msg);
        }
    }

    @RequestMapping("/updateMaterialType")
    @ResponseBody
    public ResultMessage updateMaterialType(Long id, String name)
            throws Exception {
        // TODO : 从session的到租户信息，设置到map中传递过去
        Map<String, String> params = new HashMap<>();
        params.put("id", id + "");
        params.put("name", name);

        String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("materialType.updateMaterialType"),
                params, "post");
        if (!StringUtil.isNullStr(msg) && msg.trim().equals(ConstantUtil.SUCCESS)) {
            return new ResultMessage(1, msg);
        } else {
            return new ResultMessage(-1, msg);
        }
    }
}
