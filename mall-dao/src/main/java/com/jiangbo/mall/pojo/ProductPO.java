package com.jiangbo.mall.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductPO implements Serializable {


    private static final long serialVersionUID = 4990537580441612202L;
    private Integer id;

    private Integer categoryId;

    private String name;

    private String subTitle;

    private String detail;

    private BigDecimal seckillPrice;

    private BigDecimal price;

    private Integer stock;

    private Integer productStatus;

    private Integer imageId;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    public ProductPO() {
    }

    public ProductPO(Integer id, Integer categoryId, String name, String subTitle, String detail, BigDecimal seckillPrice, BigDecimal price, Integer stock, Integer productStatus, Integer imageId, Integer status, Date createTime, Date updateTime) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.subTitle = subTitle;
        this.detail = detail;
        this.seckillPrice = seckillPrice;
        this.price = price;
        this.stock = stock;
        this.productStatus = productStatus;
        this.imageId = imageId;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
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