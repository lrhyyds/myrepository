package com.nb.yyds220118.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nb.yyds220118.dao.IProductsDao;
import com.nb.yyds220118.pojo.Products;
import com.nb.yyds220118.service.IProductsService;
import com.nb.yyds220118.util.PageTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : lq
 * @date : Created in 2022/1/18 20:26
 * @description：
 */
@Service
public class ProductsServiceImpl implements IProductsService {
    @Autowired
    IProductsDao iProductsDao;

    //得到购物车的信息
    @Override
    public PageTable<Products> getMyShoppingCar(int uno,int page,int limit) {
        System.out.println(uno+" "+page+" "+limit);
        PageHelper.startPage(page,limit);
        List<Products> list=iProductsDao.getBuyshopping(uno);
        PageInfo<Products> pageInfo=new PageInfo<>(list);
        PageTable<Products> pageTable=new PageTable<>();
        pageTable.setCode(0);
        pageTable.setCount(pageInfo.getTotal());
        pageTable.setData(pageInfo.getList());
        return pageTable;
    }

    //得到购物车的信息 jq
    @Override
    public List<Products> getMyShoppingCarjq(int uno, int page, int limit) {
        List<Products> list=iProductsDao.getBuyshopping(uno);
        return list;
    }

    //删除选中的购物车信息
    @Override
    public Integer delCheckedProducts(List<Products> list, int uno) {
        int count=iProductsDao.delCheckedProducts(list, uno);
        return count;
    }

    //删除选中的购物车信息 jq
    @Override
    public Integer delCheckedProductsjq(int carid,int pid, int uno) {
        int count=iProductsDao.delCheckedProductsjq(carid,pid,uno);
        return count;
    }

    //根据pid得到商品信息
    @Override
    public Products getProductsByPid(Integer pid) {
        Products products=iProductsDao.getProductsByPid(pid);
        return products;
    }

    //增加购物车的商品
    @Override
    public Integer addShoppingCar(int pid, int uno, int upnum) {
        int count=iProductsDao.addShoppingCar(pid, uno, upnum);
        return count;
    }

    //随机得到商品
    @Override
    public List<Products> randomGetProduct() {
        List<Products> products=iProductsDao.randomGetProduct();
        return products;
    }

}
