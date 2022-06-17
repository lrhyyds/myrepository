package com.nb.yyds220118.service.impl;

import com.nb.yyds220118.dao.IAdminNoticeDao;
import com.nb.yyds220118.pojo.Notice;
import com.nb.yyds220118.service.IAdminNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/20 13:37
 * @description：公告Service
 */
@Service
public class AdminNoticeServiceImpl implements IAdminNoticeService {
    @Autowired
    IAdminNoticeDao iAdminNoticeDao;
    /*
    * 查询所有公告
    * */
    @Override
    public List<Notice> getallnotice() {
        return null;
    }
/*
* 根据留言id删除公告
* */
    @Override
    public int delnoticeBynoid(int noid) {
       int m= iAdminNoticeDao.delnoticeBynoid(noid);
        return m;
    }
/*
* 修改审核状态
* */
    @Override
    public int updatenoticeBynoid(Notice notice) {
      int m=  iAdminNoticeDao.updatenoticeBynoid(notice);
        return m;
    }
/*
* 查询对应审核状态的公告
* */
    @Override
    public List<Notice> getnoticeisbystatus(int notice) {
       List<Notice> list= iAdminNoticeDao.getnoticeisbystatus(notice);
        return list;
    }
    //批量删除公告
    @Override
    public int delbatchnotice(List<Integer> list) {
        return iAdminNoticeDao.delbatchnotice(list);
    }



}
