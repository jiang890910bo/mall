package com.jiangbo.mall.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiangbo.mall.bo.PayInfoBO;
import com.jiangbo.mall.bo.ShippingBO;
import com.jiangbo.mall.dao.PayInfoPOMapper;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.pojo.PayInfoPO;
import com.jiangbo.mall.pojo.ShippingPO;
import com.jiangbo.mall.service.PayInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiangbo Cheng on 2017/10/18.
 */
@Service
public class PayInfoServiceImpl implements PayInfoService {
    @Autowired
    private PayInfoPOMapper payInfoPOMapper;

    /**
     * 根据主键删除对象
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(Integer id) {
        PayInfoPO payInfoPO = payInfoPOMapper.selectByPrimaryKey(id);
        if(payInfoPO == null) {
            return 0;
        }
        payInfoPO = new PayInfoPO();
        payInfoPO.setId(id);
        payInfoPO.setStatus(DataStatusEnum.ABANDONED.getNum());
        return payInfoPOMapper.updateByPrimaryKey(payInfoPO);
    }

    /**
     * 插入对象
     *
     * @param record
     * @return
     */
    @Override
    public int save(PayInfoBO record) {
        if(record == null){
            return 0;
        }
        PayInfoPO payInfoPO = new PayInfoPO();
        BeanUtils.copyProperties(record, payInfoPO);
        return payInfoPOMapper.insert(payInfoPO);
    }

    /**
     * 根据主键查询对象
     *
     * @param id
     * @return
     */
    @Override
    public PayInfoBO selectById(Integer id) {
        PayInfoPO payInfoPO = payInfoPOMapper.selectByPrimaryKey(id);
        if (payInfoPO == null){
            return null;
        }
        PayInfoBO payInfoBO = new PayInfoBO();
        BeanUtils.copyProperties(payInfoPO, payInfoBO);
        return payInfoBO;
    }

    /**
     * 根据参数更新对象
     *
     * @param record
     * @return
     */
    @Override
    public int updateByCondition(PayInfoBO record) {
        if(record == null){
            return 0;
        }
        PayInfoPO payInfoPO = new PayInfoPO();
        BeanUtils.copyProperties(record, payInfoPO);
        return payInfoPOMapper.updateByPrimaryKey(payInfoPO);
    }

    /**
     * 根据参数查询对象集合
     *
     * @param record
     * @return
     */
    @Override
    public List<PayInfoBO> queryListByCondition(PayInfoBO record) {
        if(record == null){
            record = new PayInfoBO();
        }
        List<PayInfoBO> payInfoBOList =  null;
        try{
            PayInfoPO payInfoPO = new PayInfoPO();
            BeanUtils.copyProperties(record, payInfoPO);
            List<PayInfoPO> payInfoPOList = payInfoPOMapper.selectListByCondition(payInfoPO);
            payInfoBOList = this.convert(payInfoPOList);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }
        return payInfoBOList;
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
    public PageInfo<PayInfoBO> queryListByCondition(PayInfoBO record, int index, int size) {
        PageInfo<PayInfoBO> pageInfo= new PageInfo<>();
        if(record == null){
            record = new PayInfoBO();
        }

        try{
            Page page = PageHelper.startPage(index, size, true);
            PayInfoPO payInfoPO = new PayInfoPO();
            BeanUtils.copyProperties(record, payInfoPO);
            List<PayInfoPO> payInfoPOList = payInfoPOMapper.selectListByCondition(payInfoPO);
            List<PayInfoBO> payInfoBOList = convert(payInfoPOList);

            pageInfo.setList(payInfoBOList);
            BeanUtils.copyProperties(page, pageInfo);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }

        return pageInfo;
    }

    private List<PayInfoBO> convert(List<PayInfoPO> payInfoPOList) {
        List<PayInfoBO> payInfoBOList =  null;
        if(CollectionUtils.isNotEmpty(payInfoPOList)){
            payInfoBOList = new ArrayList<>(payInfoPOList.size());
            for(PayInfoPO payInfoPO : payInfoPOList){
                PayInfoBO payInfoBO = new PayInfoBO();
                BeanUtils.copyProperties(payInfoPO, payInfoBO);
                payInfoBOList.add(payInfoBO);
            }
        }

        return payInfoBOList;
    }
}
