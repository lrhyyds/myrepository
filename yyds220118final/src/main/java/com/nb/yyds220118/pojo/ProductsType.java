package com.nb.yyds220118.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author ：javasun
 * @date ：Created in 2022/1/18 18:47
 * @description：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsType {
    private  Integer    tid;//类型id
    private String      tname;//商品类型
    private BigDecimal  tisparenttype;//是否为父类，是为1，否为0
    private BigDecimal  tparenttypeid;//父类编号
    private  String parentname;
}



