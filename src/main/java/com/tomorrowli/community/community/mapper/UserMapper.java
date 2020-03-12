package com.tomorrowli.community.community.mapper;

import com.tomorrowli.community.community.dao.DiscussPost;
import com.tomorrowli.community.community.dao.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author: LiMing
 * @Date: 2020/2/29 18:13
 * @Description:
 **/
@Mapper
public interface UserMapper {

    User selectUserById(@Param("userId") int userId);

    User selectUserName(@Param("username") String username);

    User selectEmail(@Param("email") String email);

    void insertUser(User user);

    void updateStatus(@Param("userId") int userId,@Param("status") int status);

    void updateHeaderUrl(@Param("userId") int userId,@Param("headerUrl") String headerUrl);

    void updatePassword(@Param("userId") int userId, @Param("password") String password);
}
