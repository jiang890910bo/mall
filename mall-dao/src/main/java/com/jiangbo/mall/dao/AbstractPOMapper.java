package com.jiangbo.mall.dao;

import com.jiangbo.mall.pojo.PagePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chengjiangbo on 2017-10-18.
 */
public interface AbstractPOMapper<T> {
    /**
     * 根据主键删除对象
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入对象(返回主键)
     * @param record
     * @return
     */
    int insert(T record);

    /**
     * 根据参数插入对象
     * @param record
     * @return
     */
    int insertSelective(T record);

    /**
     * 根据主键查询对象
     * @param id
     * @return
     */
    T selectByPrimaryKey(Integer id);

    /**
     * 根据参数更新对象
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 更新对象
     * @param record
     * @return
     */
    int updateByPrimaryKey(T record);

    /**
     * 更新对象为指定的状态
     * @param id
     * @param status
     * @return
     */
    int updateStatusByPrimaryKey(@Param(value="id") Integer id, @Param(value="status")int status);

    /**
     * 根据条件查询对象集合
     * @param t
     * @return
     */
    List<T> selectListByCondition(T t);

}