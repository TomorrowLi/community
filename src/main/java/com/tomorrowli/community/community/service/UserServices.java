package com.tomorrowli.community.community.service;

import com.tomorrowli.community.community.dao.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * @Author: LiMing
 * @Date: 2020/2/29 18:10
 * @Description:
 **/
@Service
public interface UserServices {
    User selectUserById(int userId);
}
