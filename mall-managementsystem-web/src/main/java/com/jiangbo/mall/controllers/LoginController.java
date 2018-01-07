package com.jiangbo.mall.controllers;

import com.jiangbo.mall.ClientResult;
import com.jiangbo.mall.biz.RedisClientBiz;
import com.jiangbo.mall.biz.UserBiz;
import com.jiangbo.mall.bo.UserBO;
import com.jiangbo.mall.enums.ClientResultStatusEnum;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.enums.UserRoleEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.util.SessionKeyUtil;
import com.jiangbo.mall.util.SessionUtil;
import com.jiangbo.mall.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.security.provider.MD5;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆
 * @author Jiangbo Cheng on 2017/10/20.
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController extends BaseController{
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserBiz userBiz;

    @Autowired
    private RedisClientBiz redisClientBiz;

    /**
     * 跳转到登陆
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }

    /**
     * 跳转到登陆
     * @param request
     * @return
     */
    @RequestMapping(value = "/toLogin")
    public String loginIndex(HttpServletRequest request){
        String sessionId = SessionUtil.getSessionId(request);
        if(sessionId != null && redisClientBiz.get(sessionId, UserVO.class) != null){
            return "redirect:/index/main";
        }
        return "jumpToLogin";
    }

    /**
     * 退出登陆
     * @param request
     * @return
     */
    @RequestMapping(value = "/exit")
    public String exit(HttpServletRequest request, HttpServletResponse response){
        String sessionId = SessionUtil.getSessionId(request);
        redisClientBiz.del(sessionId);
        SessionUtil.clearCookie(request, response, null);
        return "jumpToLogin";
    }

    /**
     * 后台异步登陆
     * @param loginName
     * @param password
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ClientResult loginIndex(@RequestParam(value="loginName")String loginName, @RequestParam(value="password") String password,
            HttpServletRequest request, HttpServletResponse response){
        ClientResult clientResult= new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            UserVO userVO = userBiz.checkLoginUser(loginName, password);
            if(userVO == null){
                clientResult.setErrorMsg("账号或密码不正确");
            }else if(userVO.getRole().intValue() == UserRoleEnum.CUSTOMER.getNum().intValue()){
                clientResult.setErrorMsg("该账号没权限登陆");
            }else if(userVO.getStatus().intValue() == DataStatusEnum.ABANDONED.getNum().intValue()){
                clientResult.setErrorMsg("该账号被禁用");
            }else{
                String sessionId = SessionUtil.createSession(userVO.getId(), request, response);
                redisClientBiz.set(sessionId, userVO, SessionKeyUtil.THIRTY_MINUTE);
                clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
            }
        }catch(Exception e){
            logger.error(e.getMessage());
        }

        return clientResult;
    }
}
