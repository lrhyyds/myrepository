package com.nb.yyds220118.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * @program: yimaishop
 * 描述：
 * @author: HH
 * @create: 2021-01-19 12:53
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {
   int  uNo;                  //用户编号
   String  uSex   ;           // 性别
   @DateTimeFormat(pattern="yyyy-MM-dd")
   @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
   Date uBirthday ;      //出生日期
   String uIdentityCode;      //身份证号
   double uMoney;             // 余额
}



