package com.jiangbo.mall.controllers;

import com.jiangbo.mall.biz.ActivityBiz;
import com.jiangbo.mall.vo.ActivityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 活动
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {
    @Autowired
    ActivityBiz activityBiz;

    @RequestMapping(value="/activityList")
    public String activityList(ModelMap modelMap){
        modelMap.addAttribute("activityList", activityBiz.getByCondition(new ActivityVO()));
        modelMap.addAttribute("dataStatusMap", dataStatusMap);
        modelMap.addAttribute("activityStatusMap", activityStatusMap);
        return "activity/activityList";
    }

}
