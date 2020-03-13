package com.tomorrowli.community.community.util;

import com.alibaba.fastjson.JSONObject;
import com.sun.mail.smtp.DigestMD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @Author: LiMing
 * @Date: 2020/3/3 17:20
 * @Description:
 **/
@Component
public class CommunityUtils {


    //生成随机字符串
    public static String generateUUID(){
       return UUID.randomUUID().toString().replaceAll("-", "");
    }

    //利用md5加密字符串
    public static String md5(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    //获取Josn对象的字符串
    public static String getJSONObject(int code , String msg, Map<String,Object> map){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("msg",msg);
        if(map!=null) {
            for (String s : map.keySet()) {
                jsonObject.put(s,map.get(s));
            }
        }
        return jsonObject.toJSONString();

    }
    public static String getJSONObject(int code , String msg){
        return getJSONObject(code,msg,null);
    }
    public static String getJSONObject(int code){
        return getJSONObject(code,null,null);
    }
    }


