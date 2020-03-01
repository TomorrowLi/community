package com.tomorrowli.community.community.UserMapperTest;

import com.tomorrowli.community.community.CommunityApplication;
import com.tomorrowli.community.community.dao.DiscussPost;
import com.tomorrowli.community.community.dao.User;
import com.tomorrowli.community.community.mapper.DisCussPortMapper;
import com.tomorrowli.community.community.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sun.applet.Main;

import java.util.List;

/**
 * @Author: LiMing
 * @Date: 2020/2/29 20:44
 * @Description:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class UserTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DisCussPortMapper disCussPortMapper;


    @Test
    public void userTest1(){
        User user = userMapper.selectUserById(1);
        System.out.println(user.toString());
    }


    @Test
    public void disCussPostTest(){
        List<DiscussPost> discussPosts = disCussPortMapper.selectAll(0);
        for (DiscussPost discussPost : discussPosts) {
            System.out.println(discussPost.toString());
        }

    }
}
