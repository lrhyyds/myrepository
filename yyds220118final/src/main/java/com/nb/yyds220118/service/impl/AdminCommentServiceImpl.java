package com.nb.yyds220118.service.impl;

import com.nb.yyds220118.dao.IAdmincommentDao;
import com.nb.yyds220118.pojo.Comments;
import com.nb.yyds220118.service.IAdminCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/22 15:21
 * @description：针对留言表
 */
@Service
public class AdminCommentServiceImpl implements IAdminCommentService {
    @Autowired
    IAdmincommentDao iAdmincommentDao;
    //根据回复状态查询要恢复的内容 管理员查状态为1的留言，用户先查所有留言，根据留言的id关联查询已回复的内容
    @Override
    public List<Comments> getcommentsBystatus(int status) {
        Comments comments=new Comments();
        comments.setReplystatus(status);
      List<Comments> list=  iAdmincommentDao.getcommentsBystatus(comments);
        return list;
    }
    //管理员，修改回复状态为2
    @Override
    public int update(Comments comments) {
       int m= iAdmincommentDao.updatecomstatus(comments);
        return m;
    }
    //用户查询所有留言，关联查询回复表中对应的回复内容
    @Override
    public List<Comments> getallcomments() {
        return null;
    }
}
