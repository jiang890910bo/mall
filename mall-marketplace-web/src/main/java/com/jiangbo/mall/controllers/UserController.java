package com.jiangbo.mall.controllers;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.jiangbo.mall.ClientResult;
import com.jiangbo.mall.biz.ProductBiz;
import com.jiangbo.mall.biz.UserBiz;
import com.jiangbo.mall.enums.ClientResultStatusEnum;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
    ProductBiz productBiz;

    /**
     * 用户列表
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/userList")
    public String userList(ModelMap modelMap){
        modelMap.addAttribute("userList", userBiz.getUsersByCondition(new UserVO()));
        modelMap.addAttribute("roleMap", userRoleMap);
        modelMap.addAttribute("dataStatusMap", dataStatusMap);
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
        modelMap.addAttribute("roleMap", userRoleMap);
        modelMap.addAttribute("dataStatusMap", dataStatusMap);
        return "user/userDetail";
    }

    /**
     * 跳转到添加用户界面
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/toAddUser")
    public String toAddUser(ModelMap modelMap){
        modelMap.addAttribute("roleMap", userRoleMap);
        return "user/addUser";
    }

    /**
     * 跳转到编辑用户
     * @param userId
     * @return
     */
    @RequestMapping(value="/toEditUser/{userId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String toEditUser(@PathVariable("userId") Integer userId, ModelMap modelMap) {
        try {
            modelMap.addAttribute("user", userBiz.getUserById(userId));
            modelMap.addAttribute("roleMap", userRoleMap);
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
