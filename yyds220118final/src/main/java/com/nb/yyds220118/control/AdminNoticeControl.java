package com.nb.yyds220118.control;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nb.yyds220118.pojo.Notice;
import com.nb.yyds220118.service.IAdminNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/20 13:39
 * @description：公告管理（做了批量删除）
 */
@RestController
public class AdminNoticeControl {
    @Autowired
    IAdminNoticeService iAdminNoticeService;
    /*
    * 查询对应审核状态的公告
    * */
    @RequestMapping("getnoticeBynostatus")
    PageInfo<Notice> getnoticeBynostatus(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int limit,
                                 @RequestParam(defaultValue = "1") int status){
        PageHelper.startPage(page,limit);
        List<Notice> list=iAdminNoticeService.getnoticeisbystatus(status);
        PageInfo<Notice> pageInfo=new PageInfo<>(list);
        return  pageInfo;
    }
    /*
    * 修改公告审核
    * */
    @RequestMapping("updatenostatus")
    int updatenostatus(int status ,Integer noId){
        Notice notice=new Notice();
        notice.setNoId(noId);
        notice.setNostatus(status);
      int sign=  iAdminNoticeService.updatenoticeBynoid(notice);
      return sign;
    }
    //批量删除公告（用请求参数接收数组）
    @RequestMapping("delbatchnotice")
    int delbatchnotice(HttpServletRequest request){
        //inpvaule[]
        String[] arr=request.getParameterValues("inpvaule[]");//用请求方式接收数组
        List<Integer> inplist=new ArrayList<>();
        if (arr==null){
            System.out.println("数组为空");
        }else {
            System.out.println("数组："+arr);
            for(String  s:arr){
                System.out.println(s);
                inplist.add(Integer.parseInt(s));
            }
        }
        int sign=  iAdminNoticeService.delbatchnotice(inplist);
        return sign;
    }
}
