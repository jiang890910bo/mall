package com.jiangbo.mall.dao;

import com.jiangbo.mall.pojo.ActivityProductRefPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityProductRefPOMapper  extends AbstractPOMapper<ActivityProductRefPO> {

    List<Integer> selectProductIdListByActivityIdAndStatus(@Param("activityId")Integer activityId, @Param("status")Integer status);

    boolean subtractStock(@Param("activityProductRefId")Integer activityProductRefId, @Param("num")Integer num
            , @Param("status")Integer status);
}