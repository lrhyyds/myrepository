package com.nb.yyds220118.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: yimaishop
 * 描述：
 * @author: HH
 * @create: 2021-01-19 13:08
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Products {

    //刘同学的实体类
    int pId;               //--商品编号
    int carid;//购物车id
    String pName ;         //--商品名称
    double pPrice ;        //-- 商品价格
    int pNum;              //--商品库存
    String pDesc;          //--商品描述
    String pFileName;      //--文件(用来存放商品图片)
    int pSoldnum;          //--销量
    int tId;               //--商品类型id
    int pStatus;           //状态   1：上架中  2：已下架
    String tName;
    Integer userpaynum;    //用户购买的数量


}


///
