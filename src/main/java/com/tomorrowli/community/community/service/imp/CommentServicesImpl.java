package com.tomorrowli.community.community.service.imp;

import com.tomorrowli.community.community.dao.Comment;
import com.tomorrowli.community.community.mapper.CommentMapper;
import com.tomorrowli.community.community.service.CommentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: LiMing
 * @Date: 2020/3/17 14:53
 * @Description:
 **/
@Service
public class CommentServicesImpl implements CommentServices {


    @Autowired
    private CommentMapper commentMapper;


    @Override
    public List<Comment> slelctCommentList(int entityType, int entityId, int offset, int limit) {
        return commentMapper.slelctCommentList(entityType,entityId,offset,limit);
    }

    @Override
    public int selectCommuntCount(int entityType, int entityId) {
        return commentMapper.selectCommuntCount(entityType,entityId);
    }
}
