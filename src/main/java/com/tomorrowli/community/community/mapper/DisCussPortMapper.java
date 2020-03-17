package com.tomorrowli.community.community.mapper;

import com.tomorrowli.community.community.dao.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: LiMing
 * @Date: 2020/2/29 18:14
 * @Description:
 **/
@Mapper
public interface DisCussPortMapper {

    List<DiscussPost> selectAll(@Param("userId") int userId,@Param("offset") int offset,@Param("limit") int limit);

    int insertDisCussport(DiscussPost discussPost);

    DiscussPost selectDiscussDetail(@Param("discussId") int discussId);

    int selectDiscussPostRows(@Param("userId") int userId);
}
