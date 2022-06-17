package com.nb.yyds220118.dao;

import com.nb.yyds220118.pojo.Products;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author : lq
 * @date : Created in 2022/1/18 20:23
 * @description：
 */
public interface IProductsDao {
    //获取购物车中的所有信息
    List<Products> getBuyshopping(int uno);
    //通过pid获得商品信息
    Products getProductById(int pid);
    //修改商品的销量和库存
    Integer updateProducts(int pid);
    //修改商品的销量和库存Jq
    Integer updateProductsJq(@RequestParam("pid") int pid,@RequestParam("num") int num);
    //删除选中的购物车信息()
    Integer delCheckedProducts(@RequestParam("list") List<Products> list,
                               @RequestParam("uno") int uno);
    //删除选中的购物车信息 jq
    Integer delCheckedProductsjq(@RequestParam("carid") int carid, @RequestParam("pid") int pid,@RequestParam("uno") int uno);
    //根据pid得到商品信息
    Products getProductsByPid(Integer pid);
    //增加购物车中的商品
    Integer addShoppingCar(@RequestParam("pid") int pid,@RequestParam("uno") int uno,@RequestParam("upnum") int upnum);
    //随机得到商品
    List<Products> randomGetProduct();

}
