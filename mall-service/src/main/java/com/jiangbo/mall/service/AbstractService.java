package com.jiangbo.mall.service;

import com.github.pagehelper.PageInfo;
import com.jiangbo.mall.bo.CategoryBO;
import com.jiangbo.mall.bo.UserBO;

import java.util.List;

/**
 * @author Jiangbo Cheng on 2017/10/18.
 */
public interface AbstractService<T> {

    /**
     * 根据主键删除对象
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 插入对象（返回主键）
     * @param record
     * @return
     */
    int save(T record);


    /**
     * 根据主键查询对象
     * @param id
     * @return
     */
    T selectById(Integer id);

    /**
     * 根据参数更新对象
     * @param record
     * @return
     */
    int updateByCondition(T record);

    /**
     * 根据参数查询对象集合
     * @param record
     * @return
     */
    List<T> queryListByCondition(T record);


    /**
     * 分页查询
     * @param record
     * @param index
     * @param size
     * @return
     */
    PageInfo<T> queryListByCondition(T record, int index, int size);
}
