package com.jiangbo.mall.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiangbo.mall.bo.CartBO;
import com.jiangbo.mall.bo.CategoryBO;
import com.jiangbo.mall.dao.CartPOMapper;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.pojo.CartPO;
import com.jiangbo.mall.pojo.CategoryPO;
import com.jiangbo.mall.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiangbo Cheng on 2017/10/18.
 */
@Service
public class CartServiceImpl implements CartService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CartPOMapper cartPOMapper;

    /**
     * 根据主键删除对象
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(Integer id) {
        CartPO cartPO = cartPOMapper.selectByPrimaryKey(id);
        if(cartPO == null){
            return 0;
        }
        cartPO = new CartPO();
        cartPO.setId(id);
        cartPO.setStatus(DataStatusEnum.ABANDONED.getNum());
        return cartPOMapper.updateByPrimaryKey(cartPO);
    }

    /**
     * 插入对象
     *
     * @param record
     * @return
     */
    @Override
    public int save(CartBO record) {
        if(record == null){
            return 0;
        }
        CartPO cartPO = new CartPO();
        BeanUtils.copyProperties(cartPO, record);
        return cartPOMapper.insert(cartPO);
    }

    /**
     * 根据主键查询对象
     *
     * @param id
     * @return
     */
    @Override
    public CartBO selectById(Integer id) {
        CartPO cartPO = cartPOMapper.selectByPrimaryKey(id);
        if(cartPO == null){
            return null;
        }
        CartBO cartBO = new CartBO();
        BeanUtils.copyProperties(cartPO, cartBO);
        return cartBO;
    }

    /**
     * 根据参数更新对象
     *
     * @param record
     * @return
     */
    @Override
    public int updateByCondition(CartBO record) {
        if(record == null){
            return 0;
        }
        CartPO cartPO = new CartPO();
        BeanUtils.copyProperties(record, cartPO);
        return cartPOMapper.updateByPrimaryKey(cartPO);
    }

    /**
     * 根据参数查询对象集合
     *
     * @param record
     * @return
     */
    @Override
    public List<CartBO> queryListByCondition(CartBO record) {
        if(record == null){
            record = new CartBO();
        }
        List<CartBO> cartBOList = null;
        try{
            CartPO cartPO = new CartPO();
            BeanUtils.copyProperties(record, cartPO);
            List<CartPO> cartPOList = cartPOMapper.selectListByCondition(cartPO);
            cartBOList = this.convert(cartPOList);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }

        return cartBOList;
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
    public PageInfo<CartBO> queryListByCondition(CartBO record, int index, int size) {
        PageInfo<CartBO> pageInfo= new PageInfo<>();
        if(record == null){
            record = new CartBO();
        }

        try{
            Page page = PageHelper.startPage(index, size, true);
            CartPO cartPO = new CartPO();
            BeanUtils.copyProperties(record, cartPO);
            List<CartPO> cartPOList = cartPOMapper.selectListByCondition(cartPO);
            List<CartBO> cartBOList = convert(cartPOList);

            pageInfo.setList(cartBOList);
            BeanUtils.copyProperties(page, pageInfo);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }

        return pageInfo;
    }

    private List<CartBO> convert(List<CartPO> cartPOList) {
        List<CartBO> cartBOList = null;
        if(CollectionUtils.isNotEmpty(cartPOList)){
            cartBOList = new ArrayList<>(cartPOList.size());
            for(CartPO cartPO : cartPOList){
                CartBO cartBO = new CartBO();
                BeanUtils.copyProperties(cartPO, cartBO);
                cartBOList.add(cartBO);
            }
        }
        return cartBOList;
    }
}
