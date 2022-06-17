package com.nb.yyds220118.dao;

import com.nb.yyds220118.pojo.Comments;

import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/22 14:32
 * @description：
 */
public interface IAdmincommentDao {
    //根据回复状态查询要恢复的内容 管理员查状态为1的留言，用户先查所有留言，根据留言的id关联查询已回复的内容
    List<Comments> getcommentsBystatus(Comments comments);
    int updatecomstatus(Comments comments);//管理员，修改回复状态为2
    //用户查询所有留言，关联查询回复表中对应的回复内容
    List<Comments> getallcomments();//cid uno


}
