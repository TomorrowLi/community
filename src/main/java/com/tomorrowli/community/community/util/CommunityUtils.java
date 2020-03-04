package com.tomorrowli.community.community.util;

import com.sun.mail.smtp.DigestMD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

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
}
