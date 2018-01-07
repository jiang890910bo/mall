package com.jiangbo.mall.controllers;

import com.jiangbo.mall.biz.ShippingBiz;
import com.jiangbo.mall.vo.ShippingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 收获地址
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Controller
@RequestMapping("/shipping")
public class ShippingController extends BaseController {
    @Autowired
    ShippingBiz shippingBiz;

    @RequestMapping(value="/shippingList")
    public String shippingList(ModelMap modelMap){
        modelMap.addAttribute("shippingList", shippingBiz.getShippingsByCondition(new ShippingVO()));
        return "shipping/shippingList";
    }

}
