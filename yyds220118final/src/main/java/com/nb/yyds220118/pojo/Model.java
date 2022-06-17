package com.nb.yyds220118.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: yimaishop
 * 描述：
 * @author: HH
 * @create: 2021-01-19 12:59
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Model {
    int modelId;      //模块ID
    String modelName;   //模块名称
    String url;    //模块地址
    int parentModId;  //父模块ID
    int isParent;    //子父模块标识,0是父模块，1是子模块
    int status;    //状态0启用，1冻结
    String perms ;   //权限标识
}



