package com.nb.yyds220118.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: yimaishop
 * 描述：
 * @author: HH
 * @create: 2021-01-19 12:55
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress {
    int aId;            //收获地址编号
    int uNo;              //用户编号
    String uConsignee;  //收货人
    String uReceivingPhone;  //收货手机号
    String uAddress;    //地址
}



