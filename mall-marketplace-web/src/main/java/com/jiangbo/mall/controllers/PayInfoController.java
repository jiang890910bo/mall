package com.jiangbo.mall.controllers;

import com.jiangbo.mall.biz.OrderBiz;
import com.jiangbo.mall.biz.PayInfoBiz;
import com.jiangbo.mall.biz.RedisClientBiz;
import com.jiangbo.mall.util.SessionUtil;
import com.jiangbo.mall.vo.OrderVO;
import com.jiangbo.mall.vo.PayInfoVO;
import com.jiangbo.mall.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;

/**
 * 支付信息
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Controller
@RequestMapping("/payInfo")
public class PayInfoController extends BaseController {
    @Autowired
    PayInfoBiz PayInfoBiz;
    @Autowired
    OrderBiz orderBiz;
    @Autowired
    RedisClientBiz redisClientBiz;

    /**
     * 支付详情信息列表
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/payInfoList")
    public String payInfoList(ModelMap modelMap){
        modelMap.addAttribute("payInfoList", PayInfoBiz.getPayInfosByCondition(new PayInfoVO()));
        return "payInfo/payInfoList";
    }

    /**
     * 跳转支付页面
     * @param orderId
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/pay/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String toPay(ModelMap modelMap, @PathVariable("orderId") int orderId, HttpServletRequest request){
        UserVO userVO = redisClientBiz.getObject(SessionUtil.getSessionId(request), UserVO.class);
        modelMap.addAttribute("user", userVO);
        OrderVO orderVO = orderBiz.getById(orderId);
        modelMap.addAttribute("orderVO", orderVO);
        return "pay";
    }

}
