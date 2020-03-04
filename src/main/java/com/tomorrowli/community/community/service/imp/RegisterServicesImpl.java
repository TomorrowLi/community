package com.tomorrowli.community.community.service.imp;

import com.tomorrowli.community.community.dao.User;
import com.tomorrowli.community.community.mapper.UserMapper;
import com.tomorrowli.community.community.service.RegisterServices;
import com.tomorrowli.community.community.util.CommunityConstant;
import com.tomorrowli.community.community.util.CommunityUtils;
import com.tomorrowli.community.community.util.SendMail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author: LiMing
 * @Date: 2020/3/3 19:28
 * @Description:
 **/
@Service
public class RegisterServicesImpl implements RegisterServices,CommunityConstant {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SendMail sendMail;

    @Value("${community.path}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();
        if(user==null){
            new IllegalArgumentException("参数不能为空！！");
        }
        if(StringUtils.isBlank(user.getUsername())){
            map.put("usernameMsg","账号不能为空！！");
            return map;
        }
        if(StringUtils.isBlank(user.getPassword())){
            map.put("passwordMsg","密码不能为空！！");
            return map;
        }
        if(StringUtils.isBlank(user.getEmail())){
            map.put("emailMsg","邮箱不能为空！！");
            return map;
        }
        //验证账户是否已经注册
        User u = userMapper.selectUserName(user.getUsername());
        if(u!=null){
            map.put("usernameMsg","账户已经被注册！！");
            return map;
        }
        u = userMapper.selectEmail(user.getEmail());
        if(u!=null){
            map.put("emailMsg","邮箱已经被注册！！");
            return map;
        }
        //注册用户
        user.setSalt(CommunityUtils.generateUUID().substring(0,5));
        user.setPassword(CommunityUtils.md5(user.getPassword()+user.getSalt()));
        user.setStatus(0);
        user.setType(0);
        user.setCreateTime(new Date());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setActivationCode(CommunityUtils.generateUUID());
        userMapper.insertUser(user);

        //发送邮件
        Context context = new Context();
        context.setVariable("email",user.getEmail());
        String url=domain+contextPath+"/activation/"+user.getId()+"/"+user.getActivationCode();
        context.setVariable("url",url);

        String process = templateEngine.process("/mail/activation", context);
        sendMail.sendEmail(user.getEmail(),"激活邮件",process);
        return map;

    }


    public int getActivationStatus(int userId,String code){
        User user = userMapper.selectUserById(userId);
        if(user.getStatus()==1){
            return ACTIVATION_REPEAT;
        }else if(user.getActivationCode().equals(code)){
            userMapper.updateStatus(userId,1);
            return ACTIVATION_SUCCESS;
        }else{
            return ACTIVATION_FAILURE;
        }

    }
}
