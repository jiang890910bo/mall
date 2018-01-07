package com.jiangbo.mall.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiangbo Cheng on 2017/10/29.
 */

public enum ProductStatusEnum {
    /**
     * 待售
     */
    WATING_SALE(1, "待售"),
    /**
     * 上架（已发布）
     */
    PUBLISHING(2, "上架"),
    /**
     * 缺货
     */
    STOCKOUT(3, "缺货"),
    /**
     * 下架
     */
    LEFT_SHELF(4, "下架"),
    ;

    private Integer num;

    private String desc;

    ProductStatusEnum(int num, String desc) {
        this.num = num;
        this.desc = desc;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static Map<String, String> getProductStatusMap(){
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put(WATING_SALE.getNum().toString(), WATING_SALE.getDesc());
        statusMap.put(PUBLISHING.getNum().toString(), PUBLISHING.getDesc());
        statusMap.put(STOCKOUT.getNum().toString(), STOCKOUT.getDesc());
        statusMap.put(LEFT_SHELF.getNum().toString(), LEFT_SHELF.getDesc());

        return statusMap;
    }
}
