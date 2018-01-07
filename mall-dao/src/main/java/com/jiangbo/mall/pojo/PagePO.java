package com.jiangbo.mall.pojo;

import java.util.List;

/**
 * @author Jiangbo Cheng on 2017/12/1.
 */

public class PagePO<T> {

    private Integer index;

    private Integer size;

    private List<T> list;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
