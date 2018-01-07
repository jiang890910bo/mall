package com.jiangbo.mall.controllers;

import com.jiangbo.mall.biz.ProductBiz;
import com.jiangbo.mall.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 产品
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {
    @Autowired
    ProductBiz productBiz;

    @RequestMapping(value="/productList")
    public String productList(ModelMap modelMap){
        modelMap.addAttribute("productList", productBiz.getProductsByCondition(new ProductVO()));
        modelMap.addAttribute("dataStatusMap", dataStatusMap);
        return "product/productList";
    }

}
