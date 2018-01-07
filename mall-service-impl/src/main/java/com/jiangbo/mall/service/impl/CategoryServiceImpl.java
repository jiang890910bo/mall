package com.jiangbo.mall.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiangbo.mall.bo.CategoryBO;
import com.jiangbo.mall.bo.UserBO;
import com.jiangbo.mall.dao.CategoryPOMapper;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.pojo.CategoryPO;
import com.jiangbo.mall.pojo.UserPO;
import com.jiangbo.mall.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CategoryPOMapper categoryPOMapper;

    /**
     * 根据主键删除对象
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(Integer id) {
        CategoryPO categoryPO = categoryPOMapper.selectByPrimaryKey(id);
        if(categoryPO == null) {
            return 0;
        }
        categoryPO = new CategoryPO();
        categoryPO.setId(id);
        categoryPO.setStatus(DataStatusEnum.ABANDONED.getNum());
        return categoryPOMapper.updateByPrimaryKey(categoryPO);
    }

    /**
     * 插入对象
     *
     * @param record
     * @return
     */
    @Override
    public int save(CategoryBO record) {
        if(record == null) {
            return 0;
        }
        CategoryPO categoryPO = new CategoryPO();
        BeanUtils.copyProperties(record, categoryPO);

        Date date = new Date();
        categoryPO.setCreateTime(date);
        categoryPO.setUpdateTime(date);
        return categoryPOMapper.insert(categoryPO);
    }

    /**
     * 根据主键查询对象
     *
     * @param id
     * @return
     */
    @Override
    public CategoryBO selectById(Integer id) {
        CategoryPO categoryPO = categoryPOMapper.selectByPrimaryKey(id);
        if(categoryPO == null) {
            return null;
        }
        CategoryBO categoryBO = new CategoryBO();
        BeanUtils.copyProperties(categoryPO, categoryBO);

        return categoryBO;
    }

    /**
     * 根据参数更新对象
     *
     * @param record
     * @return
     */
    @Override
    public int updateByCondition(CategoryBO record) {
        if(record == null) {
            return 0;
        }
        CategoryPO categoryPO = new CategoryPO();
        BeanUtils.copyProperties(record, categoryPO);
        categoryPO.setUpdateTime(new Date());
        return categoryPOMapper.updateByPrimaryKeySelective(categoryPO);
    }

    /**
     * 根据参数查询对象集合
     *
     * @param record
     * @return
     */
    @Override
    public List<CategoryBO> queryListByCondition(CategoryBO record) {
        if(record == null){
            record = new CategoryBO();
        }
        List<CategoryBO> categoryBOList = null;
        try{
            CategoryPO categoryPO = new CategoryPO();
            BeanUtils.copyProperties(record, categoryPO);;
            List<CategoryPO> categoryPOList = categoryPOMapper.selectListByCondition(categoryPO);
            categoryBOList = convert(categoryPOList);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }
        return categoryBOList;
    }


    @Override
    public PageInfo<CategoryBO> queryListByCondition(CategoryBO record, int index, int size) {
        PageInfo<CategoryBO> pageInfo= new PageInfo<>();
        if(record == null){
            record = new CategoryBO();
        }

        try{
            Page page = PageHelper.startPage(index, size, true);
            CategoryPO categoryPO = new CategoryPO();
            BeanUtils.copyProperties(record, categoryPO);
            List<CategoryPO> categoryPOList = categoryPOMapper.selectListByCondition(categoryPO);
            List<CategoryBO> categoryBOList = convert(categoryPOList);

            pageInfo.setList(categoryBOList);
            BeanUtils.copyProperties(page, pageInfo);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }

        return pageInfo;
    }

    private List<CategoryBO> convert(List<CategoryPO> categoryPOList) {
        List<CategoryBO> categoryBOList = null;
        if(CollectionUtils.isNotEmpty(categoryPOList)){
            categoryBOList= new ArrayList<>(categoryPOList.size());
            for(CategoryPO categoryPO : categoryPOList){
                CategoryBO categoryBO = new CategoryBO();
                BeanUtils.copyProperties(categoryPO , categoryBO);
                categoryBOList.add(categoryBO);
            }
        }

        return categoryBOList;
    }


}
