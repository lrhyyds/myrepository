package com.nb.yyds220118.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @program: yimaishop
 * 描述：
 * @author: HH
 * @create: 2021-01-19 13:04
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentReply {
    int crId;           //留言编号
    int cId;            //编号
    Date rTime;    //回复时间
    int uNo;            //回复人用户编号
    List<Users> getReplysByUsers;
    Users users;
    String cContent;        //被回复留言的内容
    String rContent;    //回复内容
}



