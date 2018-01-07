package com.jiangbo.mall.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiangbo Cheng on 2017/10/19.
 * 数据状态枚举
 */
public enum DataStatusEnum {
    /**
     * 正常的
     */
    NORMAL(1, "正常"),
    /**
     * 被遗弃的
     */
    ABANDONED(2, "弃用"),
    /**
     * 被删除的
     */
    DELETED(3, "被删除的"),
    ;

    private Integer num;

    private String desc;

    DataStatusEnum(Integer num, String desc) {
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

    public static Map<String, String> getStatusMap(){
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put(NORMAL.getNum().toString(), NORMAL.getDesc());
        statusMap.put(ABANDONED.getNum().toString(), ABANDONED.getDesc());
        statusMap.put(DELETED.getNum().toString(), DELETED.getDesc());

        return statusMap;
    }
}
