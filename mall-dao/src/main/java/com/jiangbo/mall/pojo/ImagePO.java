package com.jiangbo.mall.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 图片类
 */
public class ImagePO implements Serializable {
    private Integer id;

    private String name;

    private String mainImageUrl;

    private String subImageUrl;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public ImagePO(Integer id, String name, String mainImageUrl, String subImageUrl, Integer status, Date createTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.mainImageUrl = mainImageUrl;
        this.subImageUrl = subImageUrl;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public ImagePO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl == null ? null : mainImageUrl.trim();
    }

    public String getSubImageUrl() {
        return subImageUrl;
    }

    public void setSubImageUrl(String subImageUrl) {
        this.subImageUrl = subImageUrl == null ? null : subImageUrl.trim();
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