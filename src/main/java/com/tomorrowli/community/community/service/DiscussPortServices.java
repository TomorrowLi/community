package com.tomorrowli.community.community.service;

import com.tomorrowli.community.community.dao.DiscussPost;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: LiMing
 * @Date: 2020/2/29 18:11
 * @Description:
 **/
@Service
public interface DiscussPortServices {
    List<DiscussPost> selectAll(int userId,int page,int pageSize);

    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussDetail(String discussId);

}
