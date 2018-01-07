package com.jiangbo.mall.controllers;

import com.jiangbo.mall.biz.RedisClientBiz;
import com.jiangbo.mall.biz.UserBiz;
import com.jiangbo.mall.util.SessionUtil;
import com.jiangbo.mall.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 登陆
 * @author Jiangbo Cheng on 2017/10/20.
 */
@Controller
@RequestMapping(value = "/index")
public class IndexController extends BaseController{

    @Autowired
    private UserBiz userBiz;
    @Autowired
    RedisClientBiz redisClientBiz;

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, ModelMap modelMap){
        String sessionId = SessionUtil.getSessionId(request);
        if(sessionId != null){
            modelMap.addAttribute("user", redisClientBiz.get(sessionId, UserVO.class));
        }
        return "index";
    }

    @RequestMapping(value = "/top")
    public String top(){
        return "top";
    }

    @RequestMapping(value = "/left")
    public String left(){
        return "left";
    }

    @RequestMapping(value = "/main")
    public String main(){
        return "main";
    }
}
