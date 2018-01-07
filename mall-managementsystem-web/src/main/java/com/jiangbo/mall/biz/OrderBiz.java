package com.jiangbo.mall.biz;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiangbo.mall.bo.ImageBO;
import com.jiangbo.mall.bo.OrderBO;
import com.jiangbo.mall.bo.OrderStatisticBO;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.enums.OrderStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.service.OrderService;
import com.jiangbo.mall.vo.ImageVO;
import com.jiangbo.mall.vo.OrderStatisticVO;
import com.jiangbo.mall.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Component
public class OrderBiz {
    @Autowired
    OrderService orderService;

    /**
     * 根据条件获得集合
     * @param orderVO
     * @return
     */
    public List<OrderVO> getOrdersByCondition(OrderVO orderVO){
        List<OrderVO> orderVOList;
        try{
            OrderBO orderBO = new OrderBO();
            BeanUtils.copyProperties(orderVO, orderBO);
            orderVOList = this.convert(orderService.queryListByCondition(orderBO));
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }
        return orderVOList;
    }

    /**
     * 根据条件分页获得集合
     * @param orderVO
     * @return
     */
    public PageInfo<OrderVO> getOrdersByCondition(OrderVO orderVO, int pageIndex, int pageSize){
        try{

            OrderBO orderBO = new OrderBO();
            BeanUtils.copyProperties(orderVO, orderBO);
            PageInfo<OrderBO> orderBOPageInfo = orderService.queryListByCondition(orderBO, pageIndex, pageSize);
            return this.convert(orderBOPageInfo);
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }
    }

    private PageInfo<OrderVO> convert(PageInfo<OrderBO> orderBOPageInfo){
        PageInfo<OrderVO> pageInfo = new PageInfo<>();
        List<OrderVO> orderVOList;
        if(CollectionUtils.isNotEmpty(orderBOPageInfo.getList())){
            orderVOList = new ArrayList<>(orderBOPageInfo.getList().size());
            for(OrderBO orderBO : orderBOPageInfo.getList()){
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(orderBO, orderVO);
                orderVOList.add(orderVO);
            }
            BeanUtils.copyProperties(orderBOPageInfo, pageInfo);
            pageInfo.setList(orderVOList);
        }

        return pageInfo;
    }

    /**
     * 集合转换对象
     * @param OrderBOs
     * @return
     */
    private List<OrderVO> convert(List<OrderBO> OrderBOs) {
        List<OrderVO> OrderVOList = null;
        if(CollectionUtils.isNotEmpty(OrderBOs)){
            OrderVOList = new ArrayList<>(OrderBOs.size());
            for(OrderBO OrderBO : OrderBOs){
                OrderVO OrderVO = new OrderVO();
                BeanUtils.copyProperties(OrderBO, OrderVO);
                OrderVOList.add(OrderVO);
            }
        }
        return OrderVOList;
    }

    /**
     * 根据Id查询
     * @param orderId
     * @return
     */
    public OrderVO getById(Integer orderId){
        OrderVO orderVO = new OrderVO();
        OrderBO orderBO = orderService.selectById(orderId);
        if(orderBO != null){
            BeanUtils.copyProperties(orderBO, orderVO);
            return orderVO;
        }
        return null;
    }

    /**
     * 新增
     * @param orderVO
     * @return
     */
    public boolean save(OrderVO orderVO){
        OrderBO orderBO = new OrderBO();
        BeanUtils.copyProperties(orderVO, orderBO);
        return orderService.save(orderBO) > 0 ? true : false;
    }

    /**
     * 根据id逻辑删除
     * @param orderId
     * @return
     */
    public boolean deleteById(Integer orderId){
        OrderBO orderBO = orderService.selectById(orderId);
        if(orderBO != null){
            orderBO = new OrderBO();
            orderBO.setId(orderId);
            orderBO.setStatus(DataStatusEnum.ABANDONED.getNum());
            return orderService.updateByCondition(orderBO) > 0 ? true : false;
        }
        return false;
    }

    /**
     * 根据条件更新
     * @param orderVO
     * @return
     */
    public boolean updateByCondition(OrderVO orderVO){
        OrderBO orderBO = new OrderBO();
        BeanUtils.copyProperties(orderVO, orderBO);
        return orderService.updateByCondition(orderBO) > 0 ? true:false;
    }

    /**
     * 根据格式统计订单
     * @param formatString
     * @return
     */
    public String statisticOrder(String formatString){
        StringBuilder result = new StringBuilder("[");

        List<OrderStatisticBO> orderStatisticBOList = orderService.statisticOrder(formatString);
        if(CollectionUtils.isNotEmpty(orderStatisticBOList)){
            for(OrderStatisticBO bo : orderStatisticBOList){
                result.append("[\"").append(bo.getTimeStr()).append("\",").append(bo.getCount()).append("],");
            }
        }
        result.deleteCharAt(result.lastIndexOf(","));
        result.append("]");

        return result.toString();
    }


    /**
     * 手动付款
     * @param orderId
     * @return
     */
    public int handPay(Integer orderId) {
        OrderBO orderBO = orderService.selectById(orderId);
        orderBO.setOrderStatus(OrderStatusEnum.PAID.getNum());
        orderBO.setPayment(orderBO.getPayment());
        Date date = new Date();
        orderBO.setPaymentTime(date);
        orderBO.setPostage(0);
        orderBO.setUpdateTime(date);

        return orderService.updateByCondition(orderBO);
    }
}
