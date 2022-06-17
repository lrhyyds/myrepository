package com.nb.yyds220118.control;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nb.yyds220118.pojo.Logistics;
import com.nb.yyds220118.service.IAdminLogisticsService;
import com.nb.yyds220118.service.IAdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/19 12:41
 * @description：物流
 */
@RestController
public class AdminLogisticsControl {
    @Autowired
    IAdminLogisticsService iAdminLogisticsService;
   /*
   *查询所有物流信息,返回pageInfo
    */
    @RequestMapping("getAllLogiInfo")
    PageInfo<Logistics> getAllLogiInfo(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer limit){
        PageHelper.startPage(page,limit);
        List<Logistics> logistics=iAdminLogisticsService.getAllLogis();
        PageInfo<Logistics> pageInfo=new PageInfo<>(logistics);
        return pageInfo;
    }
    /*
    * 增加物流信息
    * */
    @Autowired
    IAdminOrderService iAdminOrderService;
    @RequestMapping("addLogistics")
    int addlogistics(Logistics logistics){
        Timestamp timestamp=new Timestamp(new Date().getTime());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String datestr=sdf.format(timestamp);//用时间作为物流编号的前缀
        System.out.println("得到的订单编号："+logistics.getOId());
        String oidstr=String.valueOf(logistics.getOId());//获取订单编号转成字符串作为物流编号的后缀
        logistics.setLoginum(datestr+oidstr);//设置物流单号=时间字符串+订单编号
        SimpleDateFormat sdflog=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String logdate=sdflog.format(timestamp);//发货时间按指定格式显示
        logistics.setLogdate(logdate);//设置发货时间
        System.out.println("物流单号为"+logistics.getLoginum());
       int sign= iAdminLogisticsService.addLogistics(logistics);
       if(sign!=0){
         int mm=  iAdminOrderService.updateOstastus(2,logistics.getOId());//修改订单状态 1为未发货，2 为已发货
       }
       return sign;
    }
   /*
   * 根据订单信息查询物流信息
   * */
   @RequestMapping("getlogInfo")
   List<Logistics> getlogInfo(HttpServletRequest request){
       String getoid=request.getParameter("oId");
       System.out.println("前端传的oId："+getoid);
       int oId=Integer.parseInt(getoid);
      return iAdminLogisticsService.getlogInfo(oId);
   }
}
