package com.jiangbo.mall.controllers;

import com.jiangbo.mall.ClientResult;
import com.jiangbo.mall.biz.CategoryBiz;
import com.jiangbo.mall.enums.ClientResultStatusEnum;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.vo.CategoryVO;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 商品目录
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController {
    @Autowired
    CategoryBiz categoryBiz;

    /**
     * 目录列表
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/categoryList")
    public String categoryList(@RequestParam(value = "pageIndex", required = false, defaultValue = "1")Integer pageIndex, @RequestParam(value = "pageSize", required = false, defaultValue = "10")Integer pageSize,ModelMap modelMap){
        modelMap.addAttribute("pageInfo", categoryBiz.getCategorysByCondition(new CategoryVO(), pageIndex, pageSize));
        modelMap.addAttribute("dataStatusMap", dataStatusMap);
        return "category/categoryList";
    }

    @RequestMapping(value = "/toAddCategory")
    public String toAddCategory(ModelMap modelMap){
        modelMap.addAttribute("parentCategoryList", categoryBiz.getAllValid());
        return "category/addCategory";
    }


    /**
     * 添加目录
     * @param categoryVO
     * @return
     */
    @RequestMapping(value = "/addCategory", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult addCategory(CategoryVO categoryVO){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            boolean result = categoryBiz.save(categoryVO);
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
     * 禁用目录
     * @param id
     * @return
     */
    @RequestMapping(value = "/abandonCategory/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult abandonCategory(@PathVariable("id") Integer id){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            CategoryVO categoryVO = categoryBiz.getById(id);
            if(categoryVO != null){
                categoryVO.setStatus(DataStatusEnum.ABANDONED.getNum());
                boolean result = categoryBiz.updateByCondition(categoryVO);
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
     * 启用目录
     * @param id
     * @return
     */
    @RequestMapping(value = "/enableCategory/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult enableCategory(@PathVariable("id") Integer id){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            CategoryVO categoryVO = categoryBiz.getById(id);
            if(categoryVO != null){
                categoryVO.setStatus(DataStatusEnum.NORMAL.getNum());
            }
            boolean result = categoryBiz.updateByCondition(categoryVO);
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
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toEditCategory/{id}")
    public String toEditCategory(@PathVariable("id") Integer id, ModelMap modelMap){
        modelMap.addAttribute("parentCategoryList", categoryBiz.getAllValid());
        modelMap.addAttribute("category", categoryBiz.getById(id));
        modelMap.addAttribute("dataStatusMap", dataStatusMap);

        return "category/editCategory";
    }



    /**
     * 更新目录
     * @param categoryVO
     * @return
     */
    @RequestMapping(value = "/updateCategory", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult updateCategory(CategoryVO categoryVO){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            boolean result = categoryBiz.updateByCondition(categoryVO);
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
     * 根据id查询
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/getCategory/{id}")
    public String getCategoryById(@PathVariable("id") Integer id, ModelMap modelMap){
        modelMap.addAttribute("category", categoryBiz.getById(id));
        modelMap.addAttribute("dataStatusMap", dataStatusMap);
        return "category/categoryDetail";
    }


}
