package com.tomorrowli.community.community.controller;

import com.tomorrowli.community.community.dao.User;
import com.tomorrowli.community.community.mapper.UserMapper;
import com.tomorrowli.community.community.service.RegisterServices;
import com.tomorrowli.community.community.util.CommunityConstant;
import com.tomorrowli.community.community.util.CommunityUtils;
import com.tomorrowli.community.community.util.SendMail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author: LiMing
 * @Date: 2020/3/3 17:00
 * @Description:
 **/
@Controller
public class LoginController implements CommunityConstant{

    @Autowired
    private RegisterServices registerServices;

    @RequestMapping(value = "/register" ,method = RequestMethod.GET)
    public String getRegisterPage(){
        return "/site/register";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String loginPage(){
        return "/site/login";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(Model model,User user){
        Map<String, Object> register = registerServices.register(user);

        if(register==null || register.isEmpty()){
            model.addAttribute("msg","我们已经向你发送了一封邮件，请前去激活");
            model.addAttribute("target","/index");
            return "/site/operate-result";
        }else{
            model.addAttribute("usernameMsg",register.get("usernameMsg"));
            model.addAttribute("passwordMsg",register.get("passwordMsg"));
            model.addAttribute("emailMsg",register.get("emailMsg"));
            return "site/register";
        }
    }

    @RequestMapping(value = "/activation/{userId}/{code}",method = RequestMethod.GET)
    public String activation(Model model, @PathVariable("userId") int userId, @PathVariable("code") String code){
        int status = registerServices.getActivationStatus(userId, code);
        if(status==ACTIVATION_SUCCESS){
            model.addAttribute("msg","你已经成功激活，请前往登录！");
            model.addAttribute("target","/login");
        }else if(status==ACTIVATION_REPEAT){
            model.addAttribute("msg","激活无效，您已经激活过了");
            model.addAttribute("target","/index");
        }else{
            model.addAttribute("msg","激活失败，激活码是错误的");
            model.addAttribute("target","/index");

        }

        return "/site/operate-result";
    }


}
