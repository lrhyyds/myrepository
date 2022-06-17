package com.nb.yyds220118.util;

import com.nb.yyds220118.pojo.Users;

import com.nb.yyds220118.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class MyAuthRealm extends AuthorizingRealm {
    @Autowired
    IUserService userService;

        /**
         * 用户ID，角色ID
         * 角色表:角色ID，角色名称role,
         * 用户ID1有角色:bbs,product,user
         *
         * 根据用户ID，查询出当前用户有哪些role
         * 每个用户的角色是Set<String> roles
         *
         *
         * 当登录后的用户进行权限检查时会调用这个方法
         * 为当前登录的用户授予角色和权限
         */
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
            //AdminUser user=(AdminUser)principals.getPrimaryPrincipal();
            //System.out.println("获取角色和权限:" + user.getAccount());
            SimpleAuthorizationInfo result =
                    new SimpleAuthorizationInfo();
            //List<Module> list = adminUserService.getModulesByUserId(user.getUserid());
            Set<String> roles = new HashSet<>();
//            for (Module module:list){
//                roles.add(module.getRole());
//            }
            roles.add("bbs");
            roles.add("product");
            roles.add("user");
            System.out.println("roles:" + roles);
            result.setRoles(roles);
            return result;
            /*
            //角色集合
            Set<String> roles = userDao.getRolesByAccount(userName);
            //从数据库查询当前用户的权限信息
            //Set<String> perms = userDao.getAllPerms(userName);
            authorizationInfo.setRoles(roles);
            //authorizationInfo.setStringPermissions(perms);*/
            /**
             * 根据当前用户去数据库中查询角色及权限数据，
             * 把这些数据赋值到authorizationInfo中
             *  authorizationInfo.setRoles(roles);
             *  authorizationInfo.setStringPermissions(perms);
             */
            //调用service方法查询用户的角色和权限
        }

        /**
         * 当用户登录时，进入该方法进行帐号密码检查
         * @return
         */
        protected SimpleAuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
            System.out.println("验证");
            UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

            //得到用户输入的帐号
            String account=(String)token.getPrincipal();
            //得到用户输入的密码
            String pwd = new String((char[]) token.getCredentials());
            System.out.println("执行登录验证：" + account + "," + pwd);
//            Subject subject = SecurityUtils.getSubject();

            //Integer type = (Integer) subject.getSession().getAttribute("usertype");
            //根据帐号查询数据库中的用户对象,包含加密后的密码
            Users u=userService.getUserByPhone(account);
            if (u==null){
                return null;
            }
            //根据帐号查询用户信息
            //AdminUser adminUser = adminUserService.login(account);
            //第一个参数一般是用户对象
            //第二个参数一定要写从数据库中查询到的密码
            //shiro会用这个密码与用户填写的密码进行比较
            //String dbpwd=DigestUtils.md5DigestAsHex(u.getPwd().getBytes());
            /*if (account.equals("admin")
                    && pwd.equals("123456")){
                //根据帐号查询用户信息
                //AdminUser adminUser = adminUserService.login(account);
                //第一个参数一般是用户对象
                //第二个参数一定要写从数据库中查询到的密码
                //shiro会用这个密码与用户填写的密码进行比较
                return new SimpleAuthenticationInfo(account,u.getPwd(),
                        null,"xx");
            }*/
            return new SimpleAuthenticationInfo(u,u.getUPassword(),
                    null,"xx");

            /**
             * 调用service方法查询用户名密码是否正确
             * 如果正确就返回AuthenticationInfo对象,错误返回NULL
             */

            //调用service方法查询用户名和密码是否存在
        }
}
