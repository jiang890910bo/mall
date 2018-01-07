package com.jiangbo.mall.service;

import com.jiangbo.mall.bo.ActivityBO;
import com.jiangbo.mall.bo.ActivityProductRefBO;

import java.util.Date;
import java.util.List;

/**
 * @author Jiangbo Cheng on 2017/10/18.
 */

public interface ActivityService extends AbstractService<ActivityBO> {

    int queryByTime(Date time);

    List<ActivityBO> selectCurrentActivity();

    /**
     * 查询距离当前最近时间待开始的活动
     * @return
     */
    List<ActivityBO> selectLatestActivity();

    /**
     * 是否有当前开始的活动
     * @return
     */
    boolean hasCurrentActivity();
}
