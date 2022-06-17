package com.nb.yyds220118.service;

import com.nb.yyds220118.pojo.CommentReply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/22 14:40
 * @description：
 */
public interface IAdminComreplyService {
    int addcomreply(CommentReply commentReply);//增加回复内容
    List<CommentReply> getallCommentReply(@Param("unolist") List<CommentReply> commentReplies);//根据cid查询已回复的内容
}
