package com.nb.yyds220118.control;

import com.nb.yyds220118.pojo.*;
import com.nb.yyds220118.service.IProductsService;
import com.nb.yyds220118.service.IUserService;
import com.nb.yyds220118.util.PageTable;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.tools.jconsole.JConsole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 * @author HuangYijin
 * @date 2022/1/18
 */

@RestController
public class UserControl {
    @Autowired
    IUserService iUserService;
    @Autowired
    IProductsService iProductsService;
    //得到所有用户详细信息
    @RequestMapping("users")
    PageTable<Users> users(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int limit
    ){
        return iUserService.users(page, limit);
    }
    //得到所有用户详细信息
    @RequestMapping("likeUsers")
    PageTable<Users> likeUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "1") String uname
    ){
        System.out.println("进入likeUsers方法");
        return iUserService.likeUsers(uname,page, limit);
    }
    //通过编号得到用户详细信息
    @RequestMapping("getUserByNo")
    Users getUserByNo(){
        System.out.println("得到用户信息的方法1");
        Subject subject= SecurityUtils.getSubject();
        Users Nowuser= (Users) subject.getPrincipal();
        return iUserService.getUserByNo1(Nowuser.getUNo());
    }

    //用户修改密码
    @RequestMapping("updateUserPWD")
    int updateUserPWD(String oriPWD,String newPWD){
        try{
            Subject subject= SecurityUtils.getSubject();
            Users Nowuser= (Users) subject.getPrincipal();
            System.out.println(Nowuser.getUNo()+"用户修改密码编号");
            System.out.println("原密码"+oriPWD+"新密码"+newPWD);
            String datebasePWD=iUserService.getUserByNo1(Nowuser.getUNo()).getUPassword();//得到数据库原密码
            if (DigestUtils.md5DigestAsHex(oriPWD.getBytes()).equals(datebasePWD)){//判断输入的密码是否相同
                String pwd=DigestUtils.md5DigestAsHex(newPWD.getBytes());//新密码加密
                int a = iUserService.updateUserPWD(pwd, Nowuser.getUNo());//执行密码修改
                return a;
            }else {
                return 0;
            }
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }

    }
    //查询留言
    @RequestMapping("selMessage")
    PageTable<Comments> comments(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "5") int limit
    ){
        Subject subject= SecurityUtils.getSubject();
        Users Nowuser= (Users) subject.getPrincipal();
        return iUserService.getUserComments(Nowuser.getUNo(), page,limit);
    }
    @RequestMapping("addUserLiuyan")
    int addUserLiuyan(String ccontent){
        Subject subject= SecurityUtils.getSubject();
        Users Nowuser= (Users) subject.getPrincipal();
        Comments comments=new Comments();
        Date date=new Date();
        SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currtime=sbf.format(date);
        comments.setCContent(ccontent);
        comments.setCCreatTime(currtime);
        comments.setUNo(Nowuser.getUNo());
        return iUserService.addComments(comments);
    }

    //管理员修改用户密码
    @RequestMapping("adminUpdateUserPWD")
    int adminUpdateUserPWD(String uPassword,HttpServletRequest request){
        try{
            HttpSession session=request.getSession();
            Integer uNo= (Integer) session.getAttribute("thisuno");
            String pwd = DigestUtils.md5DigestAsHex(uPassword.getBytes());
            return iUserService.updateUserPWD(pwd,uNo);
        }catch (Exception e){
            return -1;
        }

    }

    //管理员更新用户信息(性别、生日、身份证号、昵称)
    @RequestMapping("updateUser")
    int updateUser(Users users,HttpServletRequest request){
        try {
            HttpSession session=request.getSession();
            System.out.println((Integer) session.getAttribute("thisuno"));
            String uName=users.getUName();
            String uSex=users.getUSex();
            Date uBirthday=users.getUBirthday();
            String uIdentityCode=users.getUIdentityCode();
            Integer uNo= (Integer) session.getAttribute("thisuno");
            System.out.println(uNo);
            UserInfo userInfo=new UserInfo();
            userInfo.setUSex(uSex);
            userInfo.setUBirthday(uBirthday);
            userInfo.setUIdentityCode(uIdentityCode);
            userInfo.setUNo(uNo);
            iUserService.updateUserInfo(userInfo);
            iUserService.updateUserName(uName,uNo);
            return 1;
        }catch (Exception e){
            return -1;
        }

    }
    //用户修改自己信息
    @RequestMapping("updateUser2")
    int updateUser2(Users users){
        try {
            Subject subject= SecurityUtils.getSubject();
            Users Nowuser= (Users) subject.getPrincipal();

            String uName=users.getUName();
            String uSex=users.getUSex();
            Date uBirthday=users.getUBirthday();
            String uIdentityCode=users.getUIdentityCode();

            UserInfo userInfo=new UserInfo();
            userInfo.setUSex(uSex);
            userInfo.setUBirthday(uBirthday);
            userInfo.setUIdentityCode(uIdentityCode);
            userInfo.setUNo(Nowuser.getUNo());
            iUserService.updateUserInfo(userInfo);
            iUserService.updateUserName(uName,Nowuser.getUNo());
            return 1;
        }catch (Exception e){
            return -1;
        }

    }

    //修改登录名（手机号）
    @RequestMapping("updatePhone")
    int updatePhone(String uPhone,int uNo){
        try{
            return iUserService.updateUserPhone(uPhone, uNo);
        }catch (Exception e){
            return -1;
        }

    }

    //修改用户状态
    @RequestMapping("updateUsersStatus")
    int updateStatus(int uNo){
        int currentStatus = iUserService.getUserByNo1(uNo).getUStatus();//得到当前状态
        System.out.println(currentStatus);
        if (currentStatus==1){
            iUserService.updateUserStatus(0,uNo);//封号
            return 0;
        }else if (currentStatus==0){
            iUserService.updateUserStatus(1,uNo);//解封
            return 1;
        }
        return -1;
    }

    //用户充值
    @RequestMapping("updateUserInfoMoney")
    int updateUserInfoMoney(double uMoney){
        System.out.println("充值"+uMoney+"|||||||||||||||||||||||||||||");
        try{
            Subject subject= SecurityUtils.getSubject();
            Users Nowuser= (Users) subject.getPrincipal();
            return iUserService.updateUserInfoMoney(uMoney,Nowuser.getUNo());
        }catch (Exception e){
            return -1;
        }
    }
    //增加用户地址
//    @RequestMapping("addUserAddress")
//    int addUserAddress(UserAddress userAddress){
//        try{
//            return iUserService.addUserAddress(userAddress);
//
//        }catch (Exception e){
//            return -1;
//        }
//    }
//    //删除用户地址
    @RequestMapping("delUserAddress")
    int delUserAddress(int aId){
        try {
            return iUserService.delUserAddress(aId);
        }catch (Exception e){
            return -1;
        }

    }
    //修改用户地址
    @RequestMapping("updateUserAddress")
    int updateUserAddress(UserAddress userAddress){
        System.out.println(userAddress);
        try {
            Subject subject= SecurityUtils.getSubject();
            Users Nowuser= (Users) subject.getPrincipal();
            System.out.println("uno||||||||||||||||||||||||"+Nowuser.getUNo());
            /*UserAddress userAddress1=new UserAddress();
            userAddress1.setUNo(Nowuser.getUNo());
            userAddress1.setUAddress(userAddress.getUAddress());
            userAddress1.setUConsignee(userAddress1.getUConsignee());
            userAddress1.setAId(userAddress.getAId());*/
            iUserService.updateUserAddress(userAddress);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }

    }
    //查询用户地址
    @RequestMapping("getUserAddressPaging")
    PageTable<UserAddress> getUserAddressPaging(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int limit
    ){
        Subject subject= SecurityUtils.getSubject();
        Users Nowuser= (Users) subject.getPrincipal();
            return iUserService.getUserAddressPaging(Nowuser.getUNo(), page,limit);
    }

    //得到用户id
    @RequestMapping("/putuno")
    Integer putuno(int thisuno, HttpServletRequest request){
        HttpSession session=request.getSession();
        session.setAttribute("thisuno",thisuno);
        System.out.println(thisuno);
        return 1;
    }
    //上传头像
    @RequestMapping("/doFileUpload")
    public String addfile(MultipartFile file,HttpSession session) {
        Subject subject= SecurityUtils.getSubject();
        Users Nowuser= (Users) subject.getPrincipal();
//        String path =session.getServletContext().getRealPath("images/avatar/");
        String path="/usr/local/images/avatar/";//宝塔的上传路径
        System.out.println("进入上传得到的路径："+path);
        File p = new File(path);
        if (!p.exists()) { //如果upload文件夹不存在
            System.out.println("执行了新创建了一个文件夹");
            p.mkdir();//创建文件夹，用来保存上传的文件
        }
        StringBuffer fileName = new StringBuffer(path);
        System.out.println(fileName);
        String originalFilename = file.getOriginalFilename();//得到上传文件的原始文件名

        fileName.append(Nowuser.getUNo() + getFileExtension(originalFilename));//追加文件名
        System.out.println("全路径："+fileName);
        File targetFile = new File(fileName.toString());
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
           return "0";//如果失败抛异常返回0
        }
        return "200";
    }

    public String getFileExtension(String fileName) {
        String fileTyle = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("文件后缀： " + fileTyle);
        return fileTyle;
    }



    //lllllll
    //用户登录
    @RequestMapping("/login")
    public String login(String phone, String pwd) throws IOException {
        System.out.println("手机号："+phone+" 密码："+pwd);
        Users users=iUserService.getUserByPhone(phone);
        //从数据库中查找用户并验证
        if (users==null){
            System.out.println("登录失败");
        }
        AuthenticationToken token =
                new UsernamePasswordToken(phone, pwd);
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(token);
        }catch (Exception e){
            return "false";
        }
        return "login";
    }

    //登录后台
    @RequestMapping("/gotoBack")
    public String gotoBack(){
        //从authrealm中获取当前用户
        Subject subject= SecurityUtils.getSubject();
        Users Nowuser= (Users) subject.getPrincipal();
        System.out.println("进入gotoBack方法");
        String role=checkRole(Nowuser.getUNo());//同通过手机号去获得角色
        if (role==null){
            return "/index.html";
        }
        if (role.equals("管理员")){
            System.out.println("进入管理员后台");
            return "/admin/adminindex.html";
        }
        else {
            System.out.println("进入员工后台");
            return "/manage/userIndex.html";
        }
    }

    //退出登录
    @RequestMapping("/outlogin")
    public Integer outlogin(){
        //从authrealm中获取当前用户
        Subject subject= SecurityUtils.getSubject();
        //注销掉这个token
        subject.logout();
        return 1;
    }

    //用户注册
    @RequestMapping("/register")
    public String register(String phone, String pwd){
        System.out.println("进入register方法");
        String uname=getNewName(phone);
        int count=iUserService.addUsers(phone,pwd,uname);
        Users users=iUserService.getUserByPhone(phone);
        int count2=iUserService.addUserInfo(users.getUNo());
        int count3=iUserService.addUserRole(users.getUNo());
        if (count!=0&&count2!=0&&count3!=0){
            return "register";
        }
        else {
            return "false";
        }
    }

    //得到生成昵称
    public String getNewName(String phone){
        StringBuffer stringBuffer=new StringBuffer();
        //根据手机号生成昵称
        for (int i = 0; i < phone.length(); i++) {
            int num=phone.charAt(i);
            char abc= (char) (num+65);
            stringBuffer.append(abc);
        }
        return stringBuffer.toString();
    }

    //通过uno得到用户
    @RequestMapping("/getUserByUno")
    public Users getUserByUno(){
        //从authrealm中获取当前用户
        Subject subject= SecurityUtils.getSubject();
        Users Nowuser= (Users) subject.getPrincipal();
        Users users=iUserService.getUserByUno(Nowuser.getUNo());
        System.out.println(users);
        System.out.println("#######"+users.getUMoney());
        return users;
    }

    //通过uno得到用户地址
    @RequestMapping("/getUserAddressByuNo")
    public List<UserAddress> getUserAddress(int uno){
        return iUserService.getUserAddress(uno);
    }


    //支付功能 jq
    @RequestMapping("/paymentjq")
    public Integer paymentjq(HttpServletRequest request){
        //从authrealm中获取当前用户
        Subject subject=SecurityUtils.getSubject();
        Users users= (Users) subject.getPrincipal();
        System.out.println("进入paymentjq");
        //获取请求中的数据
        //一个订单的商品数量
        String[] productnum=request.getParameterValues("productnum[]");
        //购物车对应的商品编号数组--一个购物车对应一种同类商品
        String[] productpids=request.getParameterValues("productpis[]");
        //购物车选中购买的购物车数组
        String[] productcheck=request.getParameterValues("productcheck[]");
        //所要花费的商品金额数组
        String[] productcost=request.getParameterValues("productcost[]");
        //已下单支付的购物车编号
        String[] carids=request.getParameterValues("carids[]");
        System.out.print("购物车编号carids：");
        int ci=1;//打印循环
        for(String s:carids){
            System.out.println("carids["+ci+"]: "+s);
            ++ci;
        }
        //地址信息字符串
        String addressInfoAll=request.getParameter("addressInfoAll");
        System.out.println(productnum[0]);
        System.out.println(productpids.toString());
        System.out.println(productcheck);
        System.out.println("地址信息："+addressInfoAll);
        //得到所要花费的所有金额
        Double allmoney=0.0;
        for (int i = 0; i < productcost.length; i++) {
            allmoney+=Double.parseDouble(productcost[i]);
        }
        System.out.println("用户所需话费的总金额为："+allmoney);
        //得到用户所有金额
        double umoney=iUserService.getumoney(users.getUNo());
        System.out.println("用户所有金额："+umoney);
        if (umoney<allmoney){//如果用户余额不够买，则取消支付返回
            return 0;
        }
        //将pid转换为数组
        for (int i = 0; i < productcheck.length; i++) {
            if (Integer.parseInt(productcheck[i])==1){
                System.out.println("productcheck"+i+"被选中");
                int count=iUserService.payProductJq(users.getUNo(),addressInfoAll,
                        Integer.parseInt(productpids[i]),Integer.parseInt(productnum[i]),
                        Double.parseDouble(productcost[i]));
                if (count!=0){
                    iUserService.delShoppingCar(Integer.parseInt(carids[i]),users.getUNo(),Integer.parseInt(productpids[i]));
                }else {
                    return 0;
                }
            }
        }
        /*for (int i = 0; i < pids.length; i++) {
            int count=iUserService.payProduct(uno,addressInfoAll,Integer.parseInt(pids[i]));
            if (count!=0){
                iUserService.delShoppingCar(uno,Integer.parseInt(pids[i]));
            }else {
                return 0;
            }
        }*/
        return 1;
    }

    //通过uno得到用户地址 jq
    @RequestMapping("/getUserAddressByuNojq")
    public List<UserAddress> getUserAddressjq(){
        //从authrealm中获取当前用户
        Subject subject= SecurityUtils.getSubject();
        Users Nowuser= (Users) subject.getPrincipal();
        return iUserService.getUserAddress(Nowuser.getUNo());
    }
    //增加用户地址
    @RequestMapping("addUserAddress")
    int addUserAddress(UserAddress userAddress){
        Subject subject= SecurityUtils.getSubject();
        Users Nowuser= (Users) subject.getPrincipal();
        userAddress.setUNo(Nowuser.getUNo());
        return iUserService.addUserAddress(userAddress);
    }

    //判断身份
    public String checkRole(int uno){
        System.out.println("进入checkRole方法");
        //从authrealm中获取当前用户
        Subject subject= SecurityUtils.getSubject();
        Users Nowuser= (Users) subject.getPrincipal();
        String role=iUserService.checkRole(uno);
        return role;
    }
}
