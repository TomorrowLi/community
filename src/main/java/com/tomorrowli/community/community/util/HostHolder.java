package com.tomorrowli.community.community.util;

import com.tomorrowli.community.community.dao.User;
import org.springframework.stereotype.Component;

/**
 * @Author: LiMing
 * @Date: 2020/3/10 18:39
 * @Description:用户保存用户信息和session类似
 **/
@Component
public class HostHolder {

    private ThreadLocal<User> users=new ThreadLocal<>();

    public void setUser(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }


}
