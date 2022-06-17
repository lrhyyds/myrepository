package com.nb.yyds220118.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nb.yyds220118.dao.IproductstypeDao;

import com.nb.yyds220118.service.IproductstypeService;
import com.nb.yyds220118.util.PageTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import com.nb.yyds220118.pojo.ProductsType;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：javasun
 * @date ：Created in 2022/1/19 11:29
 * @description：
 */
@Service
public class ProductstypeServiceImpl implements IproductstypeService {
    @Autowired
    IproductstypeDao productstypeDao;

    @Override
    public PageTable<ProductsType> list(int page, int limit) {
        PageHelper.startPage(page, limit);
        List<ProductsType> list = productstypeDao.list();
        System.out.println("*******************");
        for (ProductsType s : list) {
            System.out.println(s);
        }
        PageInfo<ProductsType> pageInfo = new PageInfo<>(list);
        PageTable<ProductsType> pageTable = new PageTable<>();
        pageTable.setCode(0);
        pageTable.setCount(pageInfo.getTotal());
        pageTable.setData(pageInfo.getList());
        return pageTable;

    }

    @Override
    public int delete(int tid) {
        productstypeDao.delete(tid);
        return 0;
    }

    @Override
    public List<ProductsType> list1() {
        return productstypeDao.list1();

    }

    @Override
    public int add1(String tname, BigDecimal tisparenttype, BigDecimal tparenttypeid) {
        productstypeDao.add1(tname, tisparenttype, tparenttypeid);
        return 0;
    }

    @Override
    public int update1(Integer tid, String tname, BigDecimal tisparenttype, BigDecimal tparenttypeid) {
        productstypeDao.update1(tid, tname, tisparenttype, tparenttypeid);
        return 0;
    }

    @Override
    public PageTable<ProductsType> select(int page, int limit) {
        System.out.println("进入service");
        PageHelper.startPage(page, limit);
        List<ProductsType> list = productstypeDao.select();
        System.out.println("*******************");
        for (ProductsType s : list) {
            System.out.println(s);
        }
        PageInfo<ProductsType> pageInfo = new PageInfo<>(list);
        PageTable<ProductsType> pageTable = new PageTable<>();
        pageTable.setCode(0);
        pageTable.setCount(pageInfo.getTotal());
        pageTable.setData(pageInfo.getList());
        return pageTable;

    }

    @Override
    public ProductsType getparentname(String tname, Integer tid) {
        return productstypeDao.getparentname(tname, tid);
    }


}
