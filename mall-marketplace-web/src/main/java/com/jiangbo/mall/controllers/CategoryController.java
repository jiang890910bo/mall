package com.jiangbo.mall.controllers;

import com.jiangbo.mall.biz.CategoryBiz;
import com.jiangbo.mall.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品目录
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController {
    @Autowired
    CategoryBiz categoryBiz;

    @RequestMapping(value="/categoryList")
    public String categoryList(ModelMap modelMap){
        modelMap.addAttribute("categoryList", categoryBiz.getCategorysByCondition(new CategoryVO()));
        modelMap.addAttribute("dataStatusMap", dataStatusMap);
        return "category/categoryList";
    }

}
