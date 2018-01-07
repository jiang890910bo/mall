package com.jiangbo.mall.biz;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.jiangbo.mall.bo.PayInfoBO;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.service.PayInfoService;
import com.jiangbo.mall.vo.PayInfoVO;
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
public class PayInfoBiz {
    @Autowired
    PayInfoService payInfoService;

    /**
     * 根据条件获得集合
     * @param payInfoVO
     * @return
     */
    public List<PayInfoVO> getPayInfosByCondition(PayInfoVO payInfoVO){
        List<PayInfoVO> payInfoVOList;
        try{
            PayInfoBO payInfoBO = new PayInfoBO();
            BeanUtils.copyProperties(payInfoVO, payInfoBO);
            payInfoVOList = this.convert(payInfoService.queryListByCondition(payInfoBO));
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }
        return payInfoVOList;
    }

    /**
     * 集合转换对象
     * @param payInfoBOs
     * @return
     */
    private List<PayInfoVO> convert(List<PayInfoBO> payInfoBOs) {
        List<PayInfoVO> payInfoVOList = null;
        if(CollectionUtils.isNotEmpty(payInfoBOs)){
            payInfoVOList = new ArrayList<>(payInfoBOs.size());
            for(PayInfoBO payInfoBO : payInfoBOs){
                PayInfoVO payInfoVO = new PayInfoVO();
                BeanUtils.copyProperties(payInfoBO, payInfoVO);
                payInfoVOList.add(payInfoVO);
            }
        }
        return payInfoVOList;
    }

    /**
     * 根据Id查询
     * @param payInfoId
     * @return
     */
    public PayInfoVO getById(Integer payInfoId){
        PayInfoVO payInfoVO = new PayInfoVO();
        PayInfoBO payInfoBO = payInfoService.selectById(payInfoId);
        if(payInfoBO != null){
            BeanUtils.copyProperties(payInfoBO, payInfoVO);
            return payInfoVO;
        }
        return null;
    }

    /**
     * 新增
     * @param payInfoVO
     * @return
     */
    public boolean save(PayInfoVO payInfoVO){
        PayInfoBO payInfoBO = new PayInfoBO();
        BeanUtils.copyProperties(payInfoVO, payInfoBO);
        return payInfoService.save(payInfoBO) > 0 ? true : false;
    }

    /**
     * 根据id逻辑删除
     * @param payInfoId
     * @return
     */
    public boolean deleteById(Integer payInfoId){
        PayInfoBO payInfoBO = payInfoService.selectById(payInfoId);
        if(payInfoBO != null){
            payInfoBO = new PayInfoBO();
            payInfoBO.setId(payInfoId);
            payInfoBO.setStatus(DataStatusEnum.ABANDONED.getNum());
            return payInfoService.updateByCondition(payInfoBO) > 0 ? true : false;
        }
        return false;
    }

    /**
     * 根据条件更新
     * @param payInfoVO
     * @return
     */
    public boolean updateByCondition(PayInfoVO payInfoVO){
        PayInfoBO payInfoBO = new PayInfoBO();
        BeanUtils.copyProperties(payInfoVO, payInfoBO);
        return payInfoService.updateByCondition(payInfoBO) > 0 ? true:false;
    }

}
