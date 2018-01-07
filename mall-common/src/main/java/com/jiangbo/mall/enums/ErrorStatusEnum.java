package com.jiangbo.mall.enums;

/**
 * @author Jiangbo Cheng on 2017/10/19.
 * 错误类型枚举
 */
public enum ErrorStatusEnum {
    //登陆错误 1000-2000

    //订单错误2001-3000
    STOCK_SOLD_OUT(2001, "已抢完"),
    UNDER_STOCK(2002, "库存不足"),

    //其他错误 3001-4000

    //逻辑错误 5001-6001
    DATA_NOT_FOUND_IN_DB(60001, "数据库未查到数据");

    private int num;

    private String desc;

    ErrorStatusEnum(int num, String desc) {
        this.num = num;
        this.desc = desc;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
