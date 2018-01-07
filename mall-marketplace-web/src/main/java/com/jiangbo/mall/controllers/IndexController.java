package com.jiangbo.mall.controllers;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.jiangbo.mall.ClientResult;
import com.jiangbo.mall.RedisKeyConstant;
import com.jiangbo.mall.biz.*;
import com.jiangbo.mall.enums.ClientResultStatusEnum;
import com.jiangbo.mall.util.SessionKeyUtil;
import com.jiangbo.mall.util.SessionUtil;
import com.jiangbo.mall.vo.ActivityProductRefVO;
import com.jiangbo.mall.vo.ActivityVO;
import com.jiangbo.mall.vo.ProductVO;
import com.jiangbo.mall.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;

/**
 * 登陆
 * @author Jiangbo Cheng on 2017/10/20.
 */
@Controller
@RequestMapping(value = "/index")
public class IndexController extends BaseController{

    @Autowired
    private UserBiz userBiz;
    @Autowired
    private ProductBiz productBiz;
    @Autowired
    private RedisClientBiz redisClientBiz;
    @Autowired
    private ActivityProductRefBiz activityProductRefBiz;
    @Autowired
    private ActivityBiz activityBiz;

    /**
     * 主页浏览商品
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping(value = "/main")
    public String index(ModelMap modelMap, HttpServletRequest request){
        ActivityProductRefVO hotProduct;
        List<ActivityProductRefVO> boundActivityProductVOList = null;

        try{

            //检查是否当前是否有活动开始
            if(activityBiz.hasCurrentActivity()){
                if(redisClientBiz.exists(RedisKeyConstant.INDEX_WORKING_PRODUCTlIST_DATA)){
                    boundActivityProductVOList = redisClientBiz.getList(RedisKeyConstant.INDEX_WORKING_PRODUCTlIST_DATA, ActivityProductRefVO.class);
                }else{
                    boundActivityProductVOList = activityProductRefBiz.selectValidProductIdListByActivityId();
                    if(CollectionUtils.isNotEmpty(boundActivityProductVOList)) {
                        redisClientBiz.setList(RedisKeyConstant.INDEX_WORKING_PRODUCTlIST_DATA, boundActivityProductVOList, SessionKeyUtil.FIVE_MINUTE);
                    }
                }
            }else{
                if(redisClientBiz.exists(RedisKeyConstant.INDEX_WAITING_PRODUCTlIST_DATA)){
                    boundActivityProductVOList = redisClientBiz.getList(RedisKeyConstant.INDEX_WAITING_PRODUCTlIST_DATA, ActivityProductRefVO.class);
                }else{
                    boundActivityProductVOList = activityProductRefBiz.selectLatestProductIdListByActivityId();
                    if(CollectionUtils.isNotEmpty(boundActivityProductVOList)) {
                        redisClientBiz.setList(RedisKeyConstant.INDEX_WAITING_PRODUCTlIST_DATA, boundActivityProductVOList, SessionKeyUtil.FIVE_MINUTE);
                    }
                }
            }


            //加载内存的库存
            if(CollectionUtils.isNotEmpty(boundActivityProductVOList)){
                for(ActivityProductRefVO activityProductRefVO : boundActivityProductVOList){
                    String cacheKey = RedisKeyConstant.PRODUCT_STOCK_PREFIX + activityProductRefVO.getId();
                    Object objectStock = redisClientBiz.get(cacheKey);
                    if(objectStock != null){
                        activityProductRefVO.setStock(Integer.parseInt(objectStock.toString()));
                    }else{
                        activityProductRefVO.setStock(0);
                    }
                }

                hotProduct =  boundActivityProductVOList.get(0);
                boundActivityProductVOList.remove(0);
                modelMap.addAttribute("activityId", hotProduct.getActivityId());
                modelMap.addAttribute("hotProduct", hotProduct);
                modelMap.addAttribute("boundActivityProductVOList", boundActivityProductVOList);
            }

            String sessionIdStr = SessionUtil.getSessionId(request);
            if(sessionIdStr != null) {
                modelMap.addAttribute("user", redisClientBiz.getObject(sessionIdStr, UserVO.class));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return "index";
    }

}
