package com.nb.yyds220118.dao;

import com.nb.yyds220118.pojo.News;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/20 17:13
 * @description：
 */
public interface IAdminNewsDao {
    List<News> getallnew();//新闻管理
    int delnews(int oId);//删除新闻
    int addnews(News news);//增加新闻
    int updatenews(News news);//修改新闻
    List<News> getnewsBynestatus(int status);//查询发布状态的新闻
    int delbatchnews(@Param("list") List<Integer> list);//批量删除新闻
}
