package com.nb.yyds220118.control;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nb.yyds220118.pojo.Orders;
import com.nb.yyds220118.pojo.Users;
import com.nb.yyds220118.service.IAdminOrderService;
import com.sun.corba.se.impl.interceptors.PICurrent;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/19 11:24
 * @description：订单管理
 */
@RestController
public class AdminOrderControl {
    @Autowired
    IAdminOrderService iAdminOrderService;
    /*
    * 用户个人订单查询
    * @RequestParam(defaultValue = "1") Integer status 为1默认是查询所有未发货的订单
    * */
    @RequestMapping("usergetorders")
    public PageInfo<Orders> getorders(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer limit,
                                      Integer status){
        Subject subject= SecurityUtils.getSubject();//得到当前登录的用户
        Users getnowuser= (Users) subject.getPrincipal();
        Integer uno=getnowuser.getUNo();
        System.out.println("当前登录的用户uno:"+uno);
        PageHelper.startPage(page,limit);
        List<Orders> list=iAdminOrderService.getAllOrders(status,uno);
        PageInfo<Orders> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }
    /*
    * 修改订单状态
    * */
    @RequestMapping("adminUpdateostatus")
    int updateOtatus(Orders orders){
        HttpServletRequest httpServletRequest=
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        int getostatus=orders.getOStatus();
        int getoid=orders.getOId();
       int sign= iAdminOrderService.updateOstastus(getostatus,getoid);
        return sign;
    }

    //批量删除订单（用请求参数接收数组）
    @RequestMapping("delbatchorders")
    int delbatchorders(HttpServletRequest request){
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
        int sign=  iAdminOrderService.delbatchorders(inplist);
        return sign;
    }
    //根据id删除订单
    int delordersbynid(HttpServletRequest request){
        String getnid=request.getParameter("");
        return 0;
    }
    /*
    * 订单查询
    * 查询条件：订单日期范围或者订单状态或用户uno
    * 根据oid模糊查询
    * */
    @RequestMapping("getorderBydate")
    PageInfo<Orders> getorderBydate(HttpServletRequest request){
        System.out.println("进入getoderBydate");
        //得到开始日期范围
        String getstartdate=request.getParameter("startdate");//开始日期
        //得到截止日期范围
        String getenddate=request.getParameter("enddate");//结束日期
        //得到要查询的订单状态
        String getstatus=request.getParameter("status");//状态
        Integer status=null;
        try {
            status=Integer.parseInt(getstatus);
        }catch (NumberFormatException e){
            e.printStackTrace();
            status=4;
            System.out.println("捕获到字符不匹配异常：将status设为4，查所有订单");
        }
        //得到当前页
        String getpage=request.getParameter("page");//开始页面
        Integer page=null;
        try {
            page=Integer.parseInt(getpage);
        }catch (NumberFormatException e){
            e.printStackTrace();
            page=1;
            System.out.println("匹配到字符不匹配异常：将起始page设为1，从第一个开始查订单");
        }
        //得到用户id
        String getuno=request.getParameter("uno");
        Integer uno=null;
        try {
            uno=Integer.parseInt(getuno);
        }catch (NumberFormatException e){
            e.printStackTrace();
            System.out.println("NumberFormatException：用户为空，数字转换异常");
        }
        //得到模糊查询的参数
        String str=request.getParameter("vauleinput");
        //打印获得的参数
        System.out.println("开始时间："+getstartdate);
        System.out.println("截止时间："+getenddate);
        System.out.println("得到状态为："+status);
        System.out.println("获得的用户编号为："+uno);
        System.out.println("获得的模糊参数为str："+str);
        PageHelper.startPage(page,10);
         List<Orders> list= iAdminOrderService.getordersBydate(getstartdate,getenddate,status,str,uno);
         PageInfo<Orders> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }
}
