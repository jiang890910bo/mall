package com.jiangbo.mall.biz;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.jiangbo.mall.bo.UserBO;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.enums.UserRoleEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.service.UserService;
import com.jiangbo.mall.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.relation.RoleStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 用户业务
 * @author Jiangbo Cheng on 2017/10/21.
 */
@Component
public class UserBiz {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;

    /**
     * 查询所有用户
     * @return
     */
    public List<UserVO> getUsersByCondition(UserVO userVO) throws MallException {
        if(userVO == null){
            userVO = new UserVO();
        }
        List<UserVO> userVOList = Collections.EMPTY_LIST;
        try{

            UserBO userBO = new UserBO();
            BeanUtils.copyProperties(userVO, userBO);
            List<UserBO> userBOList = userService.queryListByCondition(userBO);
            userVOList = convert(userBOList);
            return userVOList;
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }

    }

    /**
     * 检查用户名称是否已存在
     * @param loginName
     * @return
     */
    public boolean checkLoginNameExist(String loginName) throws MallException {
        try{
            return userService.selectByName(loginName);
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }
    }

    /**
     * 检查手机号是否已存在
     * @param mobile
     * @return
     */
    public boolean checkLoginMobileExist(String mobile) throws MallException {
        try{
            return userService.selectByMobile(mobile);
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }
    }

    /**
     * 检查邮箱是否已存在
     * @param email
     * @return
     */
    public boolean checkLoginEmailExist(String email) throws MallException {
        try{
            return userService.selectByEmail(email);
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }
    }

    private List<UserVO> convert(List<UserBO> userBOList){
        List<UserVO> userVOList = null;
        if(CollectionUtils.isNotEmpty(userBOList)){
            userVOList = new ArrayList<>(userBOList.size());
            for(UserBO userBO : userBOList){
                UserVO userVO = new UserVO();
                BeanUtils.copyProperties(userBO, userVO);
                userVOList.add(userVO);
            }
        }

        return userVOList;
    }

    /**
     * 检查登陆用户
     * @param loginName
     * @param password
     * @return
     */
    public UserVO checkLoginUser(String loginName, String password) throws MallException {
        UserVO userVO = null;
        try{
            UserBO userBO = userService.selectByLoginNameAndPassword(loginName, password);
            if(userBO != null){
                userVO = new UserVO();
                BeanUtils.copyProperties(userBO, userVO);
            }
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }
        return userVO;
    }

    /**
     * 新增用户(返回主键id)
     * @param userVO
     * @return
     */
    public int save(UserVO userVO) throws MallException {
       try{
           UserBO userBO = new UserBO();
           BeanUtils.copyProperties(userVO, userBO);
           userBO.setStatus(DataStatusEnum.NORMAL.getNum());
           userBO.setRole(UserRoleEnum.CUSTOMER.getNum());
           return userService.save(userBO);
       }catch(Exception e){
           throw new MallException(e.getMessage());
       }
    }

    /**
     * 删除用户（逻辑删除）
     * @param userId
     * @return
     */
    public boolean updateStatusById(Integer userId, DataStatusEnum dataStatusEnum){
        UserBO userBO = userService.selectById(userId);
        if(userBO != null){
            userBO = new UserBO();
            userBO.setId(userId);
            userBO.setStatus(dataStatusEnum.getNum());
            return userService.updateByCondition(userBO) > 0 ? true :false;//逻辑删除
        }

        return false;
    }

    /**
     * 更具条件更新用户信息
     * @param userVO
     * @return
     */
    public boolean updateByCondition(UserVO userVO){
        UserBO userBO = new UserBO();
        BeanUtils.copyProperties(userVO, userBO);
        return userService.updateByCondition(userBO) > 0 ? true:false;
    }

    /**
     * 根据主键查询对象
     * @param userId
     * @return
     */
    public UserVO getUserById(Integer userId) {
        try{
            UserVO userVO = new UserVO();
            UserBO userBO = userService.selectById(userId);
            if(userBO != null){
                BeanUtils.copyProperties(userBO, userVO);
                return userVO;
            }
        }catch(Exception e){
            logger.error(e.getMessage());
            throw new MallException(e.getMessage());
        }
        return null;
    }
}
