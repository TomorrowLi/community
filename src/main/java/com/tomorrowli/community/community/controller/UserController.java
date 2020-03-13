package com.tomorrowli.community.community.controller;

import com.tomorrowli.community.community.annoation.LoginRequired;
import com.tomorrowli.community.community.dao.User;
import com.tomorrowli.community.community.service.imp.UserServicesImpl;
import com.tomorrowli.community.community.util.CommunityUtils;
import com.tomorrowli.community.community.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * @Author: LiMing
 * @Date: 2020/2/29 18:09
 * @Description:
 **/
@Controller
@RequestMapping(value = "/user")
public class UserController {


    private static final Logger logger= LoggerFactory.getLogger(UserController.class);


    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${community.path}")
    private String domain;

    @Value("${community.path.upload}")
    private String uploadLoaclation;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserServicesImpl userServices;

    @LoginRequired
    @RequestMapping(value = "/setting",method = RequestMethod.GET)
    public String getSettingPage(){
        return "/site/setting";
    }

    @RequestMapping(value = "/upload")
    @LoginRequired
    public String upload(Model model, MultipartFile handerImage){

        if(handerImage==null){
            model.addAttribute("error","你还没有选择文件");
            return "/site/setting";
        }
        String filename = handerImage.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        if(StringUtils.isBlank(suffix)|| !suffix.equalsIgnoreCase(".png")){
            model.addAttribute("error","图片格式有问题,系统只支持png格式的图片");
            return "/site/setting";
        }

        //生成随机的图片名字
        filename= CommunityUtils.generateUUID()+suffix;
        //存储文件
        File file = new File(uploadLoaclation + "/" + filename);
        try {
            handerImage.transferTo(file);
        } catch (IOException e) {
            logger.error("文件上传失败！",e.getMessage());
            new RuntimeException("上传文件失败，服务器异常",e);
        }

        //改变user的handerUrl
        String headerUrl=domain+contextPath+"/user/header/"+filename;
        userServices.uploadHeaderUrl(hostHolder.getUser().getId(),headerUrl);

        return "redirect:/index";
    }


    @RequestMapping(value = "/header/{filename}",method = RequestMethod.GET)
    public void getHeaderImage(HttpServletResponse response, @PathVariable("filename") String filename){

       String fileLocation=uploadLoaclation+"/"+filename;

        String suffix = filename.substring(filename.lastIndexOf("."));

        response.setContentType("image/"+suffix);

        try (
                FileInputStream fis = new FileInputStream(fileLocation);
                OutputStream os = response.getOutputStream();
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        }
            catch (IOException e) {
           logger.error("获取头像失败！",e.getMessage());
           new RuntimeException("头像加载失败",e);
        }


    }

    @LoginRequired
    @RequestMapping(value = "/updatePassword")
    public String updatePassword(String oldpassword,String newpassword,Model model){

        User user = hostHolder.getUser();
        if(StringUtils.isBlank(oldpassword)){
            model.addAttribute("oldpasswordMsg","原始密码不能为空！");
            return "/site/setting";
        }
        String pass = CommunityUtils.md5(oldpassword + user.getSalt());
        if(!pass.equals(user.getPassword())){
            model.addAttribute("oldpasswordMsg","原始密码不正确！");
            return "/site/setting";
        }
        if(StringUtils.isBlank(newpassword)){
            model.addAttribute("newpasswordMsg","原始密码不能为空！");
            return "/site/setting";
        }
        userServices.updatepassword(user.getId(),newpassword);
        return "redirect:/index";

    }



}
