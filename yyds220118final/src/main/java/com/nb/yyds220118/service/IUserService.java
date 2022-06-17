package com.nb.yyds220118.service;

import com.nb.yyds220118.pojo.Comments;
import com.nb.yyds220118.pojo.UserAddress;
import com.nb.yyds220118.pojo.UserInfo;
import com.nb.yyds220118.pojo.Users;
import com.nb.yyds220118.util.PageTable;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface IUserService {
    PageTable<Users> users(int page,int limit);            //得到所有用户详细信息
    PageTable<Users> likeUsers(String uname,int page,int limit);            //得到所有用户详细信息
    Users getUserByNo1(int uNo);     //通过编号得到用户详细信息
    int updateUserPWD(String upassword,int uno);    //修改密码
    int updateUserInfo(UserInfo userInfo);          //更新用户信息
    int updateUserName(String uname,int uno);       //修改昵称
    int updateUserPhone(String uphone,int uno);     //修改手机号
    int updateUserStatus(int ustatus,int uno);   //修改用户状态
    int updateUserInfoMoney(double umoney, int uno);    //用户充值
    //int addUserAddress(UserAddress userAddress);        //增加用户地址
    int delUserAddress(int aId);                        //删除用户地址
    int updateUserAddress(UserAddress userAddress);     //修改用户地址
    PageTable<UserAddress> getUserAddressPaging(int uno,int page,int limit);      //查询用户地址
    PageTable<Comments> getUserComments(int uno,int page,int limit);      //查询用户留言
    int addComments(Comments comments);

    //注册用户
    Integer addUsers(String uphone,String pwd,String uname);
    //增加用户信息
    Integer addUserInfo(int uno);
    //增加用户身份
    Integer addUserRole(int uno);
    //通过phone得到用户信息
    Users getUserByPhone(String phone);
    //通过uno获得用户信息
    Users getUserByUno(int uno);
    //通过uno得到用户所有的地址
    List<UserAddress> getUserAddress(int uno);
    //支付 jq
    Integer payProductJq(int uno,String addressInfo,int pid,int productnum,Double ordercost);
    //扣钱
    Integer reduceMoney(int uno,double cost);
    //增加订单
    Integer addOrder(int uno,String uphone,String uname,String address,Double cost);
    //删除购物车信息
    Integer delShoppingCar(int carid,int uno,int pid);
    //增加用户地址
    int addUserAddress(UserAddress userAddress);
    //进入后台 判断身份
    String checkRole(int uno);
    //得到用户金额
    Double getumoney(int uno);
}
