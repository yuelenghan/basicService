package com.ghtn.controller;

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
        String msg = HttpClientUtil.sendRequest(HttpClientUtil.getUrl("materialType.addChild"),
                params, "post");
        if (!StringUtil.isNullStr(msg) && msg.trim().equals("success")) {
            return new ResultMessage(1, msg);
        } else {
            return new ResultMessage(-1, msg);
        }
    }
}
