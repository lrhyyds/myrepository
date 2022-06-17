package com.nb.yyds220118.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @program: yimaishop
 * 描述：
 * @author: HH
 * @create: 2021-01-19 13:06
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    int noId;    //编号
    int uno; //用户id
    String noTitle;    //标题
    String noContent;  //内容
    Timestamp noCreatTime;  //录入日期
    int nostatus;//公告审核 1为审核 2为审核通过 3为审核不通过
    String nnoCreatTime;//审核时间
}



