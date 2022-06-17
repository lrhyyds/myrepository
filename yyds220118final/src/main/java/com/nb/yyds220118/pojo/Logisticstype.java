package com.nb.yyds220118.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/23 19:25
 * @description：物流表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Logisticstype {
    int logid;//物流公司id
    String logname;//物流公司名称
}
