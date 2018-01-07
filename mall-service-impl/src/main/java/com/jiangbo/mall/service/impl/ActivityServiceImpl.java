package com.jiangbo.mall.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiangbo.mall.bo.ActivityBO;
import com.jiangbo.mall.bo.ImageBO;
import com.jiangbo.mall.dao.ActivityPOMapper;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.pojo.ActivityPO;
import com.jiangbo.mall.pojo.ImagePO;
import com.jiangbo.mall.service.ActivityService;
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
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityPOMapper activityPOMapper;
    /**
     * 根据主键删除对象
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(Integer id) {
        ActivityPO activityPO = activityPOMapper.selectByPrimaryKey(id);
        if(activityPO == null) {
            return 0;
        }
        activityPO = new ActivityPO();
        activityPO.setId(id);
        activityPO.setStatus(DataStatusEnum.ABANDONED.getNum());
        return activityPOMapper.updateByPrimaryKey(activityPO);
    }

    /**
     * 插入对象
     *
     * @param record
     * @return
     */
    @Override
    public int save(ActivityBO record) {
        if(record == null){
            return 0;
        }
        ActivityPO activityPO = new ActivityPO();
        BeanUtils.copyProperties(record, activityPO);
        Date date = new Date();
        activityPO.setCreateTime(date);
        activityPO.setUpdateTime(date);
        return activityPOMapper.insert(activityPO);
    }

    /**
     * 根据主键查询对象
     *
     * @param id
     * @return
     */
    @Override
    public ActivityBO selectById(Integer id) {
        ActivityPO activityPO = activityPOMapper.selectByPrimaryKey(id);
        if(activityPO == null){
            return null;
        }
        ActivityBO activityBO = new ActivityBO();
        BeanUtils.copyProperties(activityPO, activityBO);
        return activityBO;
    }

    /**
     * 根据参数更新对象
     *
     * @param record
     * @return
     */
    @Override
    public int updateByCondition(ActivityBO record) {
        if(record == null){
            return 0;
        }
        ActivityPO activityPO = new ActivityPO();
        BeanUtils.copyProperties(record, activityPO);
        activityPO.setUpdateTime(new Date());
        return activityPOMapper.updateByPrimaryKeySelective(activityPO);
    }

    /**
     * 根据参数查询对象集合
     *
     * @param record
     * @return
     */
    @Override
    public List<ActivityBO> queryListByCondition(ActivityBO record) {

        if(record == null){
            record = new ActivityBO();
        }
        List<ActivityBO> activityBOList = null;
        try{
            ActivityPO activityPO = new ActivityPO();
            BeanUtils.copyProperties(record, activityPO);
            List<ActivityPO> activityPOList = activityPOMapper.selectListByCondition(activityPO);
            activityBOList = this.convert(activityPOList);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }
        return activityBOList;
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
    public PageInfo<ActivityBO> queryListByCondition(ActivityBO record, int index, int size) {
        PageInfo<ActivityBO> pageInfo= new PageInfo<>();
        if(record == null){
            record = new ActivityBO();
        }

        try{
            Page page = PageHelper.startPage(index, size, true);
            ActivityPO activityPO = new ActivityPO();
            BeanUtils.copyProperties(record, activityPO);
            List<ActivityPO> activityPOList = activityPOMapper.selectListByCondition(activityPO);
            List<ActivityBO> activityBOList = convert(activityPOList);

            pageInfo.setList(activityBOList);
            BeanUtils.copyProperties(page, pageInfo);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }

        return pageInfo;
    }

    private List<ActivityBO> convert(List<ActivityPO> activityPOList) {
        List<ActivityBO> activityBOList = null;
        if(CollectionUtils.isNotEmpty(activityPOList)){
            activityBOList = new ArrayList<>(activityPOList.size());
            for(ActivityPO activityPO : activityPOList){
                ActivityBO activityBO = new ActivityBO();
                BeanUtils.copyProperties(activityPO, activityBO);
                activityBOList.add(activityBO);
            }
        }

        return activityBOList;
    }

    @Override
    public int queryByTime(Date time){
        return activityPOMapper.queryByTime(time);
    }

    /**
     * 查询当前正在进行中的活动
     *
     * @return
     */
    @Override
    public List<ActivityBO> selectCurrentActivity() {
        List<ActivityPO> list = activityPOMapper.selectCurrentActivity();
        return this.convert(list);
    }

    /**
     * 查询距离当前最近时间待开始的活动
     * @return
     */
    public List<ActivityBO> selectLatestActivity(){
        List<ActivityPO> list = activityPOMapper.selectLatestActivity();
        return this.convert(list);
    }


    /**
     * 是否有当前开始的活动
     *
     * @return
     */
    @Override
    public boolean hasCurrentActivity() {
        return activityPOMapper.hasCurrentActivity() > 0;
    }
}
