package com.neu.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;


public class JsonUtils {
    public static Map<String, String> jsonToMAp(JSONObject obj) {
        return JSONObject.parseObject(obj.toJSONString(), new TypeReference<Map<String, String>>(){});
    }

    public static void main(String[] args) {
        String str = "{\"name\":\"小双\",\"age\":18,\"addr\":\"扬州\"}";
        System.out.println(jsonToMAp(JSON.parseObject(str)).get("name"));
    }
}
