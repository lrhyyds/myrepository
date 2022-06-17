package com.nb.yyds220118.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nb.yyds220118.dao.IProductsDao;
import com.nb.yyds220118.dao.IUserDao;
import com.nb.yyds220118.pojo.*;
import com.nb.yyds220118.service.IUserService;
import com.nb.yyds220118.util.PageTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import sun.awt.im.SimpleInputMethodWindow;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserDao iUserDao;

    @Override
    public PageTable<Users> users(int page, int limit) {
        PageHelper.startPage(page,limit);
        List<Users> users=iUserDao.users();
        PageInfo<Users> pageInfo=new PageInfo<>(users);
        PageTable<Users> pageTable=new PageTable<>();
        pageTable.setCode(0);
        pageTable.setCount(pageInfo.getTotal());
        pageTable.setData(pageInfo.getList());
        return pageTable;

    }

    @Override
    public PageTable<Users> likeUsers(String uname, int page, int limit) {
        PageHelper.startPage(page,limit);
        System.out.println("进入service层");
        Users user=new Users();
        user.setUName(uname);
        List<Users> users=iUserDao.likeUsers(user);
        System.out.println("成功查询到数据");
        PageInfo<Users> pageInfo=new PageInfo<>(users);
        PageTable<Users> pageTable=new PageTable<>();
        pageTable.setCode(0);
        pageTable.setCount(pageInfo.getTotal());
        pageTable.setData(pageInfo.getList());
        return pageTable;
    }

    @Override
    public Users getUserByNo1(int uNo) {
        return iUserDao.getUserByNo1(uNo);
    }

    @Override
    public int updateUserPWD(String upassword, int uno) {
        return iUserDao.updateUserPWD(upassword,uno);
    }

    @Override
    public int updateUserInfo(UserInfo userInfo) {
        return iUserDao.updateUserInfo(userInfo);
    }

    @Override
    public int updateUserName(String uname, int uno) {
        return iUserDao.updateUserName(uname,uno);
    }

    @Override
    public int updateUserPhone(String uphone, int uno) {
        return iUserDao.updateUserPhone(uphone,uno);
    }

    @Override
    public int updateUserStatus(int ustatus, int uno) {
        return iUserDao.updateUserStatus(ustatus,uno);
    }

    @Override
    public int updateUserInfoMoney(double umoney, int uno) {
        return iUserDao.updateUserInfoMoney(umoney,uno);
    }


//    @Override
//    public int addUserAddress(UserAddress userAddress) {
//
//        return iUserDao.addUserAddress(userAddress);
//    }

    @Override
    public int delUserAddress(int aId) {
        return iUserDao.delUserAddress(aId);
    }

    @Override
    public int updateUserAddress(UserAddress userAddress) {
        return iUserDao.updateUserAddress(userAddress);
    }

    @Override
    public PageTable<UserAddress> getUserAddressPaging(int uno, int page, int limit) {
        PageHelper.startPage(page,limit);
        List<UserAddress> userAddresses=iUserDao.getUserAddress(uno);
        PageInfo<UserAddress> pageInfo=new PageInfo<>(userAddresses);
        PageTable<UserAddress> pageTable=new PageTable<>();
        pageTable.setCode(0);
        pageTable.setCount(pageInfo.getTotal());
        pageTable.setData(pageInfo.getList());
        return pageTable;
    }

    @Override
    public PageTable<Comments> getUserComments(int uno, int page, int limit) {
        PageHelper.startPage(page,limit);
        List<Comments> userAddresses=iUserDao.selMessage(uno);
        PageInfo<Comments> pageInfo=new PageInfo<>(userAddresses);
        PageTable<Comments> pageTable=new PageTable<>();
        pageTable.setCode(0);
        pageTable.setCount(pageInfo.getTotal());
        pageTable.setData(pageInfo.getList());
        return pageTable;
    }

    @Override
    public int addComments(Comments comments) {
        return iUserDao.addComments(comments);
    }


    //刘祺
    @Autowired
    IProductsDao iProductsDao;

    //用户注册
    @Override
    public Integer addUsers(String uphone, String pwd, String uname) {
        Users users=new Users();
        users.setUPhone(uphone);
        String pwdsalt= DigestUtils.md5DigestAsHex(pwd.getBytes());
        users.setUPassword(pwdsalt);
        users.setUName(uname);
        int count=iUserDao.addUsers(users);
        return count;
    }

    //增加用户信息
    @Override
    public Integer addUserInfo(int uno) {
        int count=iUserDao.addUserInfo(uno);
        return count;
    }

    //增加用户身份
    @Override
    public Integer addUserRole(int uno) {
        int count=iUserDao.addUserRole(uno);
        return count;
    }

    //根据手机号得到用户
    @Override
    public Users getUserByPhone(String phone) {
        Users users=iUserDao.getUserByPhone(phone);
        return users;
    }

    //通过uno获得用户信息
    @Override
    public Users getUserByUno(int uno) {
        Users user=iUserDao.getUserByUno(uno);
        return user;
    }

    //通过uno得到用户所有的地址
    @Override
    public List<UserAddress> getUserAddress(int uno) {
        List<UserAddress> list=iUserDao.getUserAddress(uno);
        return list;
    }


    /*正在使用的支付方法*/
    //支付 jq
    @Override
    public Integer payProductJq(int uno, String addressInfo, int pid, int productnum, Double ordercost) {
        System.out.println("进入payProductJq方法");
        //获取商品信息
        Products products = iProductsDao.getProductById(pid);
        double productcost=products.getPPrice();
        double cost = ordercost;
        String[] split = addressInfo.split(",");
        String uaddress=split[0];//收货地址
        String uname=split[1];//用户名
        String uphone=split[2];//联系电话
        System.out.println("收货地址："+uaddress+"用户名："+uname+"联系电话："+uphone);
        reduceMoney(uno,cost);//扣钱
        int addsign=addOrder(uno,uphone,uname,uaddress,cost);//增加订单
        if(addsign==1){//如果订单增加成功
            int oid=iUserDao.getpaymaxoid();//查询增加订单产生的订单号，数据库中最大的订单号为产生的订单号
           int additemsign= addOrderItemJq(oid,productnum,productcost,pid);//增加订单项
            if(additemsign==1){
                updateProductsJq(pid,productnum);//修改商品的销量和库存
            }

        }

        return addsign;
    }
    //扣钱
    public Integer reduceMoney(int uno,double cost){
        System.out.println("调用reduceMoney方法");
        int count=iUserDao.reduceMoney(uno,cost);
        return count;
    }

    //增加订单
    public Integer addOrder(int uno,String uphone,String uname,String address,Double cost){
        System.out.println("调用addOrder方法");
        long time=System.currentTimeMillis();
        Timestamp timestamp=new Timestamp(new Date(time).getTime());
        System.out.println(timestamp);
        SimpleDateFormat sdflog=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ocreattime=sdflog.format(timestamp);//发货时间按指定格式显示
        Orders orders=new Orders();
        orders.setUNo(uno);
        orders.setOPhone(uphone);
        orders.setOName(uname);
        orders.setOAddress(address);
        orders.setOCreatTime(ocreattime);
        orders.setOCost(cost);
        int count=iUserDao.addOrder(orders);
        return count;
    }

    //增加订单项 jq
    public Integer addOrderItemJq(int oid,int num,Double cost,int pid){
        System.out.println("调用addOrderItemJq方法");
        OrderItem orderItem=new OrderItem();
        orderItem.setOId(oid);//增加订单时产生的订单号
        orderItem.setONum(num);//同一种商品的数量
        orderItem.setOMoney(cost);//本次订单的价格
        orderItem.setPId(pid);//本次订单的商品编号
        int count=iUserDao.addOrderItemJq(orderItem);
        return count;
    }


    //修改商品的销量和库存Jq
    public Integer updateProductsJq(int pid,int num){
        System.out.println("调用updateProductsJq方法");
        int count=iProductsDao.updateProductsJq(pid,num);
        return count;
    }

    //删除购物车信息
    public Integer delShoppingCar(int carid,int uno,int pid){
        System.out.println("调用delShoppingCar方法");
        int count=iUserDao.delShoppingCar(carid,uno, pid);
        return count;
    }

    //增加用户地址
    @Override
    public int addUserAddress(UserAddress userAddress) {
        return iUserDao.addUserAddress(userAddress);
    }

    //进入后台 判断身份
    @Override
    public String checkRole(int uno) {
        return iUserDao.checkRole(uno);
    }

    //得到用户金额
    @Override
    public Double getumoney(int uno) {
        Double money= iUserDao.getumoney(uno);
        return money;
    }
}
