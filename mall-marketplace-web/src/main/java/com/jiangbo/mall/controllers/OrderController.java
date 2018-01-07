package com.jiangbo.mall.controllers;

import com.jiangbo.mall.biz.*;
import com.jiangbo.mall.enums.ErrorStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.util.SessionUtil;
import com.jiangbo.mall.vo.OrderVO;
import com.jiangbo.mall.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {
    @Autowired
    OrderBiz orderBiz;
    @Autowired
    ProductBiz productBiz;
    @Autowired
    ActivityProductRefBiz activityProductRefBiz;

    @Autowired
    ShippingBiz shippingBiz;
    @Autowired
    RedisClientBiz redisClientBiz;



    /**
     * 订单列表
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/myOrderList")
    public String myOrderList(ModelMap modelMap, HttpServletRequest request){
        String sessionIdStr = SessionUtil.getSessionId(request);
        UserVO userVO = redisClientBiz.getObject(sessionIdStr, UserVO.class);
        modelMap.addAttribute("user", userVO);
        OrderVO orderVO = new OrderVO();
        orderVO.setUserId(userVO.getId());
        modelMap.addAttribute("orderList", orderBiz.getOrdersByCondition(orderVO));
        return "myOrderList";
    }

    /**
     * 订单确认
     * @param activityProductRefId
     * @param activityId
     * @return
     */
    @RequestMapping(value="/orderConfirm", produces = MediaType.APPLICATION_JSON_VALUE)
    public String orderConfirm(ModelMap modelMap, @RequestParam("activityProductRefId") Integer activityProductRefId, @RequestParam("activityId") Integer activityId, HttpServletRequest request){
        UserVO userVO = redisClientBiz.getObject(SessionUtil.getSessionId(request), UserVO.class);
        modelMap.addAttribute("user", userVO);
        modelMap.addAttribute("activityId", activityId);
        modelMap.addAttribute("activityProductRef", activityProductRefBiz.getById(activityProductRefId));
        modelMap.addAttribute("shippingList", shippingBiz.getShippingsByUserId(userVO.getId()));
        return "confirm";
    }

    /**
     * 下单
     * @param activityProductRefId
     * @param request
     * @return
     */
    @RequestMapping(value = "/order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String order(@RequestParam("activityProductRefId")Integer activityProductRefId,@RequestParam("shippingId")Integer shippingId, HttpServletRequest request){
        try{
            UserVO userVO = redisClientBiz.getObject(SessionUtil.getSessionId(request), UserVO.class);
            int orderId = orderBiz.saveOrder(activityProductRefId, shippingId, userVO.getId(), 1);
            if(orderId > 0){
                return "redirect:/payInfo/pay/"+orderId;
            }
        }catch(MallException e){
            if(e.getErrorNum() == ErrorStatusEnum.UNDER_STOCK.getNum()){
                return "stock_not_enough";
            }else if(e.getErrorNum() == ErrorStatusEnum.STOCK_SOLD_OUT.getNum()){
                return "stock_sold_out";
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
