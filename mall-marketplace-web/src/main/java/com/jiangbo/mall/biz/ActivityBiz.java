package com.jiangbo.mall.biz;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.jiangbo.mall.bo.ActivityBO;
import com.jiangbo.mall.bo.ActivityProductRefBO;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.service.ActivityService;
import com.jiangbo.mall.vo.ActivityVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Component
public class ActivityBiz {
    @Autowired
    ActivityService activityService;

    /**
     * 根据条件获得集合
     * @param activityVO
     * @return
     */
    public List<ActivityVO> getByCondition(ActivityVO activityVO){
        List<ActivityVO> activityVOList;
        try{
            ActivityBO activityBO = new ActivityBO();
            BeanUtils.copyProperties(activityVO, activityBO);
            activityVOList = this.convert(activityService.queryListByCondition(activityBO));
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }
        return activityVOList;
    }

    /**
     * 集合转换对象
     * @param activityBOs
     * @return
     */
    private List<ActivityVO> convert(List<ActivityBO> activityBOs) {
        List<ActivityVO> activityVOList = null;
        if(CollectionUtils.isNotEmpty(activityBOs)){
            activityVOList = new ArrayList<>(activityBOs.size());
            for(ActivityBO activityBO : activityBOs){
                ActivityVO activityVO = new ActivityVO();
                BeanUtils.copyProperties(activityBO, activityVO);

                activityVOList.add(activityVO);
            }
        }
        return activityVOList;
    }

    /**
     * 是否有当前开始的活动
     * @return
     */
    public boolean hasCurrentActivity(){
        return activityService.hasCurrentActivity();
    }

    public ActivityBO selectCurrentActivity(){
        List<ActivityBO> activityBOList = activityService.selectCurrentActivity();
        if(CollectionUtils.isNotEmpty(activityBOList)){
            return activityBOList.get(0);
        }

        return null;
    }


    /**
     * 查询距离当前最近时间待开始的活动
     * @return
     */
    public ActivityBO selectLatestActivity(){
        List<ActivityBO> activityBOList = activityService.selectLatestActivity();
        if(CollectionUtils.isNotEmpty(activityBOList)){
            return activityBOList.get(0);
        }

        return null;
    }

    /**
     * 根据Id查询
     * @param activityId
     * @return
     */
    public ActivityVO getById(Integer activityId){
        ActivityVO activityVO = new ActivityVO();
        ActivityBO activityBO = activityService.selectById(activityId);
        if(activityBO != null){
            BeanUtils.copyProperties(activityBO, activityVO);
            return activityVO;
        }
        return null;
    }

    /**
     * 新增
     * @param activityVO
     * @return
     */
    public boolean save(ActivityVO activityVO){
        ActivityBO activityBO = new ActivityBO();
        BeanUtils.copyProperties(activityVO, activityBO);
        return activityService.save(activityBO) > 0 ? true : false;
    }

    /**
     * 根据id逻辑删除
     * @param productId
     * @return
     */
    public boolean deleteById(Integer productId){
        ActivityBO activityBO = activityService.selectById(productId);
        if(activityBO != null){
            activityBO = new ActivityBO();
            activityBO.setId(productId);
            activityBO.setStatus(DataStatusEnum.ABANDONED.getNum());
            return activityService.updateByCondition(activityBO) > 0 ? true : false;
        }
        return false;
    }

    /**
     * 根据条件更新
     * @param activityVO
     * @return
     */
    public boolean updateByCondition(ActivityVO activityVO){
        ActivityBO activityBO = new ActivityBO();
        BeanUtils.copyProperties(activityVO, activityBO);
        return activityService.updateByCondition(activityBO) > 0 ? true:false;
    }

}
