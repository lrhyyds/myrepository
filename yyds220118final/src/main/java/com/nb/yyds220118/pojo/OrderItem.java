package com.nb.yyds220118.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: yimaishop
 * 描述：
 * @author: HH
 * @create: 2021-01-19 13:17
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    int oId;                 //订单编号
    int oNum;                //商品数量
    double oMoney;           //商品金额
    int pId;                 //商品编号
    List<Products> getItemProducts;
    Users getIName;
}



