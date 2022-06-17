package com.nb.yyds220118.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：javasun
 * @date ：Created in 2022/1/18 16:37
 * @description：
 */
@Data
public class Products2 {
    private  Integer pppage;//排名
    private Integer pid;//商品id
    private String  pname;//商品名称
    private BigDecimal pprice;//商品价格
    private BigDecimal pnum;//库存
    private String     pdesc;//商品说明
    private String     pfilename;//图片文件名
    private BigDecimal psoldnum;//销售数量
    private Integer tid;//类型id
    private BigDecimal pstatus;//状态
    List<ProductsType> productstype;
    private String tu;
}
