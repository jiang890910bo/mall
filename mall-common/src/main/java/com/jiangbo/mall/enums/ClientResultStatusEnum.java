package com.jiangbo.mall.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiangbo Cheng on 2017/10/19.
 * 客户端结果状态枚举
 */
public enum ClientResultStatusEnum {
    /**
     * 正确
     */
    CORRECT(0, "正确"),
    /**
     * 错误
     */
    ERROR(-1, "系统繁忙，请稍后再试"),
    /**
     * 其他
     */
    OTHER(1,"自定义")
    ;

    private Integer num;

    private String desc;

    ClientResultStatusEnum(int num, String desc) {
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
        statusMap.put(CORRECT.getNum().toString(), CORRECT.getDesc());
        statusMap.put(ERROR.getNum().toString(), ERROR.getDesc());

        return statusMap;
    }
}
