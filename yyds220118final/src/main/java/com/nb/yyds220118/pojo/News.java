package com.nb.yyds220118.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


/**
 * @program: yimaishop
 * 描述：
 * @author: HH
 * @create: 2021-01-19 13:01
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {
    int nId;    //编号
    String nTitle;    //标题
    String nContent;    //内容
    Timestamp nCreatTime;  //录入日期
    String nnCreatTime;


    //
    int nestatus;//新闻的发布状态 1为未发布 2 是已发布 3不合格新闻

}



