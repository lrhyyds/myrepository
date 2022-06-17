package com.nb.yyds220118.service.impl;

import com.nb.yyds220118.dao.IAdminComReplyDao;
import com.nb.yyds220118.pojo.CommentReply;
import com.nb.yyds220118.service.IAdminComreplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/22 14:41
 * @description：留言回复表Service
 */
@Service
public class AdminComreplyServiceImpl implements IAdminComreplyService {
    @Autowired
    IAdminComReplyDao iAdminComReplyDao;
    /*
    *增加留言内容
    * */
    @Override
    public int addcomreply(CommentReply commentReply) {
      int m=iAdminComReplyDao.addcomreply(commentReply);
        return m;
    }
/*
* 查询已回复的内容 根据cid 留言编号 ,uno
* */
    @Override
    public List<CommentReply> getallCommentReply(List<CommentReply> commentReplies) {
      List<CommentReply> list=   iAdminComReplyDao.getallCommentReply(commentReplies);
        return list;
    }
}
