package com.jiangbo.mall.controllers;

import com.jiangbo.mall.ClientResult;
import com.jiangbo.mall.biz.ImageBiz;
import com.jiangbo.mall.enums.ClientResultStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * 图片
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Controller
@RequestMapping("/image")
public class ImageController extends BaseController {
    @Autowired
    ImageBiz imageBiz;

    /**
     * 图片列表
     * @param modelMap
     * @return
     */
    @RequestMapping(value="/imageList")
    public String activityList(@RequestParam(value = "pageIndex", required = false, defaultValue = "1")Integer pageIndex, @RequestParam(value = "pageSize", required = false, defaultValue = "10")Integer pageSize,ModelMap modelMap){
        modelMap.addAttribute("pageInfo", imageBiz.getByCondition(new ImageVO(), pageIndex, pageSize));
        modelMap.addAttribute("dataStatusMap", dataStatusMap);
        return "image/imageList";
    }

    /**
     * 跳转至添加图片
     * @return
     */
    @RequestMapping(value = "/toAddImage")
    public String toAddImage(){
        return "image/addImage";
    }

    /**
     * 添加图片
     * @param imageName
     * @param imageFile
     * @return
     */
    @RequestMapping(value = "/addImage", method = RequestMethod.POST)
    public String addImage(@RequestParam(value = "imageName",required = false) String imageName, @RequestParam(value = "imageFile", required = false)MultipartFile imageFile){
        try{
           if(!imageFile.isEmpty()){
               String path = imageBiz.uploadImage(imageFile);

               ImageVO imageVO = new ImageVO();
               imageVO.setName(imageName);
               imageVO.setMainImageUrl(path);
               imageVO.setSubImageUrl(path);
               boolean result = imageBiz.save(imageVO);
               if(result){
                   return "redirect:/image/imageList";
               }
           }
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 跳转至添加图片
     * @return
     */
    @RequestMapping(value = "/toEditImage/{id}")
    public String toEditImage(ModelMap modelMap,@PathVariable("id") Integer id){
        modelMap.addAttribute("image", imageBiz.getById(id));
        return "image/editImage";
    }

    /**
     * 添加图片
     * @param id
     * @param imageFile
     * @return
     */
    @RequestMapping(value = "/editImage")
    public String editImage(@RequestParam("id") Integer id, @RequestParam("imageFile")MultipartFile imageFile){
        try{
            if(!imageFile.isEmpty()){
                String path = imageBiz.uploadImage(imageFile);

                ImageVO imageVO = imageBiz.getById(id);
                imageVO.setMainImageUrl(path);
                imageVO.setSubImageUrl(path);
                boolean result = imageBiz.updateByCondition(imageVO);
                if(result){
                    return "redirect:/image/imageList";
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteImage/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult deleteImage(@PathVariable("id") Integer id){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            boolean result = imageBiz.deleteById(id);
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
     * 更新图片
     * @param imageVO
     * @return
     */
    @RequestMapping(value = "/updateImage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientResult updateImage(ImageVO imageVO){
        ClientResult clientResult = new ClientResult(ClientResultStatusEnum.ERROR);
        try{
            boolean result = imageBiz.updateByCondition(imageVO);
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
    @RequestMapping(value = "/imageDetail/{id}")
    public String imageDetail(@PathVariable("id") Integer id, ModelMap modelMap){
        modelMap.addAttribute("image", imageBiz.getById(id));
        return "image/imageDetail";
    }

}
