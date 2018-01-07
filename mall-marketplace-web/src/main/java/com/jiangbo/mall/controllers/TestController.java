package com.jiangbo.mall.controllers;

import com.jiangbo.mall.biz.UserBiz;
import com.jiangbo.mall.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 测试controller
 * @author Jiangbo Cheng on 2017/10/20.
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private UserBiz userBiz;

    @RequestMapping(value = "/queryUserList")
    public String queryUserList(ModelMap model){
        List<UserVO> userList = userBiz.getUsersByCondition(new UserVO());
        model.addAttribute("userList", userList);
        return "index";
    }
}
