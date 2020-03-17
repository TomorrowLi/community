package com.tomorrowli.community.community.service.imp;

import com.github.pagehelper.PageHelper;
import com.tomorrowli.community.community.dao.DiscussPost;
import com.tomorrowli.community.community.mapper.DisCussPortMapper;
import com.tomorrowli.community.community.service.DiscussPortServices;
import com.tomorrowli.community.community.util.HostHolder;
import com.tomorrowli.community.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import org.thymeleaf.TemplateEngine;

import java.util.Date;
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

    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Override
    public List<DiscussPost> selectAll(int userId,int offset,int limit) {
        //PageHelper.startPage(page,pageSize);
        return disCussPortMapper.selectAll(userId,offset,limit);
    }

    @Override
    public int insertDiscussPost(DiscussPost discussPost) {

        //先对文本和类容进行特殊字符过滤
        discussPost.setTitle(HtmlUtils.htmlEscapeDecimal(discussPost.getTitle()));
        discussPost.setContent(HtmlUtils.htmlEscapeDecimal(discussPost.getContent()));

        //然后进行敏感词过滤
        discussPost.setTitle(sensitiveFilter.filter(discussPost.getTitle()));
        discussPost.setContent(sensitiveFilter.filter(discussPost.getContent()));
        return  disCussPortMapper.insertDisCussport(discussPost);
    }

    @Override
    public DiscussPost selectDiscussDetail(int discussId) {
        return disCussPortMapper.selectDiscussDetail(discussId);
    }

    @Override
    public int findDiscussPostRows(int userId) {
        return disCussPortMapper.selectDiscussPostRows(userId);
    }
}
