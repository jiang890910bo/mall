package com.jiangbo.mall.dao;

import com.jiangbo.mall.pojo.OrderPO;
import com.jiangbo.mall.pojo.OrderStatisticPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderPOMapper extends AbstractPOMapper<OrderPO>{

    List<OrderStatisticPO> statisticOrder(@Param("formatString") String formatString);
}