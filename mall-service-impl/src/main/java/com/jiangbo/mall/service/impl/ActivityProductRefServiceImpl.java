package com.jiangbo.mall.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiangbo.mall.bo.ActivityBO;
import com.jiangbo.mall.bo.ActivityProductRefBO;
import com.jiangbo.mall.dao.ActivityProductRefPOMapper;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.pojo.ActivityPO;
import com.jiangbo.mall.pojo.ActivityProductRefPO;
import com.jiangbo.mall.service.ActivityProductRefService;
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
public class ActivityProductRefServiceImpl implements ActivityProductRefService {

    @Autowired
    ActivityProductRefPOMapper activityProductRefPOMapper;
    /**
     * 根据主键删除对象
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(Integer id) {
        ActivityProductRefPO activityProductRefPO = activityProductRefPOMapper.selectByPrimaryKey(id);
        if(activityProductRefPO == null) {
            return 0;
        }
        activityProductRefPO = new ActivityProductRefPO();
        activityProductRefPO.setId(id);
        activityProductRefPO.setStatus(DataStatusEnum.ABANDONED.getNum());
        return activityProductRefPOMapper.updateByPrimaryKey(activityProductRefPO);
    }

    /**
     * 插入对象
     *
     * @param record
     * @return
     */
    @Override
    public int save(ActivityProductRefBO record) {
        if(record == null){
            return 0;
        }
        ActivityProductRefPO activityProductRefPO = new ActivityProductRefPO();
        BeanUtils.copyProperties(record, activityProductRefPO);
        Date date = new Date();
        activityProductRefPO.setCreateTime(date);
        activityProductRefPO.setUpdateTime(date);
        return activityProductRefPOMapper.insert(activityProductRefPO);
    }

    /**
     * 根据主键查询对象
     *
     * @param id
     * @return
     */
    @Override
    public ActivityProductRefBO selectById(Integer id) {
        ActivityProductRefPO activityProductRefPO = activityProductRefPOMapper.selectByPrimaryKey(id);
        if(activityProductRefPO == null){
            return null;
        }
        ActivityProductRefBO activityProductRefBO = new ActivityProductRefBO();
        BeanUtils.copyProperties(activityProductRefPO, activityProductRefBO);
        return activityProductRefBO;
    }

    /**
     * 根据参数更新对象
     *
     * @param record
     * @return
     */
    @Override
    public int updateByCondition(ActivityProductRefBO record) {
        if(record == null){
            return 0;
        }
        ActivityProductRefPO activityProductRefPO = new ActivityProductRefPO();
        BeanUtils.copyProperties(record, activityProductRefPO);
        activityProductRefPO.setUpdateTime(new Date());
        return activityProductRefPOMapper.updateByPrimaryKeySelective(activityProductRefPO);
    }

    /**
     * 根据参数查询对象集合
     *
     * @param record
     * @return
     */
    @Override
    public List<ActivityProductRefBO> queryListByCondition(ActivityProductRefBO record) {
        if(record == null){
            record = new ActivityProductRefBO();
        }
        List<ActivityProductRefBO> activityProductRefBOList = null;
        try{
            ActivityProductRefPO activityProductRefPO = new ActivityProductRefPO();
            BeanUtils.copyProperties(record, activityProductRefPO);
            List<ActivityProductRefPO> activityProductRefPOList = activityProductRefPOMapper.selectListByCondition(activityProductRefPO);
            activityProductRefBOList = this.convert(activityProductRefPOList);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }
        return activityProductRefBOList;
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
    public PageInfo<ActivityProductRefBO> queryListByCondition(ActivityProductRefBO record, int index, int size) {
        PageInfo<ActivityProductRefBO> pageInfo= new PageInfo<>();
        if(record == null){
            record = new ActivityProductRefBO();
        }

        try{
            Page page = PageHelper.startPage(index, size, true);
            ActivityProductRefPO activityProductRefPO = new ActivityProductRefPO();
            BeanUtils.copyProperties(record, activityProductRefPO);
            List<ActivityProductRefPO> activityProductRefPOList = activityProductRefPOMapper.selectListByCondition(activityProductRefPO);
            List<ActivityProductRefBO> activityProductRefBOList = convert(activityProductRefPOList);

            pageInfo.setList(activityProductRefBOList);
            BeanUtils.copyProperties(page, pageInfo);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);        }

        return pageInfo;
    }

    private List<ActivityProductRefBO> convert(List<ActivityProductRefPO> activityProductRefPOList) {
        List<ActivityProductRefBO> activityProductRefBOList = null;
        if(CollectionUtils.isNotEmpty(activityProductRefPOList)){
            activityProductRefBOList = new ArrayList<>(activityProductRefPOList.size());
            for(ActivityProductRefPO activityProductRefPO : activityProductRefPOList){
                ActivityProductRefBO activityProductRefBO = new ActivityProductRefBO();
                BeanUtils.copyProperties(activityProductRefPO, activityProductRefBO);
                activityProductRefBOList.add(activityProductRefBO);
            }
        }

        return activityProductRefBOList;
    }

    /**
     * 根据活动id和状态查询绑定活动的商品Id
     *
     * @param activityId
     * @param status
     * @return
     */
    @Override
    public List<Integer> selectProductIdListByActivityIdAndStatus(Integer activityId, Integer status) {
        return activityProductRefPOMapper.selectProductIdListByActivityIdAndStatus(activityId, status);
    }


    /**
     * 减库存
     *
     * @param activityProductRefId
     * @param num
     * @return
     */
    @Override
    public boolean subtractStock(Integer activityProductRefId, int num, int status) {
        return activityProductRefPOMapper.subtractStock(activityProductRefId,num, status);
    }
}
