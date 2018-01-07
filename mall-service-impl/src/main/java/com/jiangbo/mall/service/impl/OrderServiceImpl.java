package com.jiangbo.mall.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiangbo.mall.bo.ImageBO;
import com.jiangbo.mall.bo.OrderBO;
import com.jiangbo.mall.bo.OrderStatisticBO;
import com.jiangbo.mall.dao.OrderPOMapper;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.pojo.ImagePO;
import com.jiangbo.mall.pojo.OrderPO;
import com.jiangbo.mall.pojo.OrderStatisticPO;
import com.jiangbo.mall.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Jiangbo Cheng on 2017/10/18.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderPOMapper orderPOMapper;

    /**
     * 根据主键删除对象
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(Integer id) {
        OrderPO orderPO = orderPOMapper.selectByPrimaryKey(id);
        if(orderPO == null) {
            return 0;
        }
        orderPO.setId(id);
        orderPO.setStatus(DataStatusEnum.ABANDONED.getNum());
        return orderPOMapper.updateByPrimaryKey(orderPO);
    }

    /**
     * 插入对象(返回新生成的主键值)
     *
     * @param record
     * @return
     */
    @Override
    public int save(OrderBO record) {
        if(record == null){
            return 0;
        }
        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(record, orderPO);
        Date date = new Date();
        orderPO.setCreateTime(date);
        orderPO.setUpdateTime(date);
        if(orderPOMapper.insert(orderPO) > 0){
            return orderPO.getId();
        }
        return 0;
    }

    /**
     * 根据主键查询对象
     *
     * @param id
     * @return
     */
    @Override
    public OrderBO selectById(Integer id) {
        OrderBO orderBO = new OrderBO();
        OrderPO orderPO = orderPOMapper.selectByPrimaryKey(id);
        if(orderPO == null){
            return null;
        }
        BeanUtils.copyProperties(orderPO ,orderBO);
        return orderBO;
    }

    /**
     * 根据参数更新对象
     *
     * @param record
     * @return
     */
    @Override
    public int updateByCondition(OrderBO record) {
        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(record, orderPO);
        return orderPOMapper.updateByPrimaryKey(orderPO);
    }

    /**
     * 根据参数查询对象集合
     *
     * @param record
     * @return
     */
    @Override
    public List<OrderBO> queryListByCondition(OrderBO record) {
        if(record == null){
            record = new OrderBO();
        }
        List<OrderBO> orderBOList = null;

        try{
            OrderPO orderPO = new OrderPO();
            BeanUtils.copyProperties(record, orderPO);
            List<OrderPO> orderPOList = orderPOMapper.selectListByCondition(orderPO);
            orderBOList = this.convert(orderPOList);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }
        return orderBOList;
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
    public PageInfo<OrderBO> queryListByCondition(OrderBO record, int index, int size) {
        PageInfo<OrderBO> pageInfo= new PageInfo<>();
        if(record == null){
            record = new OrderBO();
        }

        try{
            Page page = PageHelper.startPage(index, size, true);
            OrderPO orderPO = new OrderPO();
            BeanUtils.copyProperties(record, orderPO);
            List<OrderPO> orderPOList = orderPOMapper.selectListByCondition(orderPO);
            List<OrderBO> orderBOList = convert(orderPOList);

            pageInfo.setList(orderBOList);
            BeanUtils.copyProperties(page, pageInfo);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }

        return pageInfo;
    }

    private List<OrderBO> convert(List<OrderPO> orderPOList) {
        List<OrderBO> orderBOList = null;
        if(CollectionUtils.isNotEmpty(orderPOList)){
            orderBOList = new ArrayList<>(orderPOList.size());
            for(OrderPO orderPO : orderPOList){
                OrderBO orderBO = new OrderBO();
                BeanUtils.copyProperties(orderPO, orderBO);
                orderBOList.add(orderBO);
            }
        }
        return orderBOList;

    }

    /**
     * 根据格式统计订单数量
     * @param formatString
     * @return
     */
    @Override
    public List<OrderStatisticBO> statisticOrder(String formatString) {
        List<OrderStatisticBO> orderStatisticBOList = new ArrayList<>();

        List<OrderStatisticPO> orderStatisticPOList = orderPOMapper.statisticOrder(formatString);
        for(OrderStatisticPO orderStatisticPO : orderStatisticPOList){
            OrderStatisticBO orderStatisticBO = new OrderStatisticBO();
            BeanUtils.copyProperties(orderStatisticPO, orderStatisticBO);
            orderStatisticBOList.add(orderStatisticBO);
        }

        return orderStatisticBOList;
    }
}
