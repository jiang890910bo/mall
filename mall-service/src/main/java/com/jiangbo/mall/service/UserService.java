package com.jiangbo.mall.service;

import com.github.pagehelper.PageInfo;
import com.jiangbo.mall.bo.UserBO;

/**
 * @author Jiangbo Cheng on 2017/10/18.
 */
public interface UserService extends AbstractService<UserBO> {

    /**
     * 根据传入的参数查询用户
     * @param loginName
     * @param password
     * @return
     */
    UserBO selectByLoginNameAndPassword(String loginName, String password);
    /**
     * 根据传入的名称查询用户
     *
     * @param loginName
     * @return
     */
    boolean selectByName(String loginName);

    /**
     * 根据传入的手机号查询用户
     * @param mobile
     * @return
     */
    boolean selectByMobile(String mobile);

    /**
     * 根据传入的邮箱查询用户
     * @param email
     * @return
     */
    boolean selectByEmail(String email);

    /**
     * 根据参数查询对象集合
     * @param record
     * @return
     */
    PageInfo<UserBO> queryListByCondition(UserBO record, int index, int size);
}
