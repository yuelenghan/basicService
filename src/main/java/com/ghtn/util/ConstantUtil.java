package com.ghtn.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-4
 * Time: 上午10:57
 * To change this template use File | Settings | File Templates.
 */
public class ConstantUtil {
    public static String INDEX_BASE;
    public static String UPLOAD_TEMP_PATH;
    public static String CONTACTS_TEMPLATE_PATH;
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    static {
        InputStream in = ConstantUtil.class.getResourceAsStream("/constant.properties");
        Properties prop = new Properties();
        try {
            prop.load(in);
            INDEX_BASE = prop.getProperty("hibernate.search.default.indexBase").trim();
            UPLOAD_TEMP_PATH = prop.getProperty("upload.temp.path").trim();
            CONTACTS_TEMPLATE_PATH = prop.getProperty("contacts.template.path").trim();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
