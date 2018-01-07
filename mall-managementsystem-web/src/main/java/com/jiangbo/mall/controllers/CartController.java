package com.jiangbo.mall.controllers;

import com.jiangbo.mall.biz.CartBiz;
import com.jiangbo.mall.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 购物车
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {
    @Autowired
    CartBiz cartBiz;

    @RequestMapping(value="/cartList")
    public String cartList(ModelMap modelMap){
        modelMap.addAttribute("cartList", cartBiz.getCartsByCondition(new CartVO()));
        return "cart/cartList";
    }

}
