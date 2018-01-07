package com.jiangbo.mall.biz;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.jiangbo.mall.bo.ImageBO;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.service.ImageService;
import com.jiangbo.mall.vo.ImageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
     * @param imageId
     * @return
     */
    public ImageVO getById(Integer imageId){
        ImageVO imageVO = new ImageVO();
        ImageBO imageBO = imageService.selectById(imageId);
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

}
