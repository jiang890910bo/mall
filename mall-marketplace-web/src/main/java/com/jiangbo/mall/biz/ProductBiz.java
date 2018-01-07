package com.jiangbo.mall.biz;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.jiangbo.mall.bo.ImageBO;
import com.jiangbo.mall.bo.ProductBO;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.enums.ProductStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.service.ActivityProductRefService;
import com.jiangbo.mall.service.ImageService;
import com.jiangbo.mall.service.ProductService;
import com.jiangbo.mall.vo.ActivityProductRefVO;
import com.jiangbo.mall.vo.ActivityVO;
import com.jiangbo.mall.vo.ImageVO;
import com.jiangbo.mall.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Component
public class ProductBiz {
    @Autowired
    ProductService productService;

    @Autowired
    ImageBiz imageBiz;

    @Autowired
    ActivityProductRefBiz activityProductRefBiz;

    @Autowired
    ActivityBiz activityBiz;

    /**
     * 根据条件获得集合
     * @param productVO
     * @return
     */
    public List<ProductVO> getProductsByCondition(ProductVO productVO){
        List<ProductVO> productVOList;
            ProductBO productBO = new ProductBO();
            productVO.setStatus(DataStatusEnum.NORMAL.getNum());
            productVO.setProductStatus(ProductStatusEnum.PUBLISHING.getNum());
            BeanUtils.copyProperties(productVO, productBO);
            productVOList = this.convert(productService.queryListByCondition(productBO));

        return productVOList;
    }

    /**
     * 集合转换对象
     * @param productBOs
     * @return
     */
    private List<ProductVO> convert(List<ProductBO> productBOs) {
        List<ProductVO> productVOList = null;
        if(CollectionUtils.isNotEmpty(productBOs)){
            productVOList = new ArrayList<>(productBOs.size());
            for(ProductBO productBO : productBOs){
                ProductVO productVO = new ProductVO();
                BeanUtils.copyProperties(productBO, productVO);

                if(productVO.getImageId() != null) {
                    ImageVO imageVO = imageBiz.getById(productVO.getImageId());
                    productVO.setImageVO(imageVO);
                }
                ActivityProductRefVO activityProductRefVO = new ActivityProductRefVO();
                activityProductRefVO.setProductId(productVO.getId());
                activityProductRefVO.setStatus(DataStatusEnum.NORMAL.getNum());
                List<ActivityProductRefVO> activityProductRefVOList = activityProductRefBiz.getByCondition(activityProductRefVO);
                if(CollectionUtils.isNotEmpty(activityProductRefVOList)){
                    ActivityVO activityVO = activityBiz.getById(activityProductRefVOList.get(0).getActivityId());
                    productVO.setActivityVO(activityVO);
                }
                productVOList.add(productVO);
            }
        }
        return productVOList;
    }

    /**
     * 根据Id查询
     * @param productId
     * @return
     */
    public ProductVO getById(Integer productId){
        ProductVO productVO = new ProductVO();
        ProductBO productBO = productService.selectById(productId);
        if(productBO != null){
            BeanUtils.copyProperties(productBO, productVO);
            if(productVO.getImageId() != null) {
                ImageVO imageVO = imageBiz.getById(productVO.getImageId());
                productVO.setImageVO(imageVO);
            }
            return productVO;
        }
        return null;
    }

    /**
     * 新增
     * @param productVO
     * @return
     */
    public boolean save(ProductVO productVO){
        ProductBO productBO = new ProductBO();
        BeanUtils.copyProperties(productVO, productBO);
        return productService.save(productBO) > 0 ? true : false;
    }

    /**
     * 根据id逻辑删除
     * @param productId
     * @return
     */
    public boolean deleteById(Integer productId){
        ProductBO productBO = productService.selectById(productId);
        if(productBO != null){
            productBO = new ProductBO();
            productBO.setId(productId);
            productBO.setStatus(DataStatusEnum.ABANDONED.getNum());
            return productService.updateByCondition(productBO) > 0 ? true : false;
        }
        return false;
    }

    /**
     * 根据条件更新
     * @param productVO
     * @return
     */
    public boolean updateByCondition(ProductVO productVO){
        ProductBO productBO = new ProductBO();
        BeanUtils.copyProperties(productVO, productBO);
        return productService.updateByCondition(productBO) > 0 ? true:false;
    }

}
