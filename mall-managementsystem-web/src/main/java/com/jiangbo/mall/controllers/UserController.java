package com.jiangbo.mall.controllers;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import com.jiangbo.mall.ClientResult;
import com.jiangbo.mall.biz.RedisClientBiz;
import com.jiangbo.mall.biz.UserBiz;
import com.jiangbo.mall.enums.ClientResultStatusEnum;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.enums.UserRoleEnum;
import com.jiangbo.mall.util.SessionUtil;
import com.jiangbo.mall.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;

/**
 * 用户
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserBiz userBiz;
    @Autowired
    RedisClientBiz redisClientBiz;

    /**
     * 用户列表
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/userList")
    public String userList(@RequestParam(value = "pageIndex", required = false, defaultValue = "1")Integer pageIndex,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10")Integer pageSize,
                            HttpServletRequest request,ModelMap modelMap){
        PageInfo<UserVO> page = userBiz.getUsersByCondition(new UserVO(), pageIndex, pageSize);
        modelMap.addAttribute("pageInfo", page);
        modelMap.addAttribute("userRoleMap", userAllRoleMap);
        modelMap.addAttribute("dataStatusMap", dataStatusMap);
        UserVO userVO = redisClientBiz.get(SessionUtil.getSessionId(request), UserVO.class);
        modelMap.addAttribute("user", userVO);
        return "user/userList";
    }

    /**
     * 用户详情
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/userDetail/{userId}")
    public String userDetail(ModelMap modelMap, @PathVariable("userId") Integer userId){
        modelMap.addAttribute("user", userBiz.getUserById(userId));
        modelMap.addAttribute("roleMap", userAllRoleMap);
        modelMap.addAttribute("dataStatusMap", dataStatusMap);
        return "user/userDetail";
    }

    /**
     * 跳转到添加用户界面
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/toAddUser")
    public String toAddUser(ModelMap modelMap, HttpServletRequest request){
        UserVO userVO = redisClientBiz.get(SessionUtil.getSessionId(request), UserVO.class);
        if(userVO.getRole().equals(UserRoleEnum.ADMIN.getNum())) {
            modelMap.addAttribute("roleMap", userCustomerRoleMap);
        }else{
            modelMap.addAttribute("roleMap", getUserRoleMap(userVO.getRole()));
        }
        return "user/addUser";
    }

    /**
     * 检查用户名是否已存在
     * @param userName
     * @return
     */
    @RequestMapping(value="/checkLoginNameExist", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ClientResult checkLoginNameExist(@RequestParam(value = "userName", required = true)String userName){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            boolean result = userBiz.checkLoginExist(URLDecoder.decode(userName, "UTF-8"));
            if(result){
                clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
                clientResult.setResult(result);
                return clientResult;
            }
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return clientResult;
    }

    /**
     * 添加用户
     * @param userVO
     * @return
     */
    @RequestMapping(value="/addUser", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ClientResult addUser(UserVO userVO){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
       try{
           boolean result = userBiz.checkLoginExist(userVO.getName());
           if(result){
               clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
               clientResult.setResult(result);
               return clientResult;
           }
           //新建用户是启用的数据
           userVO.setStatus(DataStatusEnum.NORMAL.getNum());
           result = userBiz.save(userVO);
           if(result){
               clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
               clientResult.setResult(result);
               return clientResult;
           }
       }catch(Exception e){
           logger.error(e.getMessage());
       }
        return clientResult;
    }

    /**
     * 跳转到编辑用户
     * @param userId
     * @return
     */
    @RequestMapping(value="/toEditUser/{userId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String toEditUser(@PathVariable("userId") Integer userId, ModelMap modelMap, HttpServletRequest request) {
        try {
            UserVO userVO = redisClientBiz.get(SessionUtil.getSessionId(request), UserVO.class);
            modelMap.addAttribute("roleMap", getUserRoleMap(userVO.getRole()));
            modelMap.addAttribute("user", userBiz.getUserById(userId));

            modelMap.addAttribute("dataStatusMap", dataStatusMap);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "user/editUser";
    }
    /**
     * 编辑用户
     * @param userVO
     * @return
     */
    @RequestMapping(value="/editUser", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ClientResult editUser(UserVO userVO){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            boolean result = userBiz.updateByCondition(userVO);
            if(result){
                clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
                clientResult.setResult(result);
                return clientResult;
            }
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return clientResult;
    }

    /**
     * 禁用用户
     * @param userId
     * @return
     */
    @RequestMapping(value="/abandonUser/{userId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String abandonUser(@PathVariable("userId") Integer userId){
        try{
            userBiz.updateStatusById(userId, DataStatusEnum.ABANDONED);
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return "redirect:/user/userList";
    }

    /**
     * 启用用户
     * @param userId
     * @return
     */
    @RequestMapping(value="/enableUser/{userId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String enableUser(@PathVariable("userId") Integer userId){
        try{
            userBiz.updateStatusById(userId, DataStatusEnum.NORMAL);
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return "redirect:/user/userList";
    }

    /**
     * 批量删除用户
     * @param userIds
     * @return
     */
    @RequestMapping(value = "/batchDeleteUsers", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ClientResult batchDeleteUsers(@RequestParam String[] userIds){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        StringBuffer error_sbStr = new StringBuffer();
        try{
            for(int i=0; userIds != null && i < userIds.length; i++){
                String userId = userIds[i];
                if(StringUtils.isNotEmpty(userId) && !userBiz.updateStatusById(Integer.parseInt(userId), DataStatusEnum.DELETED)){
                    error_sbStr.append(i);
                }
            }
            if(error_sbStr.length() > 0){
                clientResult.setErrorMsg("部分删除失败，删除的失败用户有(" + error_sbStr +")");
            }else{
                clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
            }
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return clientResult;
    }
}
