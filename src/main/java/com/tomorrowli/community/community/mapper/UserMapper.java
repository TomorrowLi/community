package com.tomorrowli.community.community.mapper;

import com.tomorrowli.community.community.dao.DiscussPost;
import com.tomorrowli.community.community.dao.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: LiMing
 * @Date: 2020/2/29 18:13
 * @Description:
 **/
@Mapper
public interface UserMapper {

    User selectUserById(@Param("userId") int userId);
}
