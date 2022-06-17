package com.nb.yyds220118.control;

import com.nb.yyds220118.pojo.Products;
import com.nb.yyds220118.pojo.Users;
import com.nb.yyds220118.service.IProductsService;
import com.nb.yyds220118.util.PageTable;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : lq
 * @date : Created in 2022/1/18 20:31
 * @description：
 */
@RestController
public class ProductsControl {
    @Autowired
    IProductsService iProductsService;

    //得到购物车的信息
    @RequestMapping("/getMyShoppingCar")
    public PageTable<Products> getMyShoppingCar(@RequestParam(defaultValue = "1")int page,
                                                @RequestParam(defaultValue = "5") int limit){
        //从authrealm中获取当前用户
        System.out.println("进入getMyShoppingCar");
        Subject subject= SecurityUtils.getSubject();
        Users Nowuser= (Users) subject.getPrincipal();
        PageTable<Products> pageTable= iProductsService.getMyShoppingCar(Nowuser.getUNo(),page,limit);
        System.out.println(pageTable.getData().toString());
        return pageTable;
    }

    //得到购物车的信息 jquery
    @RequestMapping("/getMyShoppingCarjq")
    public List<Products> getMyShoppingCarjq(@RequestParam(defaultValue = "1")int page,
                                             @RequestParam(defaultValue = "5") int limit){

        System.out.println("进入getMyShoppingCarjq");
        //从authrealm中获取当前用户
        Subject subject= SecurityUtils.getSubject();
        Users Nowuser= (Users) subject.getPrincipal();
        List<Products> list= iProductsService.getMyShoppingCarjq(Nowuser.getUNo(),page,limit);
        System.out.println(list.toString());
        return list;
    }

    //删除选中的购物车信息
    @RequestMapping("/delCheckedProducts")
    public Integer delCheckedProducts(@RequestBody List<Products> products){
        System.out.println("进入delCheckedProducts方法："+products);
        //从authrealm中获取当前用户
        Subject subject= SecurityUtils.getSubject();
        Users Nowuser= (Users) subject.getPrincipal();
        int count=iProductsService.delCheckedProducts(products,Nowuser.getUNo());
        return count;
    }

    //删除选中的购物车信息 jq
    @RequestMapping("/delCheckedProductsjq")
    public Integer delCheckedProductsjq(String carid,String pid,String gg){
        //从authrealm中获取当前用户
        Subject subject=SecurityUtils.getSubject();
        Users Nowuser=(Users)subject.getPrincipal();
        System.out.println("接收前端carid的值："+carid);
        System.out.println("接收前端pid的值："+pid);
        System.out.println("接收前端gg的值："+gg);
        int getcarid=0;
        int getpid=0;
        try {
            getcarid=Integer.parseInt(carid);
            getpid=Integer.parseInt(pid);
        }catch (NumberFormatException e){
            e.printStackTrace();
            System.out.println("删除选中购物车出现数字转换异常");
        }
    /*  if(getcarid==0||getpid==0){

      }*/
        int count=iProductsService.delCheckedProductsjq(getcarid,getpid,Nowuser.getUNo());
        return count;
    }

    //根据pid得到商品信息
    @RequestMapping("/getProductsByPid")
    public Products getProductsByPid(Integer pid){
        Products products=iProductsService.getProductsByPid(pid);
        System.out.println(products);
        return products;
    }

    //向购物车中添加商品
    @RequestMapping("/addShoppingCar")
    public Integer addShoppingCar(Integer pid,Integer pcount){
        System.out.println("进入addShoppingCar方法");
        System.out.println(pid+" "+pcount);
        //从authrealm中获取当前用户
        Subject subject=SecurityUtils.getSubject();
        Users Nowuser=(Users)subject.getPrincipal();
        int count=iProductsService.addShoppingCar(pid,Nowuser.getUNo(),pcount);
        return count;
    }

    //随机得到商品
    @RequestMapping("/randomGetProduct")
    public List<Products> randomGetProduct(){
        System.out.println("进入randomGetProduct方法");
        List<Products> products=iProductsService.randomGetProduct();
        return products;
    }



}
