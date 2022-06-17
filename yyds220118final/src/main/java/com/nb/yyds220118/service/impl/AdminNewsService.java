package com.nb.yyds220118.service.impl;

import com.nb.yyds220118.dao.IAdminNewsDao;
import com.nb.yyds220118.pojo.News;
import com.nb.yyds220118.service.IAdminNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/20 21:33
 * @description：新闻Service
 */
@Service
public class AdminNewsService implements IAdminNewsService {
    @Autowired
    IAdminNewsDao iAdminNewsDao;
    /*
    * 查询所有新闻
    * */
    @Override
    public List<News> getallnew() {
       List<News> list= iAdminNewsDao.getallnew();
        return list;
    }
/*
* 删除新闻
* */
    @Override
    public int delnews(int oId) {
       int m= iAdminNewsDao.delnews(oId);
        return m;
    }
/*
* 增加新闻
* */
    @Override
    public int addnews(News news) {
     int m=   iAdminNewsDao.addnews(news);
        return m;
    }
/*
* 修改新闻
* */
    @Override
    public int updatenews(News news) {
        int m=  iAdminNewsDao.updatenews(news);
        return m;
    }
/*
*查询对应发布状态的新闻
* */
    @Override
    public List<News> getnewsBynestatus(int status) {
        List<News> list=iAdminNewsDao.getnewsBynestatus(status);
        return list;
    }

    /*
    * 批量删除新闻
    * */
    @Override
    public int delbatchnews(List<Integer> inp){
       int m= iAdminNewsDao.delbatchnews(inp);

       return m;
    }

}
