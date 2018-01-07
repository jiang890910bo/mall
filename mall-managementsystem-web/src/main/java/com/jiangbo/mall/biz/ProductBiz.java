package com.jiangbo.mall.biz;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiangbo.mall.bo.ActivityBO;
import com.jiangbo.mall.bo.ProductBO;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.enums.ProductStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.service.ProductService;
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

    /**
     * 根据条件获得集合
     * @param productVO
     * @return
     */
    public List<ProductVO> getProductsByCondition(ProductVO productVO){
        List<ProductVO> productVOList;
        try{
            ProductBO productBO = new ProductBO();
            BeanUtils.copyProperties(productVO, productBO);
            productVOList = this.convert(productService.queryListByCondition(productBO));
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }
        return productVOList;
    }

    /**
     * 根据条件分页获得集合
     * @param productVO
     * @return
     */
    public PageInfo<ProductVO> getProductsByCondition(ProductVO productVO, int pageIndex, int pageSize){
        try{

            ProductBO productBO = new ProductBO();
            BeanUtils.copyProperties(productVO, productBO);
            PageInfo<ProductBO> productBOPageInfo = productService.queryListByCondition(productBO, pageIndex, pageSize);
            return this.convert(productBOPageInfo);
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }
    }

    private PageInfo<ProductVO> convert(PageInfo<ProductBO> productBOPageInfo){
        PageInfo<ProductVO> pageInfo = new PageInfo<>();
        List<ProductVO> productVOList;
        if(CollectionUtils.isNotEmpty(productBOPageInfo.getList())){
            productVOList = new ArrayList<>(productBOPageInfo.getList().size());
            for(ProductBO productBO : productBOPageInfo.getList()){
                ProductVO productVO = new ProductVO();
                BeanUtils.copyProperties(productBO, productVO);

                ImageVO imageVO = imageBiz.getById(productVO.getImageId());
                productVO.setMainImage(imageVO.getMainImageUrl());
                productVOList.add(productVO);
            }
            BeanUtils.copyProperties(productBOPageInfo, pageInfo);
            pageInfo.setList(productVOList);
        }

        return pageInfo;
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
        productBO.setStatus(DataStatusEnum.NORMAL.getNum());
        productBO.setProductStatus(ProductStatusEnum.WATING_SALE.getNum());
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
