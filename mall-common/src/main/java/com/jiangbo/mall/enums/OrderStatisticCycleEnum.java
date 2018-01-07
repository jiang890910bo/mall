package com.jiangbo.mall.enums;

/**
 * @author Jiangbo Cheng on 2017/11/15.
 */

public enum OrderStatisticCycleEnum {

    /**
     * 按天统计
     */
    DAY("%Y-%m-%d"),
    /**
     * 按时统计
     */
    HOUR("%Y-%m-%d %h"),
    /**
     * 按分统计
     */
    MINUTE("%Y-%m-%d %h:%m"),
    /**
     * 按秒统计
     */
    SECOND("%Y-%m-%d %h:%m:%s");

    private String value;

    OrderStatisticCycleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
