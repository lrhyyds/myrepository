package com.nb.yyds220118.dao;

import com.nb.yyds220118.pojo.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/20 11:14
 * @description：
 */
public interface IAdminNoticeDao {
    List<Notice> getallnotice();//查询公告
    int addnotice(Notice notice);//增加公告
    int delnoticeBynoid(int noid);//删除公告
    int updatenoticeBynoid(Notice notice);//修改公告
    List<Notice> getnoticeisbystatus(int notice);//查询对应状态的公告
    int delbatchnotice(@Param("list") List<Integer> list);//批量删除公告

}
