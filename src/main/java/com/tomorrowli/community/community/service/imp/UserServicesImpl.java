package com.tomorrowli.community.community.service.imp;

import com.tomorrowli.community.community.dao.LoginTicket;
import com.tomorrowli.community.community.dao.User;
import com.tomorrowli.community.community.mapper.DisCussPortMapper;
import com.tomorrowli.community.community.mapper.LoginTicketMapper;
import com.tomorrowli.community.community.mapper.UserMapper;
import com.tomorrowli.community.community.service.UserServices;
import com.tomorrowli.community.community.util.CommunityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.Oneway;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: LiMing
 * @Date: 2020/2/29 18:11
 * @Description:
 **/
@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private  DisCussPortMapper disCussPortMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;
    @Override
    public User selectUserById(int userId) {
        return userMapper.selectUserById(userId);
    }


    @Override
    public Map<String, Object> login(String username, String password, int expiredTime) {
        Map<String, Object> map = new HashMap<>();

        //判断用户名
        if(StringUtils.isBlank(username)){
            map.put("usernameMsg","用户名为空！");
            return map;
        }
        //判断密码
        if(StringUtils.isBlank(password)){
            map.put("passwordMsg","密码为空！");
            return map;
        }

        User user = userMapper.selectUserName(username);

        if(user==null){
            map.put("usernameMsg","用户不存在！");
            return map;
        }
        if(user.getStatus()==0){
            map.put("usernameMsg","用户未激活！");
            return map;
        }

        //校验密码
        String s = CommunityUtils.md5(password+user.getSalt());
        if(!(s.equals(user.getPassword()))){
            map.put("passwordMsg","密码错误！！");
            return map;
        }

        //生成凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setExpired(new Date(System.currentTimeMillis()+ expiredTime*1000));
        loginTicket.setTicket(CommunityUtils.generateUUID());
        loginTicket.setUserId(user.getId());
        loginTicket.setStatus(0);

        loginTicketMapper.insertTicket(loginTicket);

        map.put("ticket",loginTicket.getTicket());
        return map;
    }

    @Override
    public void logout(String ticket) {
        loginTicketMapper.updateStatusByUserId(ticket,1);
    }

    @Override
    public LoginTicket findLoginTicket(String ticket) {

        return loginTicketMapper.findLoginTicket(ticket);
    }

    @Override
    public void uploadHeaderUrl(int userId, String headerUrl) {
        userMapper.updateHeaderUrl(userId,headerUrl);
    }

    @Override
    public void updatepassword(int userId, String password) {
        User user = userMapper.selectUserById(userId);
        password=CommunityUtils.md5(password+user.getSalt());
        userMapper.updatePassword(userId,password);
    }

}
