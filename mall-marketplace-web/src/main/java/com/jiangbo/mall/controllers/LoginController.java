package com.jiangbo.mall.controllers;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.jiangbo.mall.ClientResult;
import com.jiangbo.mall.biz.RedisClientBiz;
import com.jiangbo.mall.biz.UserBiz;
import com.jiangbo.mall.bo.UserBO;
import com.jiangbo.mall.enums.ClientResultStatusEnum;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.enums.UserRoleEnum;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.Random;

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

    @RequestMapping(value = "/toLogin")
    public String loginIndex(HttpServletRequest request){
        try{
            String sessionIdStr = SessionUtil.getSessionId(request);
            if(sessionIdStr != null) {
                UserVO userVO = redisClientBiz.getObject(sessionIdStr, UserVO.class);
                if (sessionIdStr != null && userVO != null) {
                    return "redirect: /index/main";
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "login";
    }

    @RequestMapping(value = "/toRegister")
    public String toRegister(HttpServletRequest request){
        String sessionIdStr = SessionUtil.getSessionId(request);
        if(sessionIdStr != null) {
            UserVO userVO = redisClientBiz.getObject(sessionIdStr, UserVO.class);
            if (sessionIdStr != null && userVO != null) {
                return "redirect: /index/main";
            }
        }
        return "register";
    }

    @RequestMapping(value = "/exit")
    public String exit(HttpServletRequest request, HttpServletResponse response){
        String sessionIdStr = SessionUtil.getSessionId(request);
        SessionUtil.clearCookie(request, response, null);
        if(redisClientBiz.del(sessionIdStr)){
            logger.warn("警告：redis删除失败错误...");
        }
        return "login";
    }

    /**
     * 前台登陆
     * @param loginName
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ClientResult login(@RequestParam(value="loginName")String loginName, @RequestParam(value="password") String password,
                                   HttpServletRequest request, HttpServletResponse response){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            UserVO userVO = userBiz.checkLoginUser(loginName, password);
            if(userVO == null){
                clientResult.setErrorMsg("账号或密码不正确");
            }else if(userVO.getStatus().intValue() == DataStatusEnum.ABANDONED.getNum().intValue()){
                clientResult.setErrorMsg("该账号被禁用");
            }else{
                String sessionIdStr = SessionUtil.createSession(userVO, request, response);
                redisClientBiz.setObject(sessionIdStr, userVO, SessionKeyUtil.THIRTY_MINUTE);
                clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return clientResult;
    }

    /**
     * 登陆异步检查
     * @param loginName
     * @param password
     * @return
     */
    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ClientResult checkLogin(@RequestParam(value="loginName")String loginName, @RequestParam(value="password") String password,
                                   HttpServletRequest request, HttpServletResponse response){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            UserVO userVO = userBiz.checkLoginUser(loginName, password);
            if(userVO == null){
                clientResult.setErrorMsg("账号或密码不正确");
            }else if(userVO.getStatus().intValue() == DataStatusEnum.ABANDONED.getNum().intValue()){
                clientResult.setErrorMsg("该账号被禁用");
            }else{
                String sessionIdStr = SessionUtil.createSession(userVO, request, response);
                redisClientBiz.setObject(sessionIdStr, userVO, SessionKeyUtil.THIRTY_MINUTE);
                clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
            }
        }catch(Exception e){
            logger.error(e.getMessage());
        }

        return clientResult;
    }

    /**
     * 检查是否已登陆
     * @param request
     * @return
     */
    @RequestMapping(value = "/checkUser", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ClientResult checkLogined(HttpServletRequest request){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        if(SessionUtil.getSessionId(request) != null && redisClientBiz.getObject(SessionUtil.getSessionId(request), UserVO.class) != null){
            clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
        }
        return clientResult;
    }

    /**
     * 检查用户名或者手机号或者邮箱是否已存在
     * @param userName
     * @return
     */
    @RequestMapping(value="/checkLoginNameOrMobileOrEmailExist", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ClientResult checkLoginNameOrMobileOrEmailExist(@RequestParam(value = "userName", required = false) String userName,
                                                            @RequestParam(value = "mobile", required = false) String mobile,
                                                            @RequestParam(value = "email", required = false) String email){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
        try{
            if(StringUtils.isNotEmpty(userName)) {
                boolean result = userBiz.checkLoginNameExist(URLDecoder.decode(userName, "UTF-8"));
                if (result) {
                    clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
                    clientResult.setErrorMsg("该用户名已存在");
                    return clientResult;
                }
            }
            if(StringUtils.isNotEmpty(mobile)){
                boolean result = userBiz.checkLoginMobileExist(mobile);
                if (result) {
                    clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
                    clientResult.setErrorMsg("该手机号已注册");
                    return clientResult;
                }
            }
            if(StringUtils.isNotEmpty(email)){
                boolean result = userBiz.checkLoginEmailExist(email);
                if (result) {
                    clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
                    clientResult.setErrorMsg("该邮箱已注册");
                    return clientResult;
                }
            }
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return clientResult;
    }


    /**
     * 异步注册
     * @param userVO
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ClientResult register(UserVO userVO,
                                   HttpServletRequest request, HttpServletResponse response){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            boolean result = userBiz.checkLoginNameExist(userVO.getName());
            if(result){
                clientResult.setErrorMsg("该用户名已存在");
                return clientResult;
            }
            result = userBiz.checkLoginMobileExist(userVO.getMobile());
            if(result){
                clientResult.setErrorMsg("该手机号已注册");
                return clientResult;
            }
            result = userBiz.checkLoginEmailExist(userVO.getEmail());
            if(result){
                clientResult.setErrorMsg("该邮箱已注册");
                return clientResult;
            }

            int id = userBiz.save(userVO);
            if (id > 0) {
                userVO.setId(id);
                String sessionIdStr = SessionUtil.createSession(userVO, request, response);
                redisClientBiz.set(sessionIdStr, userVO, SessionKeyUtil.THIRTY_MINUTE);
                clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return clientResult;
    }


    @RequestMapping(value = "/testInsertUser")
    public @ResponseBody boolean testInsertUser(){
        try{
            for(int i = 0;i < 10000; i++){
                UserVO userVO = new UserVO();
                userVO.setStatus(DataStatusEnum.NORMAL.getNum());
                Random random = new Random();
                int num = random.nextInt(10000);
                userVO.setName("jiangbo" + i + num);
                userVO.setPassword("jiangbo123");
                String phone = generatePhone();
                userVO.setMobile(phone);
                userVO.setEmail(phone+"@qq.com");
                userVO.setRole(UserRoleEnum.CUSTOMER.getNum());
                userVO.setQuestion("1+1=?");
                userVO.setAnswer("2");

                userBiz.save(userVO);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    private String generatePhone(){
        StringBuffer sbStr = new StringBuffer("15021");
        for(int i = 0;i < 6; i++){
            Random random = new Random();
            int num = random.nextInt(9);
            sbStr.append(num);
        }

        return sbStr.toString();
    }
}
