package com.jiangbo.mall.biz;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiangbo.mall.bo.CategoryBO;
import com.jiangbo.mall.bo.ImageBO;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.service.ImageService;
import com.jiangbo.mall.vo.CategoryVO;
import com.jiangbo.mall.vo.ImageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 产品
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Component
public class ImageBiz {
    @Autowired
    ImageService imageService;

    @Value("${image.url.prefix}")
    String imageWebUrlPrefix;

    @Value("${image.physics.file.category}")
    String imagePhysicsFileCategory;

    /**
     * 根据条件获得集合
     * @param imageVO
     * @return
     */
    public List<ImageVO> getByCondition(ImageVO imageVO){
        List<ImageVO> imageVOList;
        try{
            ImageBO imageBO = new ImageBO();
            BeanUtils.copyProperties(imageVO, imageBO);
            imageVOList = this.convert(imageService.queryListByCondition(imageBO));
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }
        return imageVOList;
    }

    /**
     * 根据条件分页获得集合
     * @param imageVO
     * @return
     */
    public PageInfo<ImageVO> getByCondition(ImageVO imageVO, int pageIndex, int pageSize){
        if(imageVO == null){
            imageVO = new ImageVO();
        }
        try{

            ImageBO imageBO = new ImageBO();
            BeanUtils.copyProperties(imageVO, imageBO);
            PageInfo<ImageBO> imageBOPageInfo = imageService.queryListByCondition(imageBO, pageIndex, pageSize);

            return this.convert(imageBOPageInfo);
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }
    }

    private PageInfo<ImageVO> convert(PageInfo<ImageBO> imageBOPageInfo){
        PageInfo<ImageVO> pageInfo = new PageInfo<>();
        List<ImageVO> imageVOList;
        if(CollectionUtils.isNotEmpty(imageBOPageInfo.getList())){
            imageVOList = new ArrayList<>(imageBOPageInfo.getList().size());
            for(ImageBO imageBO : imageBOPageInfo.getList()){
                ImageVO imageVO = new ImageVO();
                BeanUtils.copyProperties(imageBO, imageVO);
                imageVOList.add(imageVO);
                imageVO.setMainImageUrl(imageWebUrlPrefix + imageVO.getMainImageUrl());
                imageVO.setSubImageUrl(imageWebUrlPrefix + imageVO.getSubImageUrl());
            }
            BeanUtils.copyProperties(imageBOPageInfo, pageInfo);
            pageInfo.setList(imageVOList);
        }

        return pageInfo;
    }

    /**
     * 集合转换对象
     * @param imageBOs
     * @return
     */
    private List<ImageVO> convert(List<ImageBO> imageBOs) {
        List<ImageVO> imageVOList = null;
        if(CollectionUtils.isNotEmpty(imageBOs)){
            imageVOList = new ArrayList<>(imageBOs.size());
            for(ImageBO imageBO : imageBOs){
                ImageVO imageVO = new ImageVO();
                BeanUtils.copyProperties(imageBO, imageVO);

                imageVO.setMainImageUrl(imageWebUrlPrefix + imageVO.getMainImageUrl());
                imageVO.setSubImageUrl(imageWebUrlPrefix + imageVO.getSubImageUrl());
                imageVOList.add(imageVO);
            }
        }
        return imageVOList;
    }

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    public ImageVO getById(Integer id){
        ImageVO imageVO = new ImageVO();
        ImageBO imageBO = imageService.selectById(id);
        if(imageBO != null){
            BeanUtils.copyProperties(imageBO, imageVO);
            imageVO.setMainImageUrl(imageWebUrlPrefix + imageVO.getMainImageUrl());
            imageVO.setSubImageUrl(imageWebUrlPrefix + imageVO.getSubImageUrl());
            return imageVO;
        }
        return null;
    }

    /**
     * 新增
     * @param imageVO
     * @return
     */
    public boolean save(ImageVO imageVO){
        ImageBO imageBO = new ImageBO();
        BeanUtils.copyProperties(imageVO, imageBO);
        imageBO.setStatus(DataStatusEnum.NORMAL.getNum());
        return imageService.save(imageBO) > 0 ? true : false;
    }

    /**
     * 根据id逻辑删除
     * @param productId
     * @return
     */
    public boolean deleteById(Integer productId){
        ImageBO imageBO = imageService.selectById(productId);
        if(imageBO != null){
            imageBO = new ImageBO();
            imageBO.setId(productId);
            imageBO.setStatus(DataStatusEnum.ABANDONED.getNum());
            return imageService.updateByCondition(imageBO) > 0 ? true : false;
        }
        return false;
    }

    /**
     * 根据条件更新
     * @param imageVO
     * @return
     */
    public boolean updateByCondition(ImageVO imageVO){
        ImageBO imageBO = new ImageBO();
        BeanUtils.copyProperties(imageVO, imageBO);
        return imageService.updateByCondition(imageBO) > 0 ? true:false;
    }

    public String uploadImage(MultipartFile imageFile) throws MallException{
        String path = null;
        try{
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            String contentType = imageFile.getContentType();
            //获得文件后缀名称
            String imageName=contentType.substring(contentType.indexOf("/")+1);
            path="/product_image/"+uuid+"."+imageName;
            File file = new File(imagePhysicsFileCategory + path);
            if(!file.exists()){
                file.mkdirs();
            }
            imageFile.transferTo(file);

        }catch(Exception e){
            throw new MallException(e);
        }

        return path;
    }

}
