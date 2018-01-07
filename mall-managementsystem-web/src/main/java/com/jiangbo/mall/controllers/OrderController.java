package com.jiangbo.mall.controllers;

import com.alibaba.fastjson.JSONObject;
import com.jiangbo.mall.ClientResult;
import com.jiangbo.mall.biz.OrderBiz;
import com.jiangbo.mall.enums.ClientResultStatusEnum;
import com.jiangbo.mall.enums.OrderStatisticCycleEnum;
import com.jiangbo.mall.vo.OrderStatisticVO;
import com.jiangbo.mall.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {
    @Autowired
    OrderBiz orderBiz;

    @RequestMapping(value="/orderList")
    public String orderList(@RequestParam(value = "pageIndex", required = false, defaultValue = "1")Integer pageIndex, @RequestParam(value = "pageSize", required = false, defaultValue = "10")Integer pageSize,ModelMap modelMap){
        modelMap.addAttribute("pageInfo", orderBiz.getOrdersByCondition(new OrderVO(), pageIndex, pageSize));
        modelMap.addAttribute("orderStatusMap", orderStatusMap);
        return "order/orderList";
    }

    /**
     * 订单统计
     * @param timeFormat
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/statistics")
    public String statistics(@RequestParam (value = "timeFormat", required = false, defaultValue = "HOUR")String timeFormat, ModelMap modelMap){
        modelMap.addAttribute("statisticData", orderBiz.statisticOrder(OrderStatisticCycleEnum.valueOf(timeFormat).getValue()));
        return "order/statistics";
    }


    /**
     * 手动缴费
     * @param orderId
     * @return
     */
    @RequestMapping(value="/handPay")
    public ClientResult handPay(@RequestParam("orderId")Integer orderId){
        ClientResult clientResult= new ClientResult(ClientResultStatusEnum.ERROR);
        if(orderBiz.handPay(orderId) > 0){
            clientResult= new ClientResult(ClientResultStatusEnum.CORRECT);
        }

        return clientResult;
    }
}
