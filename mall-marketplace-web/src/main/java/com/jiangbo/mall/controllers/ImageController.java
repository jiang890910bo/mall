package com.jiangbo.mall.controllers;

import com.jiangbo.mall.biz.ImageBiz;
import com.jiangbo.mall.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 图片
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Controller
@RequestMapping("/image")
public class ImageController extends BaseController {
    @Autowired
    ImageBiz imageBiz;



    @RequestMapping(value="/imageList")
    public String activityList(ModelMap modelMap){
        modelMap.addAttribute("imageList", imageBiz.getByCondition(new ImageVO()));
        modelMap.addAttribute("dataStatusMap", dataStatusMap);
        return "image/imageList";
    }

}
