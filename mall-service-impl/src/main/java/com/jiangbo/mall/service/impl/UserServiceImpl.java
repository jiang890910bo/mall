package com.jiangbo.mall.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiangbo.mall.bo.UserBO;
import com.jiangbo.mall.dao.UserPOMapper;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.enums.ErrorStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.pojo.UserPO;
import com.jiangbo.mall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jiangbo Cheng on 2017/10/18.
 */
@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserPOMapper userPOMapper;

    @Override
    public int deleteById(Integer id) {
        try{
            UserPO userPO = userPOMapper.selectByPrimaryKey(id);
            if(userPO == null){
                logger.warn(ErrorStatusEnum.DATA_NOT_FOUND_IN_DB.getDesc());
                return 0;
            }
            userPO = new UserPO();
            userPO.setId(id);
            userPO.setStatus(DataStatusEnum.ABANDONED.getNum());
            return userPOMapper.updateByPrimaryKey(userPO);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }
    }

    @Override
    public int save(UserBO record) {
        try{
            if(record == null){
                return 0;
            }
            UserPO userPO = new UserPO();
            BeanUtils.copyProperties(record, userPO);
            Date date = new Date();
            userPO.setCreateTime(date);
            userPO.setUpdateTime(date);
            return userPOMapper.insert(userPO);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }
    }

    @Override
    public UserBO selectById(Integer id) {
        try{
            UserPO userPO = userPOMapper.selectByPrimaryKey(id);
            if(userPO == null){
                return null;
            }
            UserBO userBO = new UserBO();
            BeanUtils.copyProperties(userPO, userBO);
            return userBO;
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }
    }

    @Override
    public int updateByCondition(UserBO record) {
        try{
            if(record == null){
                return 0;
            }
            UserPO userPO = new UserPO();
            BeanUtils.copyProperties(record, userPO);
            userPO.setUpdateTime(new Date());
            return userPOMapper.updateByPrimaryKeySelective(userPO);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }
    }

    @Override
    public List<UserBO> queryListByCondition(UserBO record) {
        PageInfo<UserBO> pageInfo;
        if(record == null){
            record = new UserBO();
        }

        try{
            UserPO userPO = new UserPO();
            BeanUtils.copyProperties(record, userPO);
            List<UserPO> userPOList = userPOMapper.selectListByCondition(userPO);
            return convert(userPOList);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }
    }

    @Override
    public PageInfo<UserBO> queryListByCondition(UserBO record, int index, int size) {
        PageInfo<UserBO> pageInfo= new PageInfo<>();
        if(record == null){
            record = new UserBO();
        }

        try{
            Page page = PageHelper.startPage(index, size, true);
            UserPO userPO = new UserPO();
            BeanUtils.copyProperties(record, userPO);
            List<UserPO> userPOList = userPOMapper.selectListByCondition(userPO);
            List<UserBO> userBOList = convert(userPOList);

            pageInfo.setList(userBOList);
            BeanUtils.copyProperties(page, pageInfo);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }

        return pageInfo;
    }

    private List<UserBO> convert(List<UserPO> userPOList){
        List<UserBO> userBOList = null;
        if(CollectionUtils.isNotEmpty(userPOList)){
            userBOList = new ArrayList<>(userPOList.size());
            for(UserPO userPO : userPOList){
                UserBO userBO = new UserBO();
                BeanUtils.copyProperties(userPO, userBO);
                userBOList.add(userBO);
            }
        }

        return userBOList;
    }

    /**
     * 根据传入的参数查询用户
     *
     * @param loginName
     * @param password
     * @return
     */
    @Override
    public UserBO selectByLoginNameAndPassword(String loginName, String password) {
        UserBO userBO;
        try{
            UserPO userPO = userPOMapper.selectByLoginNameAndPassword(loginName, password);
            if(userPO == null) {
                return null;
            }
            userBO = new UserBO();
            BeanUtils.copyProperties(userPO, userBO);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }
        return userBO;
    }

    /**
     * 根据传入的参数查询用户
     *
     * @param loginName
     * @return
     */
    @Override
    public boolean selectByName(String loginName) {
        try{
            if( userPOMapper.selectByName(loginName) > 0) {
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }
        return false;
    }

    @Override
    public boolean selectByMobile(String mobile) {
        try{
            if( userPOMapper.selectByMobile(mobile) > 0) {
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }
        return false;
    }

    @Override
    public boolean selectByEmail(String email) {
        try{
            if( userPOMapper.selectByEmail(email) > 0) {
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }
        return false;
    }
}
