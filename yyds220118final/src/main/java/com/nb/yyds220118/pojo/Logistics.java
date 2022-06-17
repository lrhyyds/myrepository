package com.nb.yyds220118.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/19 12:25
 * @description：物流信息表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Logistics {
    String loginum;//物流单号
    Integer oId;//订单编号
    String loginame;//物流公司名称
    String logdate;//发货时间
    String oName;//收货人
}
