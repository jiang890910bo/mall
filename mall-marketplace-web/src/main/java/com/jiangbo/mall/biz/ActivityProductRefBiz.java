package com.jiangbo.mall.biz;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.jiangbo.mall.RedisKeyConstant;
import com.jiangbo.mall.RedisValidTimeConstant;
import com.jiangbo.mall.bo.ActivityBO;
import com.jiangbo.mall.bo.ActivityProductRefBO;
import com.jiangbo.mall.bo.ProductBO;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.enums.ProductStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.service.ActivityProductRefService;
import com.jiangbo.mall.service.ActivityService;
import com.jiangbo.mall.service.ProductService;
import com.jiangbo.mall.vo.ActivityProductRefVO;
import com.jiangbo.mall.vo.ActivityVO;
import com.jiangbo.mall.vo.ProductVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Jiangbo Cheng on 2017/10/31.
 */
@Component
public class ActivityProductRefBiz {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ActivityProductRefService activityProductRefService;
    @Autowired
    RedisClientBiz redisClientBiz;
    @Autowired
    ProductBiz productBiz;
    @Autowired
    ActivityBiz activityBiz;

    /**
     * 服务启动即加载商品库存到redis
     */
    @PostConstruct
    private void loadStock(){
        logger.info("...Begin loading porduct stock...");
        ActivityProductRefVO activityProductRefVO = new ActivityProductRefVO();
        activityProductRefVO.setStatus(DataStatusEnum.NORMAL.getNum());
        List<ActivityProductRefVO> list = this.getByCondition(activityProductRefVO);
        for(ActivityProductRefVO vo : list){
            redisClientBiz.set(RedisKeyConstant.PRODUCT_STOCK_PREFIX + vo.getId(), vo.getStock().intValue(), RedisValidTimeConstant.TWO_HOURS);
        }
    }

    /**
     * 查询当前正在开始的活动与商品
     *
     * @return
     */
    public List<ActivityProductRefVO> selectValidProductIdListByActivityId(){
        List<ActivityProductRefVO> boundActivityProductVOList = new ArrayList<>();

       try{
           ActivityBO activityBO = activityBiz.selectCurrentActivity();
           if(activityBO != null) {
               ActivityProductRefBO activityProductRefBO = new ActivityProductRefBO();
               activityProductRefBO.setStatus(DataStatusEnum.NORMAL.getNum());
               activityProductRefBO.setActivityId(activityBO.getId());
               List<ActivityProductRefBO> activityProductRefBOList = activityProductRefService.queryListByCondition(activityProductRefBO);
               boundActivityProductVOList.addAll(this.convertActivityProductRef(activityProductRefBOList,null));
           }
       }catch(Exception e){
           e.printStackTrace();
           throw new MallException(e);
       }

        return boundActivityProductVOList;
    }

    /**
     * 查询距离当前时间最近的活动与商品
     *
     * @return
     */
    public List<ActivityProductRefVO> selectLatestProductIdListByActivityId(){
        List<ActivityProductRefVO> boundActivityProductVOList = new ArrayList<>();

        try{
            ActivityBO activityBO = activityBiz.selectLatestActivity();
            if(activityBO != null) {
                ActivityProductRefBO activityProductRefBO = new ActivityProductRefBO();
                activityProductRefBO.setStatus(DataStatusEnum.NORMAL.getNum());
                activityProductRefBO.setActivityId(activityBO.getId());
                List<ActivityProductRefBO> activityProductRefBOList = activityProductRefService.queryListByCondition(activityProductRefBO);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(activityBO.getStartTime());
                boundActivityProductVOList.addAll(this.convertActivityProductRef(activityProductRefBOList, calendar.get(Calendar.HOUR_OF_DAY)) );
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }

        return boundActivityProductVOList;
    }

    /**
     * 根据条件获得集合
     * @param activityVO
     * @return
     */
    public List<ActivityVO> getByCondition(ActivityVO activityVO){
        List<ActivityVO> activityVOList;
        try{

            activityVOList = activityBiz.getByCondition(activityVO);
        }catch(Exception e){
            throw new MallException(e);
        }
        return activityVOList;
    }

    public List<ActivityProductRefVO> getActivityProductRefVOByCondition (ActivityProductRefVO activityProductRefVO){
        try{
            if(activityProductRefVO == null){
                activityProductRefVO = new ActivityProductRefVO();
            }
            ActivityProductRefBO activityProductRefBO = new ActivityProductRefBO();
            BeanUtils.copyProperties(activityProductRefVO, activityProductRefBO);
            return this.convertActivityProductRef(activityProductRefService.queryListByCondition(activityProductRefBO),null);
        }catch(Exception e){
            throw new MallException (e);
        }
    }

    /**
     * 集合转换对象
     * @param activityProductRefBOList
     * @return
     */
    private List<ActivityProductRefVO> convertActivityProductRef(List<ActivityProductRefBO> activityProductRefBOList, Integer startHour) {
        List<ActivityProductRefVO> activityProductRefVOList = null;
        if(CollectionUtils.isNotEmpty(activityProductRefBOList)){
            activityProductRefVOList = new ArrayList<>(activityProductRefBOList.size());
            for(ActivityProductRefBO activityProductRefBO : activityProductRefBOList){
                ActivityProductRefVO activityProductRefVO = new ActivityProductRefVO();
                BeanUtils.copyProperties(activityProductRefBO, activityProductRefVO);

                ProductVO productVO = productBiz.getById(activityProductRefVO.getProductId());
                activityProductRefVO.setProductVO(productVO);
                if(startHour != null){
                    activityProductRefVO.setStartHour(startHour.toString());
                }
                activityProductRefVOList.add(activityProductRefVO);
            }
        }
        return activityProductRefVOList;
    }

    /**
     * 集合转换对象
     * @param activityBOs
     * @return
     */
    private List<ActivityVO> convertActivity(List<ActivityBO> activityBOs) {
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
     * 根据条件获得集合
     * @param activityProductRefVO
     * @return
     */
    public List<ActivityProductRefVO> getByCondition(ActivityProductRefVO activityProductRefVO){
        List<ActivityProductRefVO> activityProductRefVOList;
        try{
            ActivityProductRefBO activityProductRefBO = new ActivityProductRefBO();
            BeanUtils.copyProperties(activityProductRefVO, activityProductRefBO);
            activityProductRefVOList = this.convert(activityProductRefService.queryListByCondition(activityProductRefBO));
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }
        return activityProductRefVOList;
    }

    /**
     * 集合转换对象
     * @param activityProductRefBOs
     * @return
     */
    private List<ActivityProductRefVO> convert(List<ActivityProductRefBO> activityProductRefBOs) {
        List<ActivityProductRefVO> activityProductRefVOList = null;
        if(CollectionUtils.isNotEmpty(activityProductRefBOs)){
            activityProductRefVOList = new ArrayList<>(activityProductRefBOs.size());
            for(ActivityProductRefBO activityProductRefBO : activityProductRefBOs){
                ActivityProductRefVO activityProductRefVO = new ActivityProductRefVO();
                BeanUtils.copyProperties(activityProductRefBO, activityProductRefVO);

                activityProductRefVOList.add(activityProductRefVO);
            }
        }
        return activityProductRefVOList;
    }

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    public ActivityProductRefVO getById(Integer id){
        ActivityProductRefVO activityProductRefVO = new ActivityProductRefVO();
        ActivityProductRefBO activityProductRefBO = activityProductRefService.selectById(id);
        if(activityProductRefBO != null){
            BeanUtils.copyProperties(activityProductRefBO, activityProductRefVO);

            ProductVO productVO = productBiz.getById(activityProductRefVO.getProductId());
            activityProductRefVO.setProductName(productVO.getName());
            activityProductRefVO.setImageUrl(productVO.getImageVO().getMainImageUrl());
            return activityProductRefVO;
        }
        return null;
    }

    /**
     * 新增
     * @param activityProductRefVO
     * @return
     */
    public boolean save(ActivityProductRefVO activityProductRefVO){
        ActivityProductRefBO activityProductRefBO = new ActivityProductRefBO();
        BeanUtils.copyProperties(activityProductRefVO, activityProductRefBO);
        return activityProductRefService.save(activityProductRefBO) > 0 ? true : false;
    }

    /**
     * 根据id逻辑删除
     * @param productId
     * @return
     */
    public boolean deleteById(Integer productId){
        ActivityProductRefBO activityProductRefBO = activityProductRefService.selectById(productId);
        if(activityProductRefBO != null){
            activityProductRefBO = new ActivityProductRefBO();
            activityProductRefBO.setId(productId);
            activityProductRefBO.setStatus(DataStatusEnum.ABANDONED.getNum());
            return activityProductRefService.updateByCondition(activityProductRefBO) > 0 ? true : false;
        }
        return false;
    }



    /**
     * 减库存
     * @param activityProductRefId
     * @param num
     * @return
     */
    public boolean subtractStock(Integer activityProductRefId, int num, int status){
        return activityProductRefService.subtractStock(activityProductRefId, num, status);
    }
}
