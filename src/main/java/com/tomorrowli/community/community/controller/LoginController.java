package com.tomorrowli.community.community.controller;

import com.google.code.kaptcha.Producer;
import com.tomorrowli.community.community.dao.User;
import com.tomorrowli.community.community.mapper.UserMapper;
import com.tomorrowli.community.community.service.RegisterServices;
import com.tomorrowli.community.community.service.UserServices;
import com.tomorrowli.community.community.service.imp.UserServicesImpl;
import com.tomorrowli.community.community.util.CommunityConstant;
import com.tomorrowli.community.community.util.CommunityUtils;
import com.tomorrowli.community.community.util.SendMail;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
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

    @Autowired
    private Producer producerKaptcha;

    @Autowired
    private UserServices userServices;


    @Value("${server.servlet.context-path}")
    private String contextPath;

    private static final Logger logger= LoggerFactory.getLogger(LoginController.class);

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


    @RequestMapping(value = "/kaptcha",method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response, HttpSession session){

        //生成验证码
        String text = producerKaptcha.createText();
        BufferedImage image = producerKaptcha.createImage(text);

        //将验证码写入session
        session.setAttribute("kaptchacode",text);

        //将图片响应给浏览器
        response.setContentType("image/png");

        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image,"png",outputStream);
        } catch (IOException e) {
            logger.error("验证码生成失败！",e.getMessage());
        }

    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String username,String password,Model model, String code,boolean remember,HttpSession session,HttpServletResponse response){

        String kaptchacode = (String) session.getAttribute("kaptchacode");

        //先判断验证码
        if(StringUtils.isBlank(kaptchacode) || StringUtils.isBlank(code)|| !kaptchacode.equalsIgnoreCase(code)){
            model.addAttribute("codeMsg","验证码不正确！！");
            return "/site/login";
        }
        int expried=remember?REMEMBER_EXPIRED_TIME:DEFAULT_EXPIRED_TIME;

        Map<String, Object> map = userServices.login(username, password, expried);

        if(map.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
            cookie.setPath(contextPath);
            cookie.setMaxAge(expried);
            response.addCookie(cookie);
            return "redirect:/index";
        }else {
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            return "/site/login";
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket){
        userServices.logout(ticket);
        return "redirect:/index";
    }

}
