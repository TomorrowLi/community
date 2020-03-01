package com.tomorrowli.community.community.service.imp;

import com.github.pagehelper.PageHelper;
import com.tomorrowli.community.community.dao.DiscussPost;
import com.tomorrowli.community.community.mapper.DisCussPortMapper;
import com.tomorrowli.community.community.service.DiscussPortServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: LiMing
 * @Date: 2020/2/29 18:12
 * @Description:
 **/
@Service
public class DiscussPortServiceImpl implements DiscussPortServices {

    @Autowired
    private DisCussPortMapper disCussPortMapper;
    @Override
    public List<DiscussPost> selectAll(int userId,int page,int pageSize) {
        PageHelper.startPage(page,pageSize);
        return disCussPortMapper.selectAll(userId);
    }
}
