package com.nb.yyds220118.control;



import com.nb.yyds220118.pojo.ProductsType;

import com.nb.yyds220118.service.impl.ProductstypeServiceImpl;
import com.nb.yyds220118.util.PageTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：javasun
 * @date ：Created in 2022/1/19 11:34
 * @description：
 */
@RestController
public class ProductstypeControl {
    @Autowired
    ProductstypeServiceImpl pro;

    @RequestMapping("list")
    PageTable<ProductsType> list(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int limit) {
        return pro.select(page, limit);
    }

    @RequestMapping("delete")
    int delete(int tid) {
        pro.delete(tid);
        return 0;
    }

    @RequestMapping("add1")
    int add1(ProductsType p) {

        System.out.println("要看到自己添加的" + p.getTname());

        pro.add1(p.getTname(), p.getTparenttypeid(), p.getTisparenttype());
        return 0;
    }

    @RequestMapping("update1")
    int update1(Integer tid, String tname, BigDecimal tisparenttype, BigDecimal tparenttypeid) {
        pro.update1(tid, tname, tisparenttype, tparenttypeid);
        return 0;
    }

}
