package com.jiangbo.mall.util;

import com.jiangbo.mall.bo.UserBO;
import com.jiangbo.mall.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jiangbo Cheng on 2017/11/5.
 */

public class SessionUtil {

    private static Logger logger = LoggerFactory.getLogger(MauthUtil.class);
    /**
     * 半小时
     */
    private static final int COOKIE_MAX_AGE = 30 * 60;

    /**
     * 创建会话
     * @param userVO
     * @param request
     * @param response
     */
    public static String createSession(UserVO userVO, HttpServletRequest request, HttpServletResponse response){
        String user_key_str = System.currentTimeMillis() + ":" + request.getRemoteAddr() + ":" + userVO.getId();
        String sessionKey = MauthUtil.encode(user_key_str);
        Cookie cookie = new Cookie(SessionKeyUtil.COOKIE_NAME, sessionKey);
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_MAX_AGE);

        response.addCookie(cookie);

        return user_key_str;
    }

    /**
     * 获取sessionId
     * @param request
     * @return
     */
    public static String getSessionId(HttpServletRequest request){
        Cookie[] cookies =  request.getCookies();
        try{
            if(cookies != null){
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals(SessionKeyUtil.COOKIE_NAME)){
                        return MauthUtil.decode(cookie.getValue());
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error("获取http session 异常!!!", e);
        }

        return null;
    }

    /**
     * 清除cookie
     * @param request
     * @param response
     * @param domain
     */
    public static void clearCookie(HttpServletRequest request, HttpServletResponse response, String domain){

        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                if(StringUtils.isEmpty(domain)){
                    cookie.setPath("/");
                }else{
                    cookie.setPath(domain);
                }
                response.addCookie(cookie);
            }
        }
    }
}
