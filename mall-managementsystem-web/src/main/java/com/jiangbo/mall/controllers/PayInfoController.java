package com.jiangbo.mall.controllers;

import com.jiangbo.mall.biz.PayInfoBiz;
import com.jiangbo.mall.vo.PayInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 支付信息
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Controller
@RequestMapping("/payInfo")
public class PayInfoController extends BaseController {
    @Autowired
    PayInfoBiz PayInfoBiz;

    @RequestMapping(value="/payInfoList")
    public String payInfoList(ModelMap modelMap){
        modelMap.addAttribute("payInfoList", PayInfoBiz.getPayInfosByCondition(new PayInfoVO()));
        return "payInfo/payInfoList";
    }

}
