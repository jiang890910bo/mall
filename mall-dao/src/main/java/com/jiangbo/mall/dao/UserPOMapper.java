package com.jiangbo.mall.dao;

import com.jiangbo.mall.pojo.UserPO;
import org.apache.ibatis.annotations.Param;

public interface UserPOMapper  extends AbstractPOMapper<UserPO>{

    /**
     * 根据用户名和密码验证用户
     * @param loginName
     * @param password
     * @return
     */
    UserPO selectByLoginNameAndPassword(@Param(value="loginName")String loginName, @Param(value="password")String password);

    /**
     * 根据用户名查询
     * @param loginName
     * @return
     */
    int selectByName(@Param(value="loginName")String loginName);

    /**
     * 根据手机号查询
     * @param mobile
     * @return
     */
    int selectByMobile(String mobile);

    /**
     * 根据邮箱查询
     * @param email
     * @return
     */
    int selectByEmail(String email);
}