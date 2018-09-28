package com.lifestorm.learn.template.freemarker;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by life_storm on 2018/5/4.
 */
public class FreemarkerTest {

    public static void main(String[] args) {
        FreeMarkerUtils freeUtils = new FreeMarkerUtils();
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("username", "李四");
        freeUtils.print("01.ftl", root);
        freeUtils.fprint("02.ftl", root,"D:\\01.html");

    }
}
