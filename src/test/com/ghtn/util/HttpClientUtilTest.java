package com.ghtn.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-11-11
 * Time: 下午4:21
 */
public class HttpClientUtilTest {

    private static Log log = LogFactory.getLog(HttpClientUtilTest.class);

    @Test
    public void testGetUrl() throws Exception {
        String url = HttpClientUtil.getUrl("contacts.addContacts");
        log.debug("url = " + url);
    }

    @Test
    public void testSendRequest() throws Exception {
        String url = HttpClientUtil.getUrl("contacts.addContacts");
        Map<String, String> map = new HashMap<>();
        map.put("name", "httpClient");
        map.put("idCard", "888888888");
        String message = HttpClientUtil.sendRequest(url, map, "post");
        log.debug(message);
    }
}
