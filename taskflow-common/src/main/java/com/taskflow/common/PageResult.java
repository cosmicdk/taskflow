package com.taskflow.common;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long page;
    private Long size;
    private Long total;
    private Long pages;
    private List<T> records;

    private PageResult() {}

    public static <T> PageResult<T> of(long page, long size, long total, List<T> records) {
        PageResult<T> result = new PageResult<>();
        result.page = page;
        result.size = size;
        result.total = total;
        result.pages = (total + size - 1) / size;
        result.records = records;
        return result;
    }
}