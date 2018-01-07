package com.jiangbo.mall.biz;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.jiangbo.mall.bo.ShippingBO;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.service.ShippingService;
import com.jiangbo.mall.vo.ShippingVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 支付详情
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Component
public class ShippingBiz {
    @Autowired
    ShippingService shippingService;

    /**
     * 根据条件获得集合
     * @param shippingVO
     * @return
     */
    public List<ShippingVO> getShippingsByCondition(ShippingVO shippingVO){
        List<ShippingVO> shippingVOList;
        try{
            ShippingBO shippingBO = new ShippingBO();
            BeanUtils.copyProperties(shippingVO, shippingBO);
            shippingVOList = this.convert(shippingService.queryListByCondition(shippingBO));
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }
        return shippingVOList;
    }

    /**
     * 集合转换对象
     * @param shippingBOs
     * @return
     */
    private List<ShippingVO> convert(List<ShippingBO> shippingBOs) {
        List<ShippingVO> shippingVOList = null;
        if(CollectionUtils.isNotEmpty(shippingBOs)){
            shippingVOList = new ArrayList<>(shippingBOs.size());
            for(ShippingBO shippingBO : shippingBOs){
                ShippingVO shippingVO = new ShippingVO();
                BeanUtils.copyProperties(shippingBO, shippingVO);
                shippingVOList.add(shippingVO);
            }
        }
        return shippingVOList;
    }

    /**
     * 根据Id查询
     * @param shippingId
     * @return
     */
    public ShippingVO getById(Integer shippingId){
        ShippingVO shippingVO = new ShippingVO();
        ShippingBO shippingBO = shippingService.selectById(shippingId);
        if(shippingBO != null){
            BeanUtils.copyProperties(shippingBO, shippingVO);
            return shippingVO;
        }
        return null;
    }

    /**
     * 新增
     * @param shippingVO
     * @return
     */
    public boolean save(ShippingVO shippingVO){
        ShippingBO shippingBO = new ShippingBO();
        BeanUtils.copyProperties(shippingVO, shippingBO);
        return shippingService.save(shippingBO) > 0 ? true : false;
    }

    /**
     * 根据id逻辑删除
     * @param shippingId
     * @return
     */
    public boolean deleteById(Integer shippingId){
        ShippingBO shippingBO = shippingService.selectById(shippingId);
        if(shippingBO != null){
            shippingBO = new ShippingBO();
            shippingBO.setId(shippingId);
            shippingBO.setStatus(DataStatusEnum.ABANDONED.getNum());
            return shippingService.updateByCondition(shippingBO) > 0 ? true : false;
        }
        return false;
    }

    /**
     * 根据条件更新
     * @param shippingVO
     * @return
     */
    public boolean updateByCondition(ShippingVO shippingVO){
        ShippingBO shippingBO = new ShippingBO();
        BeanUtils.copyProperties(shippingVO, shippingBO);
        return shippingService.updateByCondition(shippingBO) > 0 ? true:false;
    }

}
