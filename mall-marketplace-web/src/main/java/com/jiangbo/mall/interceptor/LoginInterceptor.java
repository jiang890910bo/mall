package com.jiangbo.mall.interceptor;

import com.jiangbo.mall.biz.RedisClientBiz;
import com.jiangbo.mall.bo.UserBO;
import com.jiangbo.mall.util.SessionKeyUtil;
import com.jiangbo.mall.util.SessionUtil;
import com.jiangbo.mall.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jiangbo Cheng on 2017/11/4.
 */

public class LoginInterceptor implements HandlerInterceptor {
    private static final String IMAGE_CATEGORY = "/img";
    private static final String CSS_CATEGORY = "/css";
    private static final String JS_CATEGORY = "/js";

    @Autowired
    private RedisClientBiz redisClientBiz;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获得请求的url路径
        String uri = request.getRequestURI();
        if(uri.indexOf(IMAGE_CATEGORY) == 0 || uri.indexOf(CSS_CATEGORY) == 0 || uri.indexOf(JS_CATEGORY) == 0 ){
            return true;//静态资源
        }else if(uri.contains("/index") || uri.contains("/checkLogin") || uri.contains("/checkUser")|| uri.contains("/toLogin")|| uri.contains("/toRegister")|| uri.contains("/register")|| uri.contains("/login")){
            return true;
            // 其他情况判断session中是否有key，有的话继续用户的操作
        }else if(SessionUtil.getSessionId(request) != null && redisClientBiz.getObject(SessionUtil.getSessionId(request), UserVO.class) != null){
            return true;
        }

        // 最后的情况就是进入登录页面
        response.sendRedirect(request.getContextPath() + "/login/toLogin");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
