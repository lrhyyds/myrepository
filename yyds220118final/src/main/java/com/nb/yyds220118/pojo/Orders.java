package com.nb.yyds220118.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.sql.Timestamp;

/**
 * @program: yimaishop
 * 描述：
 * @author: HH
 * @create: 2021-01-19 13:15
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    int oId;               // 订单编号
    int uNo;               //用户编号(下单人的编号)
    String oPhone;         // 收货人手机号
    String oName;          // 收货人姓名
    String oAddress;       // 收货人地址
    String  oCreatTime;  //  创建日期
    double oCost;          //  订单金额
    int oStatus;           //订单状态   1未发货，2已发货，3已退单
    String ooCreatTime;
    Users getUsers;
}



