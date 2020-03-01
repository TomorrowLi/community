package com.tomorrowli.community.community.service.imp;

import com.tomorrowli.community.community.dao.User;
import com.tomorrowli.community.community.mapper.DisCussPortMapper;
import com.tomorrowli.community.community.mapper.UserMapper;
import com.tomorrowli.community.community.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public User selectUserById(int userId) {
        return userMapper.selectUserById(userId);
    }
}
