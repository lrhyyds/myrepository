package com.nb.yyds220118.dao;

import com.nb.yyds220118.pojo.CommentReply;
import com.nb.yyds220118.pojo.News;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/22 12:41
 * @description：
 */
public interface IAdminComReplyDao {
    //查询已回复的内容 根据cid 留言编号
    List<CommentReply> getallCommentReply(@Param("commentReplies") List<CommentReply> commentReplies);
    int addcomreply(CommentReply commentReply);//增加回复
    String getComrepBycid(int cid);//根据留言编号查询回复内容

}
