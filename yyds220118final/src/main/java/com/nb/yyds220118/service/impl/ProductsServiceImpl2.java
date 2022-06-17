package com.nb.yyds220118.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.nb.yyds220118.dao.IproductsDao2;
import com.nb.yyds220118.dao.IproductstypeDao;

import com.nb.yyds220118.pojo.Products;
import com.nb.yyds220118.pojo.Products2;
import com.nb.yyds220118.pojo.ProductsType;

import com.nb.yyds220118.service.IproductsService2;
import com.nb.yyds220118.util.PageTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：javasun
 * @date ：Created in 2022/1/18 17:00
 * @description：
 */
@Service
public class ProductsServiceImpl2 implements IproductsService2 {
    @Autowired
    IproductsDao2 productsDao2;

    @Override
    public PageTable<Products2> biglist(int page, int limit) {
//        int n;
        PageHelper.startPage(page, limit);
        List<Products2> sel = productsDao2.sel1();
        PageInfo<Products2> pageInfo= new PageInfo<>(sel);
//        List<Products> aa=pageInfo.getList();
//        List<Products> tu=new ArrayList<>();
//        int i=1;
//        for (Products p:aa ){
//            p.setTu(p.getPfilename());
//
//            ++i;
//        }
        PageTable<Products2> pageTable=new PageTable<>();
        pageTable.setCode(0);
        pageTable.setCount(pageInfo.getTotal());
        pageTable.setData(pageInfo.getList());
        return pageTable;
    }

    @Override
    public PageTable<ProductsType> biglist1(int page, int limit) {
        PageHelper.startPage(page, limit);
        List<ProductsType> sel1=productsDao2.getproductstype();
        PageInfo<ProductsType> pageInfo =new PageInfo<>(sel1);
        PageTable<ProductsType> pageTable=new PageTable<>();
        pageTable.setCode(0);
        pageTable.setCount(pageInfo.getTotal());
        pageTable.setData(pageInfo.getList());
        return pageTable;
    }

    @Override
    public int del(int pid) {
        productsDao2.del(pid);
        return 0;
    }



    @Override
    public int add(String pname, BigDecimal pprice, BigDecimal pnum,Integer tid) {
        productsDao2.add(pname, pprice, pnum,tid);
        return 0;
    }

    @Override
    public int update(int pid, String pname, BigDecimal pprice, BigDecimal pnum) {
       productsDao2.update(pid, pname, pprice, pnum);
        return 0;
    }

    @Override
    public int addProductstype(int tid) {
       productsDao2.addProductstype(tid);
        return 0;
    }

    @Autowired
    IproductstypeDao productstypeDao;


    @Override
    public int getIdByName(String pname) {
        productsDao2.getIdByName(pname);
        return 0;
    }

    @Override
    public PageTable<Products2> desc(int page,int limit) {
        int n=page;
        int m=limit;
        PageHelper.startPage(page, limit);
        List<Products2> desc = productsDao2.desc();
        PageInfo<Products2> pageInfo=new PageInfo<>(desc);
        List<Products2> lll=pageInfo.getList();
        List<Products2> neww=new ArrayList<>();
        int i=1;
        for(Products2 s:lll){
            //将循环后的数据存入Pppage中。
            s.setPppage((n-1)*m+i);
            neww.add(s);
            ++i;
        }
        pageInfo.setList(neww);
        PageTable<Products2> pageTable=new PageTable<>();
        pageTable.setCode(0);
        pageTable.setCount(pageInfo.getTotal());
        pageTable.setData(pageInfo.getList());
        return pageTable;

    }

    @Override
    public PageInfo<Products2> select1(int page, int limit) {
        PageHelper.startPage(page, limit);
        List<Products2> list=productsDao2.sel1();
        PageInfo<Products2> pageInfo=new PageInfo<>(list);

        return pageInfo;
    }

    @Override
    public Double getfourMonth() {
        return  productsDao2.getfourMonth();
    }


}
