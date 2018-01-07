package com.jiangbo.mall.enums;

/**
 * @author Jiangbo Cheng on 2017/11/10.
 * 支付类型
 */
public enum PaymentTypeEnum {
    ALI_PAY("在线支付"),
    WECHAT_PAY("微信支付");

    private String desc;

    PaymentTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
