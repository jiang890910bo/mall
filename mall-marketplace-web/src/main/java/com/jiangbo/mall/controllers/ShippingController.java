package com.jiangbo.mall.controllers;

import com.jiangbo.mall.ClientResult;
import com.jiangbo.mall.biz.ShippingBiz;
import com.jiangbo.mall.enums.ClientResultStatusEnum;
import com.jiangbo.mall.vo.ShippingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 收获地址
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Controller
@RequestMapping("/shipping")
public class ShippingController extends BaseController {
    @Autowired
    ShippingBiz shippingBiz;

    /**
     * 收货地址列表
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/shippingList")
    public String shippingList(ModelMap modelMap){
        modelMap.addAttribute("shippingList", shippingBiz.getShippingsByCondition(new ShippingVO()));
        return "shipping/shippingList";
    }

    /**
     * 添加收获地址
     * @return
     */
    @RequestMapping(value="/addShipping", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ClientResult addShipping(ShippingVO shippingVO){//注意：spring4.0 不需要@RequestBody,使用了反而会报 “Unsupported Media Type 415”错误
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
         try{
             if(shippingBiz.save(shippingVO)){
                 clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
                 clientResult.setResult(shippingVO);
             }
         }catch(Exception e){
             e.printStackTrace();
         }
        return clientResult;
    }

    /**
     * 添加收获地址
     * @return
     */
    @RequestMapping(value="/getShipping/${id}", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ClientResult addShipping(@PathVariable("id") Integer id){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            ShippingVO shippingVO = shippingBiz.getById(id);
            if(shippingVO != null){
                clientResult = new ClientResult(ClientResultStatusEnum.CORRECT);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return clientResult;
    }
}
