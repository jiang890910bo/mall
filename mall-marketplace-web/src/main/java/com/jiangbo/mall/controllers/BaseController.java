package com.jiangbo.mall.controllers;

import com.jiangbo.mall.enums.ActivityStatusEnum;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.enums.UserRoleEnum;

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
     * 用户角色
     */
    protected static final Map<String, String> userRoleMap = UserRoleEnum.getAllRoleMap();
    /**
     * 活动状态
     */
    protected static final Map<String, String> activityStatusMap = ActivityStatusEnum.getActivityStatusMap();

}
