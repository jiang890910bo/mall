package com.jiangbo.mall.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiangbo Cheng on 2017/10/29.
 */

public enum ActivityStatusEnum {
    /**
     * 待审核
     */
    WATING_AUDIT(1, "待审核"),
    /**
     * 待发布(已审核)
     */
    WATING_PUBLIC(2, "待发布(已审核)"),
    /**
     * 已发布
     */
    PUBLISHING(3, "已发布"),
    ;

    private Integer num;

    private String desc;

    ActivityStatusEnum(int num, String desc) {
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

    public static Map<String, String> getActivityStatusMap(){
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put(WATING_AUDIT.getNum().toString(), WATING_AUDIT.getDesc());
        statusMap.put(WATING_PUBLIC.getNum().toString(), WATING_PUBLIC.getDesc());
        statusMap.put(PUBLISHING.getNum().toString(), PUBLISHING.getDesc());

        return statusMap;
    }
}
