package com.jiangbo.mall.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiangbo.mall.bo.OrderBO;
import com.jiangbo.mall.bo.ShippingBO;
import com.jiangbo.mall.dao.ShippingPOMapper;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.pojo.OrderPO;
import com.jiangbo.mall.pojo.ShippingPO;
import com.jiangbo.mall.service.ShippingService;
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
public class ShippingServiceImpl implements ShippingService {
    @Autowired
    private ShippingPOMapper shippingPOMapper;
    /**
     * 根据主键删除对象
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(Integer id) {
        ShippingPO shippingPO = shippingPOMapper.selectByPrimaryKey(id);
        if(shippingPO == null) {
            return 0;
        }
        shippingPO = new ShippingPO();
        shippingPO.setId(id);
        shippingPO.setStatus(DataStatusEnum.ABANDONED.getNum());
        return shippingPOMapper.updateByPrimaryKey(shippingPO);
    }

    /**
     * 插入对象
     *
     * @param record
     * @return
     */
    @Override
    public int save(ShippingBO record) {
        if(record == null){
            return 0;
        }
        ShippingPO shippingPO = new ShippingPO();
        BeanUtils.copyProperties(record, shippingPO);
        shippingPO.setStatus(DataStatusEnum.NORMAL.getNum());
        Date date = new Date(System.currentTimeMillis());
        shippingPO.setUpdateTime(date);
        shippingPO.setCreateTime(date);
        return shippingPOMapper.insert(shippingPO);
    }

    /**
     * 根据主键查询对象
     *
     * @param id
     * @return
     */
    @Override
    public ShippingBO selectById(Integer id) {
        ShippingPO shippingPO = shippingPOMapper.selectByPrimaryKey(id);
        if(shippingPO == null){
            return null;
        }
        ShippingBO shippingBO = new ShippingBO();
        BeanUtils.copyProperties(shippingPO, shippingBO);
        return shippingBO;
    }

    /**
     * 根据参数更新对象
     *
     * @param record
     * @return
     */
    @Override
    public int updateByCondition(ShippingBO record) {
        if(record == null){
            return 0;
        }
        ShippingPO shippingPO = new ShippingPO();
        BeanUtils.copyProperties(record, shippingPO);
        return shippingPOMapper.updateByPrimaryKey(shippingPO);
    }

    /**
     * 根据参数查询对象集合
     *
     * @param record
     * @return
     */
    @Override
    public List<ShippingBO> queryListByCondition(ShippingBO record) {
        if(record == null){
            record = new ShippingBO();
        }
        List<ShippingBO> shippingBOList = null;
        try{
            ShippingPO shippingPO = new ShippingPO();
            BeanUtils.copyProperties(record, shippingPO);
            List<ShippingPO> shippingPOList = shippingPOMapper.selectListByCondition(shippingPO);
            shippingBOList = this.convert(shippingPOList);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }
        return shippingBOList;
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
    public PageInfo<ShippingBO> queryListByCondition(ShippingBO record, int index, int size) {
        PageInfo<ShippingBO> pageInfo= new PageInfo<>();
        if(record == null){
            record = new ShippingBO();
        }

        try{
            Page page = PageHelper.startPage(index, size, true);
            ShippingPO shippingPO = new ShippingPO();
            BeanUtils.copyProperties(record, shippingPO);
            List<ShippingPO> shippingPOList = shippingPOMapper.selectListByCondition(shippingPO);
            List<ShippingBO> shippingBOList = convert(shippingPOList);

            pageInfo.setList(shippingBOList);
            BeanUtils.copyProperties(page, pageInfo);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }

        return pageInfo;
    }

    private List<ShippingBO> convert(List<ShippingPO> shippingPOList) {
        List<ShippingBO> shippingBOList = null;
        if(CollectionUtils.isNotEmpty(shippingPOList)){
            shippingBOList = new ArrayList<>(shippingPOList.size());
            for(ShippingPO shippingPO : shippingPOList){
                ShippingBO shippingBO = new ShippingBO();
                BeanUtils.copyProperties(shippingPO, shippingBO);
                shippingBOList.add(shippingBO);
            }
        }
        return shippingBOList;
    }
}
