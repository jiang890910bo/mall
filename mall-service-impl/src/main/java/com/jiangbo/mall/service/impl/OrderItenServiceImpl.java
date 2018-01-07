package com.jiangbo.mall.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiangbo.mall.bo.OrderItemBO;
import com.jiangbo.mall.bo.PayInfoBO;
import com.jiangbo.mall.dao.OrderItemPOMapper;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.pojo.OrderItemPO;
import com.jiangbo.mall.pojo.PayInfoPO;
import com.jiangbo.mall.service.OrderItemService;
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
public class OrderItenServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemPOMapper orderItemPOMapper;

    /**
     * 根据主键删除对象
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(Integer id) {
        OrderItemPO orderItemPO = orderItemPOMapper.selectByPrimaryKey(id);
        if(orderItemPO == null) {
            return 0;
        }
        orderItemPO = new OrderItemPO();
        orderItemPO.setId(id);
        orderItemPO.setStatus(DataStatusEnum.ABANDONED.getNum());
        return orderItemPOMapper.updateByPrimaryKey(orderItemPO);
    }

    /**
     * 插入对象
     *
     * @param record
     * @return
     */
    @Override
    public int save(OrderItemBO record) {
        if(record == null){
            return 0;
        }
        OrderItemPO orderItemPO = new OrderItemPO();
        BeanUtils.copyProperties(record, orderItemPO);
        Date date = new Date();
        orderItemPO.setCreateTime(date);
        orderItemPO.setUpdateTime(date);
        return orderItemPOMapper.insert(orderItemPO);
    }

    /**
     * 根据主键查询对象
     *
     * @param id
     * @return
     */
    @Override
    public OrderItemBO selectById(Integer id) {
        OrderItemPO orderItemPO = orderItemPOMapper.selectByPrimaryKey(id);
        if(orderItemPO == null){
            return null;
        }
        OrderItemBO orderItemBO = new OrderItemBO();
        BeanUtils.copyProperties(orderItemPO, orderItemBO);
        return orderItemBO;
    }

    /**
     * 根据参数更新对象
     *
     * @param record
     * @return
     */
    @Override
    public int updateByCondition(OrderItemBO record) {
        if(record == null){
            return 0;
        }
        OrderItemPO orderItemPO = new OrderItemPO();
        BeanUtils.copyProperties(record, orderItemPO);
        return orderItemPOMapper.updateByPrimaryKey(orderItemPO);
    }

    /**
     * 根据参数查询对象集合
     *
     * @param record
     * @return
     */
    @Override
    public List<OrderItemBO> queryListByCondition(OrderItemBO record) {
        if(record == null){
            record = new OrderItemBO();
        }
        List<OrderItemBO> orderItemBOList = null;

        try{
            OrderItemPO orderItemPO = new OrderItemPO();
            BeanUtils.copyProperties(record, orderItemPO);
            List<OrderItemPO> orderItemPOList = orderItemPOMapper.selectListByCondition(orderItemPO);
            orderItemBOList = this.convert(orderItemPOList);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }
        return orderItemBOList;
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
    public PageInfo<OrderItemBO> queryListByCondition(OrderItemBO record, int index, int size) {
        PageInfo<OrderItemBO> pageInfo= new PageInfo<>();
        if(record == null){
            record = new OrderItemBO();
        }

        try{
            Page page = PageHelper.startPage(index, size, true);
            OrderItemPO orderItemPO = new OrderItemPO();
            BeanUtils.copyProperties(record, orderItemPO);
            List<OrderItemPO> orderItemPOList = orderItemPOMapper.selectListByCondition(orderItemPO);
            List<OrderItemBO> orderItemBOList = convert(orderItemPOList);

            pageInfo.setList(orderItemBOList);
            BeanUtils.copyProperties(page, pageInfo);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }

        return pageInfo;
    }

    private List<OrderItemBO> convert(List<OrderItemPO> orderItemPOList) {
        List<OrderItemBO> orderItemBOList = null;
        if(CollectionUtils.isNotEmpty(orderItemPOList)){
            orderItemBOList= new ArrayList<>(orderItemPOList.size());
            for(OrderItemPO orderItemPO : orderItemPOList){
                OrderItemBO orderItemBO = new OrderItemBO();
                BeanUtils.copyProperties(orderItemPO , orderItemBO);
                orderItemBOList.add(orderItemBO);
            }
        }

        return orderItemBOList;
    }
}
