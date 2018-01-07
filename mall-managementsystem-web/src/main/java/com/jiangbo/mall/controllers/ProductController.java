package com.jiangbo.mall.controllers;

import com.jiangbo.mall.ClientResult;
import com.jiangbo.mall.biz.ActivityBiz;
import com.jiangbo.mall.biz.CategoryBiz;
import com.jiangbo.mall.biz.ImageBiz;
import com.jiangbo.mall.biz.ProductBiz;
import com.jiangbo.mall.bo.CategoryBO;
import com.jiangbo.mall.enums.ActivityStatusEnum;
import com.jiangbo.mall.enums.ClientResultStatusEnum;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.enums.ProductStatusEnum;
import com.jiangbo.mall.vo.ActivityVO;
import com.jiangbo.mall.vo.CategoryVO;
import com.jiangbo.mall.vo.ImageVO;
import com.jiangbo.mall.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 产品
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {
    @Autowired
    ProductBiz productBiz;
    @Autowired
    ImageBiz imageBiz;
    @Autowired
    ActivityBiz activityBiz;
    @Autowired
    CategoryBiz categoryBiz;

    /**
     * 查看商品列表
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/productList")
    public String productList(@RequestParam(value = "pageIndex", required = false, defaultValue = "1")Integer pageIndex, @RequestParam(value = "pageSize", required = false, defaultValue = "10")Integer pageSize,ModelMap modelMap){
        modelMap.addAttribute("pageInfo", productBiz.getProductsByCondition(new ProductVO(), pageIndex, pageSize));
        modelMap.addAttribute("dataStatusMap", dataStatusMap);
        modelMap.addAttribute("productStatusMap", productStatusMap);
        return "product/productList";
    }

    /**
     * 跳转到添加产品页面
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/toAddProduct")
    public String toAddProduct(ModelMap modelMap){
        ImageVO imageVO = new ImageVO();
        imageVO.setStatus(DataStatusEnum.NORMAL.getNum());
        modelMap.addAttribute("imageList", imageBiz.getByCondition(imageVO));
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setStatus(DataStatusEnum.NORMAL.getNum());
        modelMap.addAttribute("categoryList", categoryBiz.getCategorysByCondition(categoryVO));
        modelMap.addAttribute("productStatusMap", productStatusMap);
        return "product/addProduct";
    }

    /**
     * 添加产品
     * @param productVO
     * @return
     */
    @RequestMapping(value = "/addProduct", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult addProduct(ProductVO productVO){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);

        try{
            boolean result = productBiz.save(productVO);
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
     * 产品详情
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/productDetail/{id}")
    public String productList(@PathVariable("id")Integer id, ModelMap modelMap){
        ProductVO productVO = productBiz.getById(id);
        modelMap.addAttribute("category", categoryBiz.getById(productVO.getCategoryId()));
        modelMap.addAttribute("image", imageBiz.getById(productVO.getImageId()));
        modelMap.addAttribute("product", productVO);
        return "product/productDetail";
    }


    /**
     * 跳转到更新产品页面
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/toEditProduct/{id}")
    public String toEditProduct(ModelMap modelMap,@PathVariable("id") Integer id){
        ImageVO imageVO = new ImageVO();
        imageVO.setStatus(DataStatusEnum.NORMAL.getNum());
        modelMap.addAttribute("imageList", imageBiz.getByCondition(imageVO));
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setStatus(DataStatusEnum.NORMAL.getNum());
        modelMap.addAttribute("categoryList", categoryBiz.getCategorysByCondition(categoryVO));
        modelMap.addAttribute("product", productBiz.getById(id));
        return "product/editProduct";
    }

    /**
     * 更新产品
     * @return
     */
    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult updateProduct(ProductVO productVO){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);

        try{
            boolean result = productBiz.updateByCondition(productVO);
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
     * 启用产品
     * @param id
     * @return
     */
    @RequestMapping(value = "/enableProduct/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult enableProduct(@PathVariable("id") Integer id){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            ProductVO productVO = productBiz.getById(id);
            if(productVO != null){
                productVO.setStatus(DataStatusEnum.NORMAL.getNum());
            }
            boolean result = productBiz.updateByCondition(productVO);
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
     * 废弃活动
     * @param id
     * @return
     */
    @RequestMapping(value = "/abandonProduct/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult abandonProduct(@PathVariable("id") Integer id){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            ProductVO productVO = productBiz.getById(id);
            if(productVO != null){
                productVO.setStatus(DataStatusEnum.ABANDONED.getNum());
                boolean result = productBiz.updateByCondition(productVO);
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
     * 下架
     * @param id
     * @return
     */
    @RequestMapping(value = "/leftShelf/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult passAudit(@PathVariable("id") Integer id){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            ProductVO productVO = productBiz.getById(id);
            if(productVO != null){
                productVO.setProductStatus(ProductStatusEnum.LEFT_SHELF.getNum());
            }
            boolean result = productBiz.updateByCondition(productVO);
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
     * 上架
     * @return
     */
    @RequestMapping(value = "/publish/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult addActivity(@PathVariable("id") Integer id){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            ProductVO productVO = productBiz.getById(id);
            if(productVO != null){
                productVO.setProductStatus(ProductStatusEnum.PUBLISHING.getNum());
            }
            boolean result = productBiz.updateByCondition(productVO);
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
}
