package com.jiangbo.mall.dao;

import com.jiangbo.mall.pojo.ProductPO;
import org.apache.ibatis.annotations.Param;

public interface ProductPOMapper  extends AbstractPOMapper<ProductPO>{

    /**
     * 减库存
     * @param productId
     * @param num
     * @return
     */
    boolean subtractStock(@Param("productId") Integer productId, @Param("num") int num);
}