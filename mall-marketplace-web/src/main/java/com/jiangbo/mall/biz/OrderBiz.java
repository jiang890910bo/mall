package com.jiangbo.mall.biz;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.jiangbo.mall.IdWorker;
import com.jiangbo.mall.RedisKeyConstant;
import com.jiangbo.mall.bo.ImageBO;
import com.jiangbo.mall.bo.OrderBO;
import com.jiangbo.mall.bo.ProductBO;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.enums.ErrorStatusEnum;
import com.jiangbo.mall.enums.OrderStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.service.ImageService;
import com.jiangbo.mall.service.OrderService;
import com.jiangbo.mall.service.ProductService;
import com.jiangbo.mall.vo.ActivityProductRefVO;
import com.jiangbo.mall.vo.OrderItemVO;
import com.jiangbo.mall.vo.OrderVO;
import com.jiangbo.mall.vo.ProductVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Component
public class OrderBiz {
    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;
    @Autowired
    ActivityProductRefBiz activityProductRefBiz;
    @Autowired
    ImageService imageService;
    @Autowired
    OrderItemBiz orderItemBiz;
    @Autowired
    RedisClientBiz redisClientBiz;


    private static final int PRODUCT_NUM = 1;

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
     * 集合转换对象
     * @param OrderBOs
     * @return
     */
    private List<OrderVO> convert(List<OrderBO> OrderBOs) {
        List<OrderVO> OrderVOList = null;
        if(CollectionUtils.isNotEmpty(OrderBOs)){
            OrderVOList = new ArrayList<>(OrderBOs.size());
            for(OrderBO orderBO : OrderBOs){
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(orderBO, orderVO);

                OrderItemVO orderItemVO = new OrderItemVO();
                orderItemVO.setOrderId(orderBO.getId());
                List<OrderItemVO> orderItemVOList = orderItemBiz.getOrderItemsByCondition(orderItemVO);
                orderVO.setOrderItemVOList(orderItemVOList);
                OrderVOList.add(orderVO);
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
     * 保存订单
     * @param activityProductRefId
     * @param shippingId
     * @param userId
     * @param number
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int saveOrder(Integer activityProductRefId, Integer shippingId, Integer userId, int number){
        int orderId = -1;
        try{

            String cacheKey = RedisKeyConstant.PRODUCT_STOCK_PREFIX + activityProductRefId;
            Object objectStock = redisClientBiz.get(cacheKey);
            if(objectStock != null){
                int stock = Integer.parseInt(objectStock.toString());
                if(stock < number){
                    throw new MallException(ErrorStatusEnum.UNDER_STOCK.getNum(), ErrorStatusEnum.UNDER_STOCK.getDesc());
                }

                //减少redis中的库存
                long value = redisClientBiz.incr(cacheKey, -number);
                if(value >= 0){
                    //库存充足
                    logger.info("Purchasing successfully..");
                    //TODO 真正减 扣 库存 等操作 下单等操作  ,这些操作可用通过 MQ 或 其他方式
                    activityProductRefBiz.subtractStock(activityProductRefId, number, DataStatusEnum.NORMAL.getNum());
                }else{
                    //库存不足，需要恢复刚刚减去的库存
                    redisClientBiz.incr(cacheKey, number);
                }
            }else{
                throw new MallException(ErrorStatusEnum.STOCK_SOLD_OUT.getNum(), ErrorStatusEnum.STOCK_SOLD_OUT.getDesc());
            }

            IdWorker idWorker = new IdWorker(0, 0);

            ActivityProductRefVO activityProductRefVO = activityProductRefBiz.getById(activityProductRefId);
            if(activityProductRefVO != null){
                ProductBO productBO = productService.selectById(activityProductRefVO.getProductId());

                OrderBO orderBO = new OrderBO();
                orderBO.setUserId(userId);
                orderBO.setOrderNo(String.valueOf(idWorker.nextId()));
                orderBO.setShippingId(shippingId);
                orderBO.setPayment(new BigDecimal(PRODUCT_NUM).multiply(activityProductRefVO.getSeckillPrice()));
                orderBO.setStatus(DataStatusEnum.NORMAL.getNum());
                orderBO.setOrderStatus(OrderStatusEnum.UNPAID.getNum());
                orderId = orderService.save(orderBO);

                OrderItemVO orderItemVO = new OrderItemVO();
                orderItemVO.setOrderId(orderId);
                orderItemVO.setProductId(activityProductRefVO.getId());
                orderItemVO.setStatus(DataStatusEnum.NORMAL.getNum());
                orderItemVO.setQuantity(PRODUCT_NUM);
                orderItemVO.setCurrentUnitPrice(activityProductRefVO.getSeckillPrice());
                orderItemVO.setTotalPrice(new BigDecimal(orderItemVO.getQuantity()).multiply(orderItemVO.getCurrentUnitPrice()));
                orderItemVO.setProductName(productBO.getName());

                ImageBO imageBO = imageService.selectById(productBO.getImageId());
                orderItemVO.setProductImage(imageBO.getSubImageUrl());
                orderItemVO.setCurrentUnitPrice(activityProductRefVO.getSeckillPrice());
                orderItemVO.setUserId(userId);
                orderItemBiz.save(orderItemVO);
            }
        }catch(Exception e){
            throw e;
        }

        return orderId;
    }

}
