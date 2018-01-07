package com.jiangbo.mall.biz;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.github.pagehelper.PageInfo;
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
        try{
            UserBO userBO = new UserBO();
            BeanUtils.copyProperties(userVO, userBO);
            List<UserBO> userBOList = userService.queryListByCondition(userBO);
            return convert(userBOList);
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }

    }

    /**
     * 查询所有用户
     * @return
     */
    public PageInfo<UserVO> getUsersByCondition(UserVO userVO, int index, int size) throws MallException {
        if(userVO == null){
            userVO = new UserVO();
        }
        try{
            UserBO userBO = new UserBO();
            BeanUtils.copyProperties(userVO, userBO);
            PageInfo<UserBO> pageInfo = userService.queryListByCondition(userBO, index, size);
            return convert(pageInfo);
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }

    }

    /**
     * 检查用户名称是否已存在
     * @param loginName
     * @return
     */
    public boolean checkLoginExist(String loginName) throws MallException {
        try{
            return userService.selectByName(loginName);
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }
    }

    private PageInfo<UserVO> convert(PageInfo<UserBO> userBOPageInfo){
        PageInfo<UserVO> pageInfo = new PageInfo<>();
        List<UserVO> userVOList = null;
        if(CollectionUtils.isNotEmpty(userBOPageInfo.getList())){
            userVOList = new ArrayList<>(userBOPageInfo.getList().size());
            for(UserBO userBO : userBOPageInfo.getList()){
                UserVO userVO = new UserVO();
                BeanUtils.copyProperties(userBO, userVO);
                userVOList.add(userVO);
            }
            BeanUtils.copyProperties(userBOPageInfo, pageInfo);
            pageInfo.setList(userVOList);
        }

        return pageInfo;
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
        UserVO userVO = new UserVO();
        try{
            UserBO userBO = userService.selectByLoginNameAndPassword(loginName, password);
            if(userBO == null) {
                return null;
            }
            BeanUtils.copyProperties(userBO, userVO);
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }
        return userVO;
    }

    /**
     * 新增用户
     * @param userVO
     * @return
     */
    public boolean save(UserVO userVO) throws MallException {
       try{
           UserBO userBO = new UserBO();
           BeanUtils.copyProperties(userVO, userBO);
           return userService.save(userBO) > 0 ? true:false;
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
