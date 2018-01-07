package com.jiangbo.mall.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户角色
 * @author Jiangbo Cheng on 2017/10/24.
 */
public enum UserRoleEnum {
    SUPER_ADMIN(0, "超级管理员"),
    ADMIN(1, "普通管理员"),
    CUSTOMER(2, "普通用户");

    private Integer num;

    private String desc;

    UserRoleEnum(Integer num, String desc) {
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

    public static Map<String, String> getAllRoleMap(){
        Map<String, String> roleMap = new HashMap<>();
        roleMap.put(SUPER_ADMIN.getNum().toString(), SUPER_ADMIN.getDesc());
        roleMap.put(ADMIN.getNum().toString(), ADMIN.getDesc());
        roleMap.put(CUSTOMER.getNum().toString(), CUSTOMER.getDesc());

        return roleMap;
    }

    public static Map<String, String> getNormalRoleMap(){
        Map<String, String> roleMap = new HashMap<>();
        roleMap.put(ADMIN.getNum().toString(), ADMIN.getDesc());
        roleMap.put(CUSTOMER.getNum().toString(), CUSTOMER.getDesc());

        return roleMap;
    }

    public static Map<String, String> getCustomerRoleMap(){
        Map<String, String> roleMap = new HashMap<>();
        roleMap.put(CUSTOMER.getNum().toString(), CUSTOMER.getDesc());

        return roleMap;
    }
}
