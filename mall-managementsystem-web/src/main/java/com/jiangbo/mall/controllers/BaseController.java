package com.jiangbo.mall.controllers;

import com.jiangbo.mall.enums.*;
import com.jiangbo.mall.vo.UserVO;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

/**
 * 基础Controller
 * @author Jiangbo Cheng on 2017/10/24.
 */

public class BaseController {

    /**
     * 数据状态
     */
    protected static final Map<String, String> dataStatusMap = DataStatusEnum.getStatusMap();
    /**
     * 普通用户角色
     */
    protected static final Map<String, String> userNormalRoleMap = UserRoleEnum.getNormalRoleMap();
    /**
     * 普通用户角色
     */
    protected static final Map<String, String> userCustomerRoleMap = UserRoleEnum.getCustomerRoleMap();
    /**
     * 所有用户角色
     */
    protected static final Map<String, String> userAllRoleMap = UserRoleEnum.getAllRoleMap();
    /**
     * 活动状态
     */
    protected static final Map<String, String> activityStatusMap = ActivityStatusEnum.getActivityStatusMap();

    /**
     * 产品状态
     */
    protected static final Map<String, String> productStatusMap = ProductStatusEnum.getProductStatusMap();

    /**
     * 订单状态
     */
    protected static  final Map<String, String> orderStatusMap = OrderStatusEnum.getOrderStatusMap();


    protected  static Map<String, String> getUserRoleMap(Integer role){
        if(role.equals(UserRoleEnum.SUPER_ADMIN.getNum())) {
            return userAllRoleMap;
        }else{
            return userNormalRoleMap;
        }
    }

}
