package com.jiangbo.mall.dao;

import com.jiangbo.mall.pojo.ActivityPO;

import java.util.Date;
import java.util.List;

public interface ActivityPOMapper  extends AbstractPOMapper<ActivityPO> {

    int queryByTime(Date time);

    List<ActivityPO> selectCurrentActivity();

    List<ActivityPO> selectLatestActivity();

    /**
     * 是否有当前正在开始的活动
     * @return
     */
    int hasCurrentActivity();
}