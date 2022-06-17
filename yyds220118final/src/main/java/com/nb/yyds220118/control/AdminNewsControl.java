package com.nb.yyds220118.control;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nb.yyds220118.pojo.News;
import com.nb.yyds220118.service.IAdminNewsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/20 22:29
 * @description：新闻管理（做了批量删除）
 */
@RestController
public class AdminNewsControl {
    @Autowired
    IAdminNewsService iAdminNewsService;
    //查询对应发布状态的新闻
    @RequestMapping("getnewsBynestatus")
    PageInfo<News> getnews(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "10") int limit,
                            @RequestParam(defaultValue = "1") int status){
        PageHelper.startPage(page,limit);
        List<News> list=iAdminNewsService.getnewsBynestatus(status);
        PageInfo<News> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }
    //增加新闻
    @RequestMapping("addnews")
    int addnews(News news){
       if(news.getNestatus()==0){
           news.setNestatus(1);
       }if(news.getNCreatTime()==null){
           Date date=new Date();
          long time= date.getTime();
          news.setNCreatTime(new Timestamp(time));
        }
      int sign= iAdminNewsService.addnews(news);
      return sign;
    }
    //修改发布状态
    @RequestMapping("updatenestatus")
    int updatestatus(int status,int nid){
        News news=new News();
        news.setNId(nid);
        news.setNestatus(status);
        int sign= iAdminNewsService.updatenews(news);
        return sign;
    }

/*   //批量删除新闻(通过前端转换成json字符串接收)
    @RequestMapping("delbatchnews")
    int delbatchnews( String inpvaule){
        List<Integer> inplist=new ArrayList<>();
        if (inpvaule==null){
            System.out.println("数组为空");
        }else {
            System.out.println("数组："+inpvaule);
            String[] str=inpvaule.split(",");
            for (String s:str
                 ) {
                String st=s.substring(s.indexOf("\"")+1,s.lastIndexOf("\""));
                inplist.add(Integer.parseInt(st));
            }
        }
        System.out.println(inpvaule);
      int sign=  iAdminNewsService.delbatchnews(inplist);
        return sign;
    }*/
    //批量删除新闻（用请求参数接收数组）
    @RequestMapping("delbatchnews")
    int delbatchnews(HttpServletRequest request){
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
        int sign=  iAdminNewsService.delbatchnews(inplist);
        return sign;
    }
}
