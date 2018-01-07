package com.jiangbo.mall.controllers;

import com.jiangbo.mall.biz.OrderItemBiz;
import com.jiangbo.mall.vo.OrderItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 订单详情
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Controller
@RequestMapping("/orderItem")
public class OrderItemController extends BaseController {
    @Autowired
    OrderItemBiz orderItemBiz;

    @RequestMapping(value="/orderItemList")
    public String orderList(ModelMap modelMap){
        modelMap.addAttribute("orderItemList", orderItemBiz.getOrderItemsByCondition(new OrderItemVO()));
        return "order/orderItemList";
    }

}
