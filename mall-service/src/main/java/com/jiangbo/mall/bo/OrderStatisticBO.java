package com.jiangbo.mall.bo;

import java.io.Serializable;

/**
 * @author Jiangbo Cheng on 2017/11/15.
 */

public class OrderStatisticBO implements Serializable{


    private static final long serialVersionUID = 820121758813711118L;
    private String timeStr;

    private Integer count;

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
