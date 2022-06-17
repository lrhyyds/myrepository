package com.nb.yyds220118.service;

import com.nb.yyds220118.pojo.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/20 13:36
 * @description：公告Service接口层
 */
public interface IAdminNoticeService {
    List<Notice> getallnotice();//查询所有公告
    int delnoticeBynoid(int noid);//删除公告
    int updatenoticeBynoid(Notice notice);//修改公告
    List<Notice> getnoticeisbystatus(int notice);//查询对应状态的公告
    int delbatchnotice(@Param("list") List<Integer> list);//批量删除公告
}
