package com.jiangbo.mall.biz;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.jiangbo.mall.bo.OrderItemBO;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.service.OrderItemService;
import com.jiangbo.mall.vo.OrderItemVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单详情表
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Component
public class OrderItemBiz {
    @Autowired
    OrderItemService orderItemService;
    @Value("${image.url.prefix}")
    String imageWebUrlPrefix;

    /**
     * 根据条件获得集合
     * @param orderItemVO
     * @return
     */
    public List<OrderItemVO> getOrderItemsByCondition(OrderItemVO orderItemVO){
        List<OrderItemVO> orderItemVOList;
        try{
            OrderItemBO orderItemBO = new OrderItemBO();
            BeanUtils.copyProperties(orderItemVO, orderItemBO);
            orderItemVOList = this.convert(orderItemService.queryListByCondition(orderItemBO));
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }
        return orderItemVOList;
    }

    /**
     * 集合转换对象
     * @param orderItemBOs
     * @return
     */
    private List<OrderItemVO> convert(List<OrderItemBO> orderItemBOs) {
        List<OrderItemVO> orderItemVOList = null;
        if(CollectionUtils.isNotEmpty(orderItemBOs)){
            orderItemVOList = new ArrayList<>(orderItemBOs.size());
            for(OrderItemBO orderItemBO : orderItemBOs){
                OrderItemVO orderItemVO = new OrderItemVO();
                BeanUtils.copyProperties(orderItemBO,orderItemVO);
                orderItemVOList.add(orderItemVO);
                orderItemVO.setProductImage(imageWebUrlPrefix + orderItemVO.getProductImage());
            }
        }
        return orderItemVOList;
    }

    /**
     * 根据Id查询
     * @param orderItemId
     * @return
     */
    public OrderItemVO getById(Integer orderItemId){
        OrderItemVO orderItemVO = new OrderItemVO();
        OrderItemBO orderItemBO = orderItemService.selectById(orderItemId);
        if(orderItemBO != null){
            BeanUtils.copyProperties(orderItemBO, orderItemVO);
            return orderItemVO;
        }
        return null;
    }

    /**
     * 新增
     * @param orderItemVO
     * @return
     */
    public boolean save(OrderItemVO orderItemVO){
        OrderItemBO orderItemBO = new OrderItemBO();
        BeanUtils.copyProperties(orderItemVO, orderItemBO);
        return orderItemService.save(orderItemBO) > 0 ? true : false;
    }

    /**
     * 根据id逻辑删除
     * @param orderItemId
     * @return
     */
    public boolean deleteById(Integer orderItemId){
        OrderItemBO orderItemBO = orderItemService.selectById(orderItemId);
        if(orderItemBO != null){
            orderItemBO = new OrderItemBO();
            orderItemBO.setId(orderItemId);
            orderItemBO.setStatus(DataStatusEnum.ABANDONED.getNum());
            return orderItemService.updateByCondition(orderItemBO) > 0 ? true : false;
        }
        return false;
    }

    /**
     * 根据条件更新
     * @param orderItemVO
     * @return
     */
    public boolean updateByCondition(OrderItemVO orderItemVO){
        OrderItemBO orderItemBO = new OrderItemBO();
        BeanUtils.copyProperties(orderItemVO, orderItemBO);
        return orderItemService.updateByCondition(orderItemBO) > 0 ? true:false;
    }

}
