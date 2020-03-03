package com.tomorrowli.community.community.UserMapperTest;

import com.tomorrowli.community.community.CommunityApplication;
import com.tomorrowli.community.community.util.SendMail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sun.awt.SunHints;

/**
 * @Author: LiMing
 * @Date: 2020/3/3 11:42
 * @Description:
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CommunityApplication.class)
public class EmailTest {

    @Autowired
    private SendMail sendMail;

    @Test
    public void sendMailTest(){
        sendMail.sendEmail("tomorrowli123@sina.com","牛客","你好");
    }
}
