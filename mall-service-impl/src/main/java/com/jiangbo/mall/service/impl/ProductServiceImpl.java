package com.jiangbo.mall.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiangbo.mall.bo.CategoryBO;
import com.jiangbo.mall.bo.ProductBO;
import com.jiangbo.mall.dao.ProductPOMapper;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.pojo.CategoryPO;
import com.jiangbo.mall.pojo.ProductPO;
import com.jiangbo.mall.service.ProductService;
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
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductPOMapper productPOMapper;
    /**
     * 根据主键删除对象
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(Integer id) {
        ProductPO productPO = productPOMapper.selectByPrimaryKey(id);
        if(productPO == null) {
            return 0;
        }
        productPO = new ProductPO();
        productPO.setId(id);
        productPO.setStatus(DataStatusEnum.ABANDONED.getNum());
        return productPOMapper.updateByPrimaryKey(productPO);
    }

    /**
     * 插入对象
     *
     * @param record
     * @return
     */
    @Override
    public int save(ProductBO record) {
        if(record == null){
            return 0;
        }
        ProductPO productPO = new ProductPO();
        BeanUtils.copyProperties(record, productPO);
        Date date = new Date();
        productPO.setCreateTime(date);
        productPO.setUpdateTime(date);
        return productPOMapper.insert(productPO);
    }

    /**
     * 根据主键查询对象
     *
     * @param id
     * @return
     */
    @Override
    public ProductBO selectById(Integer id) {
        ProductPO productPO = productPOMapper.selectByPrimaryKey(id);
        if(productPO == null){
            return null;
        }
        ProductBO productBO = new ProductBO();
        BeanUtils.copyProperties(productPO, productBO);
        return productBO;
    }

    /**
     * 根据参数更新对象
     *
     * @param record
     * @return
     */
    @Override
    public int updateByCondition(ProductBO record) {
        if(record == null){
            return 0;
        }
        ProductPO productPO = new ProductPO();
        BeanUtils.copyProperties(record, productPO);
        productPO.setUpdateTime(new Date());
        return productPOMapper.updateByPrimaryKeySelective(productPO);
    }

    /**
     * 根据参数查询对象集合
     *
     * @param record
     * @return
     */
    @Override
    public List<ProductBO> queryListByCondition(ProductBO record) {
        if(record == null){
            record = new ProductBO();
        }
        List<ProductBO> productBOList = null;
        try{
            ProductPO productPO = new ProductPO();
            BeanUtils.copyProperties(record, productPO);
            List<ProductPO> productPOList = productPOMapper.selectListByCondition(productPO);
            productBOList = this.convert(productPOList);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }
        return productBOList;
    }


    /**
     * 分页查询
     *
     * @param record
     * @param index
     * @param size
     * @return
     */
    @Override
    public PageInfo<ProductBO> queryListByCondition(ProductBO record, int index, int size) {
        PageInfo<ProductBO> pageInfo= new PageInfo<>();
        if(record == null){
            record = new ProductBO();
        }

        try{
            Page page = PageHelper.startPage(index, size, true);
            ProductPO productPO = new ProductPO();
            BeanUtils.copyProperties(record, productPO);
            List<ProductPO> productPOList = productPOMapper.selectListByCondition(productPO);
            List<ProductBO> productBOList = convert(productPOList);

            pageInfo.setList(productBOList);
            BeanUtils.copyProperties(page, pageInfo);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }

        return pageInfo;
    }

    private List<ProductBO> convert(List<ProductPO> productPOList) {
        List<ProductBO> productBOList = null;
        if(CollectionUtils.isNotEmpty(productPOList)){
            productBOList = new ArrayList<>(productPOList.size());
            for(ProductPO productPO : productPOList){
                ProductBO productBO = new ProductBO();
                BeanUtils.copyProperties(productPO, productBO);
                productBOList.add(productBO);
            }
        }

        return productBOList;
    }

    /**
     * 减库存
     *
     * @param productId
     * @param num
     * @return
     */
    @Override
    public boolean subtractStock(Integer productId, int num) {
        return productPOMapper.subtractStock(productId, num);
    }
}
