package com.jiangbo.mall.biz;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiangbo.mall.bo.ActivityBO;
import com.jiangbo.mall.bo.ActivityProductRefBO;
import com.jiangbo.mall.bo.ProductBO;
import com.jiangbo.mall.bo.UserBO;
import com.jiangbo.mall.enums.ActivityStatusEnum;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.enums.ProductStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.service.ActivityProductRefService;
import com.jiangbo.mall.service.ActivityService;
import com.jiangbo.mall.service.ProductService;
import com.jiangbo.mall.vo.ActivityProductRefVO;
import com.jiangbo.mall.vo.ActivityVO;
import com.jiangbo.mall.vo.ProductVO;
import com.jiangbo.mall.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 产品
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Component
public class ActivityBiz {
    @Autowired
    ActivityService activityService;
    @Autowired
    ActivityProductRefService activityProductRefService;
    @Autowired
    ProductService productService;

    /**
     * 根据活动id和状态查询绑定活动的商品Id
     *
     * @param activityId
     * @return
     */
    public void selectValidProductIdListByActivityId(Integer activityId, List<ActivityProductRefVO> boundActivityProductVOList, List<ProductVO> unBoundActivityProductVOList){
        if(boundActivityProductVOList == null) {
            boundActivityProductVOList = new ArrayList<>();
        }
        if(unBoundActivityProductVOList == null) {
            unBoundActivityProductVOList = new ArrayList<>();
        }
        List<Integer> productIdList = activityProductRefService.selectProductIdListByActivityIdAndStatus(activityId, DataStatusEnum.NORMAL.getNum());
        ActivityProductRefBO activityProductRefBO = new ActivityProductRefBO();
        activityProductRefBO.setStatus(DataStatusEnum.NORMAL.getNum());
        activityProductRefBO.setActivityId(activityId);
        List<ActivityProductRefBO> activityProductRefBOList = activityProductRefService.queryListByCondition(activityProductRefBO);
        List<ActivityProductRefVO> activityProductRefVOList = this.convertActivityProductRef(activityProductRefBOList);
        if(activityProductRefVOList != null){
            boundActivityProductVOList.addAll(activityProductRefVOList);
        }

        ProductBO productBO = new ProductBO();
        //查询已经上架的商品
        productBO.setProductStatus(ProductStatusEnum.PUBLISHING.getNum());
        List<ProductBO> productBOList = productService.queryListByCondition(productBO);

        if(CollectionUtils.isNotEmpty(productBOList)){
            for(ProductBO bo : productBOList){
                ProductVO vo = new ProductVO();
                BeanUtils.copyProperties(bo, vo);
                if(!productIdList.contains(vo.getId())){
                    unBoundActivityProductVOList.add(vo);
                }

            }
        }
    }

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
            activityVOList = this.convertActivity(activityService.queryListByCondition(activityBO));
        }catch(Exception e){e.printStackTrace();
            throw new MallException(e.getMessage());
        }
        return activityVOList;
    }

    /**
     * 根据条件分页获得集合
     * @param activityVO
     * @return
     */
    public PageInfo<ActivityVO> getByCondition(ActivityVO activityVO, int pageIndex, int pageSize){
        if(activityVO == null){
            activityVO = new ActivityVO();
        }

        try{
            ActivityBO activityBO = new ActivityBO();
            BeanUtils.copyProperties(activityVO, activityBO);
            PageInfo<ActivityBO> pageInfo = activityService.queryListByCondition(activityBO, pageIndex, pageSize);
            return this.convert(pageInfo);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e.getMessage());
        }
    }

    public List<ActivityProductRefVO> getActivityProductRefVOByCondition (ActivityProductRefVO activityProductRefVO){
        try{
            if(activityProductRefVO == null){
                activityProductRefVO = new ActivityProductRefVO();
            }
            ActivityProductRefBO activityProductRefBO = new ActivityProductRefBO();
            BeanUtils.copyProperties(activityProductRefVO, activityProductRefBO);
            return this.convertActivityProductRef(activityProductRefService.queryListByCondition(activityProductRefBO));
        }catch(Exception e){e.printStackTrace();
            throw new MallException (e.getMessage());
        }
    }


    private PageInfo<ActivityVO> convert(PageInfo<ActivityBO> activityBOPageInfo){
        PageInfo<ActivityVO> pageInfo = new PageInfo<>();
        List<ActivityVO> activityVOList;
        if(CollectionUtils.isNotEmpty(activityBOPageInfo.getList())){
            activityVOList = new ArrayList<>(activityBOPageInfo.getList().size());
            for(ActivityBO activityBO : activityBOPageInfo.getList()){
                ActivityVO activityVO = new ActivityVO();
                BeanUtils.copyProperties(activityBO, activityVO);
                activityVOList.add(activityVO);
            }
            BeanUtils.copyProperties(activityBOPageInfo, pageInfo);
            pageInfo.setList(activityVOList);
        }

        return pageInfo;
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
     * 集合转换对象
     * @param activityProductRefBOList
     * @return
     */
    private List<ActivityProductRefVO> convertActivityProductRef(List<ActivityProductRefBO> activityProductRefBOList) {
        List<ActivityProductRefVO> activityProductRefVOList = null;
        if(CollectionUtils.isNotEmpty(activityProductRefBOList)){
            activityProductRefVOList = new ArrayList<>(activityProductRefBOList.size());
            for(ActivityProductRefBO activityProductRefBO : activityProductRefBOList){
                ActivityProductRefVO activityProductRefVO = new ActivityProductRefVO();
                BeanUtils.copyProperties(activityProductRefBO, activityProductRefVO);

                ProductVO productVO = new ProductVO();
                ProductBO productBO = productService.selectById(activityProductRefVO.getProductId());
                BeanUtils.copyProperties(productBO, productVO);
                activityProductRefVO.setProductVO(productVO);
                activityProductRefVOList.add(activityProductRefVO);
            }
        }
        return activityProductRefVOList;
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
        activityBO.setStatus(DataStatusEnum.NORMAL.getNum());
        activityBO.setActivityStatus(ActivityStatusEnum.WATING_AUDIT.getNum());
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

    /**
     * 根据时间查询
     * @param time
     * @return
     */
    public int queryByTime(Date time){
        return activityService.queryByTime(time);
    }

    /**
     * 添加商品绑定活动的关系
     * @param activityProductRefVO
     * @return
     */
    public boolean saveActivityProductRef(ActivityProductRefVO activityProductRefVO) {
        ActivityProductRefBO activityProductRefBO = new ActivityProductRefBO();
        BeanUtils.copyProperties(activityProductRefVO, activityProductRefBO);
        return activityProductRefService.save(activityProductRefBO) > 0;
    }

    /**
     * 更新商品绑定活动的关系
     * @param activityProductRefVO
     * @return
     */
    public boolean updateActivityProductRef(ActivityProductRefVO activityProductRefVO) {
        ActivityProductRefBO activityProductRefBO = new ActivityProductRefBO();
        BeanUtils.copyProperties(activityProductRefVO, activityProductRefBO);
        return activityProductRefService.updateByCondition(activityProductRefBO) > 0;
    }
}
