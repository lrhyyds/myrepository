package com.nb.yyds220118.dao;

import com.nb.yyds220118.pojo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * @author HuangYijin
 * @date 2022/1/18
 */

public interface IUserDao {
    //获取用户所有信息
    List<Users> users();
    List<Users> likeUsers(Users u);
    //通过编号获取用户所有信息
    Users getUserByNo1(int uNo);
    //修改密码
    int updateUserPWD (
            @Param("upassword")String upassword,
            @Param("uno")int uno
    );
    //更新用户信息
    int updateUserInfo(UserInfo userInfo);
    //修改昵称
    int updateUserName(
            @Param("uname") String uname,
            @Param("uno") int uno
    );
    //修改手机号
    int updateUserPhone(
            @Param("uphone") String uphone,
            @Param("uno") int uno
    );
    //修改用户状态
    int updateUserStatus(
            @Param("ustatus") int ustatus,
            @Param("uno") int uno
    );
    //用户充值
    int updateUserInfoMoney(
            @Param("umoney") double umoney,
            @Param("uno") int uno
    );
    //增加用户地址
    //int addUserAddress(UserAddress userAddress);
    //删除用户地址
    int delUserAddress(int aId);
    //修改用户地址
    int updateUserAddress(UserAddress userAddress);
    //查询用户地址x
    //List<UserAddress> getUserAddress(int uno);
    //查询留言
    List<Comments> selMessage(int uno);
    int addComments(Comments comments);


    //注册用户
    Integer addUsers(Users users);
    //增加用户信息
    Integer addUserInfo(int uno);
    //增加用户身份
    Integer addUserRole(int uno);
    //通过phone得到用户
    Users getUserByPhone(String phone);
    /*通过uno得到用户*/
    Users getUserByUno(int uno);
    /*得到用户地址*/
    List<UserAddress> getUserAddress(int uno);
    //扣钱
    Integer reduceMoney(@RequestParam("uno") int uno, @RequestParam("cost") double cost);
    //增加订单
    Integer addOrder(Orders order);
    //查询最大的oid---找到增加订单时产生的订单号
    Integer getpaymaxoid();

    //增加订单项Jq
    Integer addOrderItemJq(OrderItem orderItem);
    //删除购物车信息
    Integer delShoppingCar(@RequestParam("carid") int carid,@RequestParam("uno") int uno,@RequestParam("pid") int pid);
    //增加用户地址
    int addUserAddress(UserAddress userAddress);
    //进入后台 判断身份
    String checkRole(int uno);
    //得到用户金额
    Double getumoney(int uno);
    //通过用户编号查询用户名字 --回复表关联查询
    String getunameByuno(int uno);
}
