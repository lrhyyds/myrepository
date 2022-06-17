package com.nb.yyds220118.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageTable<T> {
    private long count;
    private int code;
    private String msg;
    private List<T> data;
}
