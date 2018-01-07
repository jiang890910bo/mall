package com.jiangbo.mall.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ActivityProductRefPO implements Serializable {
    private Integer id;

    private Integer activityId;

    private Integer productId;

    private Integer status;

    private Integer stock;

    private BigDecimal seckillPrice;

    private BigDecimal price;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public ActivityProductRefPO(Integer id, Integer activityId, Integer productId, Integer status, Integer stock, BigDecimal seckillPrice, BigDecimal price, Date createTime, Date updateTime) {
        this.id = id;
        this.activityId = activityId;
        this.productId = productId;
        this.status = status;
        this.stock = stock;
        this.seckillPrice = seckillPrice;
        this.price = price;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public ActivityProductRefPO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(BigDecimal seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}