package com.jiangbo.mall.controllers;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.jiangbo.mall.ClientResult;
import com.jiangbo.mall.biz.ActivityBiz;
import com.jiangbo.mall.biz.ProductBiz;
import com.jiangbo.mall.enums.ActivityStatusEnum;
import com.jiangbo.mall.enums.ClientResultStatusEnum;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.vo.ActivityProductRefVO;
import com.jiangbo.mall.vo.ActivityVO;
import com.jiangbo.mall.vo.CategoryVO;
import com.jiangbo.mall.vo.ProductVO;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 活动
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {
    @Autowired
    ActivityBiz activityBiz;

    @Autowired
    ProductBiz productBiz;

    /**
     * 活动列表
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/activityList")
    public String activityList(@RequestParam(value = "pageIndex", required = false, defaultValue = "1")Integer pageIndex, @RequestParam(value = "pageSize", required = false, defaultValue = "10")Integer pageSize,ModelMap modelMap){

        modelMap.addAttribute("pageInfo", activityBiz.getByCondition(new ActivityVO(), pageIndex, pageSize));
        modelMap.addAttribute("dataStatusMap", dataStatusMap);
        modelMap.addAttribute("activityStatusMap", activityStatusMap);
        return "activity/activityList";
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteActivity/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult deleteActivity(@PathVariable("id") Integer id){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);

        try{
            boolean result = activityBiz.deleteById(id);
            if(result){
                clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
                clientResult.setResult(result);
                return clientResult;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return clientResult;
    }

    /**
     * 跳转到添加页面
     * @return
     */
    @RequestMapping("/toAddActivity")
    public String toAddActivity(){
        return "activity/addActivity";
    }

    /**
     * 添加活动
     * @return
     */
    @RequestMapping(value = "/addActivity", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult addActivity(ActivityVO activityVO){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            boolean result = activityBiz.save(activityVO);
            if(result){
                clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
                clientResult.setResult(result);
                return clientResult;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return clientResult;
    }

    /**
     * 活动详情
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/activityDetail/{id}")
    public String activiytDetail(@PathVariable(value = "id")Integer id, ModelMap modelMap){
        modelMap.addAttribute("dataStatusMap", dataStatusMap);
        modelMap.addAttribute("activityStatusMap", activityStatusMap);
        modelMap.addAttribute("activity", activityBiz.getById(id));
        return "activity/activityDetail";
    }

    /**
     * 废弃活动
     * @param id
     * @return
     */
    @RequestMapping(value = "/abandonActivity/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult abandonActivity(@PathVariable("id") Integer id){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            ActivityVO activityVO = activityBiz.getById(id);
            if(activityVO != null){
                activityVO.setStatus(DataStatusEnum.ABANDONED.getNum());
                boolean result = activityBiz.updateByCondition(activityVO);
                if(result){
                    clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
                    clientResult.setResult(result);
                    return clientResult;
                }
            }else{
                clientResult.setErrorMsg("未查询到对象");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return clientResult;
    }

    /**
     * 启用活动
     * @param id
     * @return
     */
    @RequestMapping(value = "/enableActivity/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult enableActivity(@PathVariable("id") Integer id){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            ActivityVO activityVO = activityBiz.getById(id);
            if(activityVO != null){
                activityVO.setStatus(DataStatusEnum.NORMAL.getNum());
            }
            boolean result = activityBiz.updateByCondition(activityVO);
            if(result){
                clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
                clientResult.setResult(result);
                return clientResult;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return clientResult;
    }


    /**
     * 审核通过
     * @param id
     * @return
     */
    @RequestMapping(value = "/passAudit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult passAudit(@PathVariable("id") Integer id){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            ActivityVO activityVO = activityBiz.getById(id);
            if(activityVO != null){
                activityVO.setActivityStatus(ActivityStatusEnum.WATING_PUBLIC.getNum());
            }
            boolean result = activityBiz.updateByCondition(activityVO);
            if(result){
                clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
                clientResult.setResult(result);
                return clientResult;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return clientResult;
    }


    /**
     * 发布
     * @return
     */
    @RequestMapping(value = "/public/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult addActivity(@PathVariable("id") Integer id){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            ActivityVO activityVO = activityBiz.getById(id);
            if(activityVO != null){
                activityVO.setActivityStatus(ActivityStatusEnum.PUBLISHING.getNum());
            }
            boolean result = activityBiz.updateByCondition(activityVO);
            if(result){
                clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
                clientResult.setResult(result);
                return clientResult;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return clientResult;
    }


    /**
     * 跳转到编辑页面
     * @return
     */
    @RequestMapping("/toEditActivity/{activityId}")
    public String toEdtiActivity(@PathVariable(name = "activityId")Integer activityId, ModelMap modelMap){
        modelMap.addAttribute("activity", activityBiz.getById(activityId));
        return "activity/editActivity";
    }

    /**
     * 编辑更新活动
     * @return
     */
    @RequestMapping(value = "/updateActivity", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult updateActivity(ActivityVO activityVO){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            boolean result = activityBiz.updateByCondition(activityVO);
            if(result){
                clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
                clientResult.setResult(result);
                return clientResult;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return clientResult;
    }

    /**
     * 跳转到添加商品到活动中的页面
     * @param activityId
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/addActivityProduct/{id}")
    public String addActivityProduct(@PathVariable(name = "id")Integer activityId, ModelMap modelMap){
        ActivityProductRefVO activityProductRefVO = new ActivityProductRefVO();
        activityProductRefVO.setActivityId(activityId);
        activityProductRefVO.setStatus(DataStatusEnum.NORMAL.getNum());

        java.util.List<ActivityProductRefVO> boundActivityProductVOList = new ArrayList<>();
        List<ProductVO> unBoundActivityProductVOList = new ArrayList<>();
        activityBiz.selectValidProductIdListByActivityId(activityId, boundActivityProductVOList, unBoundActivityProductVOList);
        modelMap.addAttribute("activity", activityBiz.getById(activityId));
        modelMap.addAttribute("boundActivityProductVOList", boundActivityProductVOList);
        modelMap.addAttribute("unBoundActivityProductVOList", unBoundActivityProductVOList);

        return "activity/addActivityProduct";
    }

    /**
     * 跳转到查看活动商品的页面
     * @param activityId
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/queryActivityProduct/{id}")
    public String queryActivityProduct(@PathVariable(name = "id")Integer activityId, ModelMap modelMap){
        ActivityProductRefVO activityProductRefVO = new ActivityProductRefVO();
        activityProductRefVO.setActivityId(activityId);
        activityProductRefVO.setStatus(DataStatusEnum.NORMAL.getNum());

        java.util.List<ActivityProductRefVO> boundActivityProductVOList = new ArrayList<>();
        activityBiz.selectValidProductIdListByActivityId(activityId, boundActivityProductVOList, null);
        modelMap.addAttribute("activity", activityBiz.getById(activityId));
        modelMap.addAttribute("boundActivityProductVOList", boundActivityProductVOList);

        return "activity/queryActivityProduct";
    }


    /**
     * 商品参与活动
     * @param activityId
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/joinActivity/{activityId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult joinActivity(@PathVariable(name = "activityId")Integer activityId, @RequestParam(name = "productId")Integer productId, ModelMap modelMap){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            ProductVO productVO = productBiz.getById(productId);
            ActivityProductRefVO activityProductRefVO = new ActivityProductRefVO();
            activityProductRefVO.setActivityId(activityId);
            activityProductRefVO.setProductId(productId);
            activityProductRefVO.setStatus(DataStatusEnum.NORMAL.getNum());
            activityProductRefVO.setPrice(productVO.getPrice());
            activityProductRefVO.setSeckillPrice(productVO.getSeckillPrice());
            activityProductRefVO.setStock(productVO.getStock());
            boolean result = activityBiz.saveActivityProductRef(activityProductRefVO);
            if(result){
                clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
                clientResult.setResult(result);
                return clientResult;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return clientResult;
    }

    /**
     * 商品退出活动
     * @param activityProductRefId
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/exitActivity/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult exitActivity(@RequestParam(name = "activityProductRefId")Integer activityProductRefId, ModelMap modelMap){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            ActivityProductRefVO activityProductRefVO = new ActivityProductRefVO();
            activityProductRefVO.setId(activityProductRefId);
            List<ActivityProductRefVO> list = activityBiz.getActivityProductRefVOByCondition(activityProductRefVO);
            if(CollectionUtils.isNotEmpty(list)) {
                activityProductRefVO = list.get(0);
                activityProductRefVO.setStatus(DataStatusEnum.ABANDONED.getNum());
                boolean result = activityBiz.updateActivityProductRef(activityProductRefVO);
                if (result) {
                    clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
                    clientResult.setResult(result);
                    return clientResult;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return clientResult;
    }
}
