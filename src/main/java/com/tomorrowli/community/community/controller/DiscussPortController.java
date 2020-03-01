package com.tomorrowli.community.community.controller;

import com.tomorrowli.community.community.dao.DiscussPost;
import com.tomorrowli.community.community.mapper.DisCussPortMapper;
import com.tomorrowli.community.community.mapper.UserMapper;
import com.tomorrowli.community.community.service.DiscussPortServices;
import com.tomorrowli.community.community.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: LiMing
 * @Date: 2020/2/29 18:10
 * @Description:
 **/

@Controller
@RequestMapping(path = "/disPost")
public class DiscussPortController {


    @Autowired
    private DiscussPortServices discussPortServices;

    @Autowired
    private UserServices userServices;

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
}
