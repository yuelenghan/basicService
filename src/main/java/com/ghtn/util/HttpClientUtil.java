package com.ghtn.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;

/**
 * User: Administrator
 * Date: 13-11-11
 * Time: 下午3:48
 */
public class HttpClientUtil {

    private static Log log = LogFactory.getLog(HttpClientUtil.class);

    // 使用线程安全的连接管理来创建httpClient
    private static ClientConnectionManager conMgr = new ThreadSafeClientConnManager();
    private static HttpClient httpClient = new DefaultHttpClient(conMgr);

    /**
     * 根据属性名得到请求的url
     *
     * @param property 属性名
     * @return
     */
    public static String getUrl(String property) {
        Properties prop = new Properties();
        try (InputStream in = ConstantUtil.class.getResourceAsStream("/url.properties")) {
            prop.load(in);
            return prop.getProperty(property).trim();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String sendRequest(String url, Map<String, String> params, String method)
            throws Exception {
        // 初始化请求参数
        List<BasicNameValuePair> formParams = null;
        if (params == null || params.size() == 0) {
            log.warn("没有请求参数！");
        } else {
            formParams = new ArrayList<>();
            Set<String> keySet = params.keySet();

            for (String key : keySet) {
                BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key, params.get(key));
                formParams.add(basicNameValuePair);
            }
        }

        if (method.toUpperCase().equals("POST")) {
            return sendRequestPost(url, formParams);
        } else if (method.toUpperCase().equals("GET")) {
            return sendRequestGet(url, formParams);
        } else {
            log.error("请求方式错误！");
            return "";
        }
    }

    public static String sendRequestPost(String url, List<BasicNameValuePair> params) throws Exception {
        HttpPost request = new HttpPost(url);

        if (params == null || params.size() == 0) {
            log.warn("没有请求参数！");
        } else {
            HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            request.setEntity(entity);
        }

        // 获取返回数据
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        return httpClient.execute(request, responseHandler);
    }

    public static String sendRequestGet(String url, List<BasicNameValuePair> params) throws Exception {
        HttpGet request = new HttpGet(url);
        String str = EntityUtils.toString(new UrlEncodedFormEntity(params));
        request.setURI(new URI(request.getURI().toString() + "?" + str));

        // 获取返回数据
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        return httpClient.execute(request, responseHandler);
    }
}
