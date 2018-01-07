package com.jiangbo.mall.service;

import com.jiangbo.mall.bo.ProductBO;

/**
 * @author Jiangbo Cheng on 2017/10/18.
 */

public interface ProductService extends AbstractService<ProductBO> {

    /**
     * 减库存
     * @param productId
     * @param num
     * @return
     */
    boolean subtractStock(Integer productId, int num);
}
