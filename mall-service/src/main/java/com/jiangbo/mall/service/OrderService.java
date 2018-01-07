package com.jiangbo.mall.service;

import com.jiangbo.mall.bo.OrderBO;
import com.jiangbo.mall.bo.OrderStatisticBO;

import java.util.List;
import java.util.Map;

/**
 * @author Jiangbo Cheng on 2017/10/18.
 */

public interface OrderService extends AbstractService<OrderBO> {
    List<OrderStatisticBO> statisticOrder(String formatString);
}
