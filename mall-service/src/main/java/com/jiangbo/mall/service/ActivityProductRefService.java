package com.jiangbo.mall.service;

import com.jiangbo.mall.bo.ActivityProductRefBO;

import java.util.List;

/**
 * @author Jiangbo Cheng on 2017/10/18.
 */

public interface ActivityProductRefService extends AbstractService<ActivityProductRefBO> {
    /**
     * 根据活动id和状态查询绑定活动的商品Id
     * @param activityId
     * @return
     */
    List<Integer> selectProductIdListByActivityIdAndStatus(Integer activityId, Integer status);

    boolean subtractStock(Integer activityProductRefId, int num, int status);
}
