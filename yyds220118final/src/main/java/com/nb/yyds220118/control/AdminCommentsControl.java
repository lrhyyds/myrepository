package com.nb.yyds220118.control;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nb.yyds220118.pojo.Comments;
import com.nb.yyds220118.service.IAdminCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.events.Comment;
import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/22 15:36
 * @description：留言表
 */
@RestController
public class AdminCommentsControl {
    @Autowired
    IAdminCommentService iAdminCommentService;
    /*
    * 管理员得到未回复的内容
    * */
    @RequestMapping("admingetcommentbystatus")
  PageInfo<Comments> admingetcommentbystatus(@RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "10") int limit,
                                                int status){
        System.out.println("请求回复内容的状态："+status);
        PageHelper.startPage(page, limit);
        List<Comments> list=iAdminCommentService.getcommentsBystatus(status);
        PageInfo<Comments> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }
   @RequestMapping("updatereplystatus")
    int updatrestatus(Integer replystatus,Integer cId){

       Comments comments=new Comments();
       comments.setReplystatus(replystatus);
       comments.setCId(cId);
      int sign= iAdminCommentService.update(comments);
      return sign;
   }
}
