package com.ghtn.controller;

import com.ghtn.util.HttpClientUtil;
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
    public Object getMaterialByPage(Long materialTypeId, Integer page, Integer rows) throws Exception {
        log.debug("page ======" + page + " , rows ======" + rows);
        Map<String, String> params = new HashMap<>();
        params.put("id", materialTypeId + "");
        params.put("page", page + "");
        params.put("rows", rows + "");
        return HttpClientUtil.sendRequest(HttpClientUtil.getUrl("material.getMaterialByPage"),
                params, "post");
    }
}
