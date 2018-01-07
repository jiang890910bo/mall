package com.jiangbo.mall.biz;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.jiangbo.mall.bo.CartBO;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.service.CartService;
import com.jiangbo.mall.vo.CartVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Component
public class CartBiz {
    @Autowired
    CartService cartService;

    /**
     * 根据条件获得集合
     * @param cartVO
     * @return
     */
    public List<CartVO> getCartsByCondition(CartVO cartVO){
        List<CartVO> cartVOList;
        try{
            CartBO cartBO = new CartBO();
            BeanUtils.copyProperties(cartVO, cartBO);
            cartVOList = this.convert(cartService.queryListByCondition(cartBO));
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }
        return cartVOList;
    }

    /**
     * 集合转换对象
     * @param CartBOs
     * @return
     */
    private List<CartVO> convert(List<CartBO> CartBOs) {
        List<CartVO> cartVOList = null;
        if(CollectionUtils.isNotEmpty(CartBOs)){
            cartVOList = new ArrayList<>(CartBOs.size());
            for(CartBO CartBO : CartBOs){
                CartVO CartVO = new CartVO();
                BeanUtils.copyProperties(CartBO, CartVO);
                cartVOList.add(CartVO);
            }
        }
        return cartVOList;
    }

    /**
     * 根据Id查询
     * @param cartId
     * @return
     */
    public CartVO getById(Integer cartId){
        CartVO cartVO = new CartVO();
        CartBO cartBO = cartService.selectById(cartId);
        if(cartBO != null){
            BeanUtils.copyProperties(cartBO, cartVO);
            return cartVO;
        }
        return null;
    }

    /**
     * 新增
     * @param cartVO
     * @return
     */
    public boolean save(CartVO cartVO){
        CartBO cartBO = new CartBO();
        BeanUtils.copyProperties(cartVO, cartBO);
        return cartService.save(cartBO) > 0 ? true : false;
    }

    /**
     * 根据id逻辑删除
     * @param cartId
     * @return
     */
    public boolean deleteById(Integer cartId){
        CartBO cartBO = cartService.selectById(cartId);
        if(cartBO != null){
            cartBO = new CartBO();
            cartBO.setId(cartId);
            cartBO.setStatus(DataStatusEnum.ABANDONED.getNum());
            return cartService.updateByCondition(cartBO) > 0 ? true : false;
        }
        return false;
    }

    /**
     * 根据条件更新
     * @param cartVO
     * @return
     */
    public boolean updateByCondition(CartVO cartVO){
        CartBO cartBO = new CartBO();
        BeanUtils.copyProperties(cartVO, cartBO);
        return cartService.updateByCondition(cartBO) > 0 ? true:false;
    }

}
