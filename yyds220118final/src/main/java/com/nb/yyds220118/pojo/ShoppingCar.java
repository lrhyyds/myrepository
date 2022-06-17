package com.nb.yyds220118.pojo;/*
 *@author GJC
 *@date 2021/1/21 15:25
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*购物车*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCar {
    int carid;//购物车编号
    int uNo;   //用户ID
    int pId;   //商品ID
    int uPnum; //商品数量
}
