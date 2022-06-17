package com.nb.yyds220118.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users implements Serializable {
    int uNo;                 //编号
    String uPhone;           //  手机号(登录用户名)
    String uPassword ;       // 密码
    String uName;            //昵称
    int uStatus;            //状态   0禁用  1可用
    String uSex;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    Date uBirthday;
    //String uuBirthday;
    String uIdentityCode;      //身份证号
    double uMoney;             // 余额


    List<UserAddress> address;
}



