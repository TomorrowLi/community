package com.tomorrowli.community.community.controller;

import com.tomorrowli.community.community.dao.DiscussPost;
import com.tomorrowli.community.community.dao.User;
import com.tomorrowli.community.community.mapper.DisCussPortMapper;
import com.tomorrowli.community.community.mapper.UserMapper;
import com.tomorrowli.community.community.service.DiscussPortServices;
import com.tomorrowli.community.community.service.UserServices;
import com.tomorrowli.community.community.service.imp.DiscussPortServiceImpl;
import com.tomorrowli.community.community.util.CommunityUtils;
import com.tomorrowli.community.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @Author: LiMing
 * @Date: 2020/2/29 18:10
 * @Description:
 **/

@Controller
public class DiscussPortController {


    @Autowired
    private DiscussPortServices discussPortServices;

    @Autowired
    private UserServices userServices;

    @Autowired
    private HostHolder hostHolder;


    @RequestMapping(path = "/index",method = RequestMethod.GET)
    public String getDiscussPost(Model model){
        List<DiscussPost> discussPosts = discussPortServices.selectAll(0,1,10);
        List<Map<String, Object>> list = new ArrayList<>();
        for (DiscussPost discussPost : discussPosts) {
            Map<String, Object> map = new HashMap<>();
            map.put("user",userServices.selectUserById(discussPost.getUserId()));
            map.put("post",discussPost);
            list.add(map);
        }
        model.addAttribute("disPost",list);
        return "/index";
    }


    @RequestMapping(value = "/discuss/add",method = RequestMethod.POST)
    @ResponseBody
    public String addDiscussPort(String title,String content ){

        User user = hostHolder.getUser();
        if(user==null){
            return CommunityUtils.getJSONObject(403,"你还没有登录！");
        }

        DiscussPost discussPost = new DiscussPost();
        discussPost.setUserId(user.getId());
        discussPost.setTitle(title);
        discussPost.setContent(content);
        discussPost.setCreateTime(new Date());
        discussPost.setCommentCount(0);
        discussPost.setScore(0D);
        discussPost.setStatus(0);
        discussPost.setType(0);

        discussPortServices.insertDiscussPost(discussPost);
        return CommunityUtils.getJSONObject(0,"发布成功");
    }
}
