package com.jiangbo.mall.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiangbo Cheng on 2017/11/10.
 */

public enum OrderStatusEnum {
    //0-已取消，10-未付款，20-已付款，30-已发货，40-交易成功，50-交易关闭
    /*
    已取消
     */
    CANCELED(0, "已取消"),
    /**
     * 待支付（微支付）
     */
    UNPAID(10, "未支付"),
    /**
     * 已付款（等该发货）
     */
    PAID(20, "已付款"),
    /**
     * 已发货
     */
    DELIVERED(30, "已发货"),
    /**
     * 交易成功（已经签收）
     */
    TRADE_SUCCESSFULLY(40, "交易成功"),
    /**
     * 交易关闭
     */
    TRADE_CLOSE(50, "交易关闭");

    OrderStatusEnum(Integer num, String desc) {
        this.num = num;
        this.desc = desc;
    }

    private Integer num;

    private String desc;

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

    public static Map<String, String> getOrderStatusMap(){
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put(CANCELED.getNum().toString(), CANCELED.getDesc());
        statusMap.put(UNPAID.getNum().toString(), UNPAID.getDesc());
        statusMap.put(PAID.getNum().toString(), PAID.getDesc());
        statusMap.put(DELIVERED.getNum().toString(), DELIVERED.getDesc());
        statusMap.put(TRADE_SUCCESSFULLY.getNum().toString(), TRADE_SUCCESSFULLY.getDesc());
        statusMap.put(TRADE_CLOSE.getNum().toString(), TRADE_CLOSE.getDesc());

        return statusMap;
    }
}
