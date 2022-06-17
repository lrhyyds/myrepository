package com.nb.yyds220118.control;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nb.yyds220118.pojo.CommentReply;
import com.nb.yyds220118.pojo.Comments;
import com.nb.yyds220118.pojo.News;
import com.nb.yyds220118.service.IAdminCommentService;
import com.nb.yyds220118.service.IAdminComreplyService;
import com.sun.tools.internal.xjc.model.CDefaultValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.tools.jconsole.JConsole;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/22 14:43
 * @description：留言回复
 */
@RestController
public class AdminComreplyControl {
    @Autowired
    IAdminComreplyService iAdminComreplyService;

    /*
     * 查询已回复的内容 根据cid 留言编号 ,uno
     * */
@Autowired
    IAdminCommentService iAdminCommentService;
    @RequestMapping("getadmincomreply")
    PageInfo<Comments> getadmincomreply(@RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "10") int limit,
                                            @RequestParam(defaultValue = "1") int status
                                             ){

        PageHelper.startPage(page, limit);
        List<Comments> list=iAdminCommentService.getcommentsBystatus(status);
        PageInfo<Comments> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }
    /*
    * 增加回复内容，从前端要得到 cid ,uno，ccontent
    * 1.增加回复内容到回复表中
    * 2.修改留言的状态为已回复
    * */
   @RequestMapping("adminaddcomreply")
    int adminaddcomreply(CommentReply commentReply){
       System.out.println("进入adminaddcomreply");
       System.out.println("commentReply:"+commentReply);
       Date date=new Date();
       Timestamp timestamp=new Timestamp(date.getTime());//得到回复时间存入留言回复表
       commentReply.setRTime(timestamp);//把得到的时间戳赋给对象属性
     int sign=  iAdminComreplyService.addcomreply(commentReply);//将改回复对象存入留言回复表
     Comments comments=new Comments();
     comments.setCId(commentReply.getCId());//得到留言编号
     comments.setReplystatus(2);//修改状态为已回复
     comments.setUNo(commentReply.getUNo());//得到用户编号
     int sstatus=iAdminCommentService.update(comments);//修改留言状态为已回复
     if(sstatus!=0){
         System.out.println("修改失败");
     }
     return sign;
   }
}
