package com.tomorrowli.community.community.service;

import com.tomorrowli.community.community.dao.User;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: LiMing
 * @Date: 2020/3/3 19:27
 * @Description:
 **/
@Service
public interface RegisterServices {

    Map<String,Object> register(User user);

    int getActivationStatus(int userId,String code);
}
