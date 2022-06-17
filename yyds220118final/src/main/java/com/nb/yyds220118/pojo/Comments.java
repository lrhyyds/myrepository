package com.nb.yyds220118.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @program: yimaishop
 * 描述：
 * @author: HH
 * @create: 2021-01-19 13:03
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments {
    int cId;                //编号
    int uNo;                //用户编号
    String cContent;        //内容
   String cCreatTime;   //创建时间
    CommentReply commentReply;//关联的回复内容
    int replystatus;//留言状态 1：未回复 2.已回复 3.屏蔽状态
    String uName;//用户名字
    String rTime;    //回复时间
    String rContent;    //回复内容
}



