package com.nb.yyds220118.control;

import com.github.pagehelper.PageInfo;
import com.nb.yyds220118.pojo.Products;

import com.nb.yyds220118.pojo.Products2;
import com.nb.yyds220118.pojo.ProductsType;

import com.nb.yyds220118.service.IproductsService2;
import com.nb.yyds220118.service.IproductstypeService;
import com.nb.yyds220118.util.PageTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：javasun
 * @date ：Created in 2022/1/18 17:08
 * @description：
 */
@RestController
public class ProductsControl2 {
    @Autowired
    IproductsService2 productsService;
    @Autowired
    IproductstypeService service;

    @RequestMapping("select")
    PageTable<Products2> select(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int limit)
    {
        return productsService.biglist(page, limit);
    }
    @RequestMapping("select1")
    PageInfo<Products2> select1(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int limit){
        return productsService.select1(page, limit);
    }

    @RequestMapping("del")
    int del(int pid) {
        productsService.del(pid);
        return 0;
    }


    @Autowired
    IproductstypeService productstypeService;

    @RequestMapping("protype")
    List<ProductsType> list1() {
        return productstypeService.list1();
    }

    //add处写的还是有问题
    @RequestMapping("add")
    int add(String pname, BigDecimal pprice, BigDecimal pnum, int tid) {
        productsService.add(pname, pprice, pnum, tid);
        Integer id = productsService.getIdByName(pname);
        return 0;
    }

    @RequestMapping("update")
    int update(int pid, String pname, BigDecimal pprice, BigDecimal pnum) {

        productsService.update(pid, pname, pprice, pnum);
        return 0;
    }

    //销量排名 pnum
    @RequestMapping("desc")
    PageTable<Products2> desc(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int limit) {
        return productsService.desc(page, limit);
    }
    @RequestMapping("getsixMonth")
    Map<String,Object> getfourMonth(){
        Map<String,Object> map=new HashMap<>();
        String[] name=new String[]{"六个月"};
        Double[] data=new Double[]{
                productsService.getfourMonth()
        };
        map.put("data",data);
        map.put("name",name);
        return map;
    }
}

