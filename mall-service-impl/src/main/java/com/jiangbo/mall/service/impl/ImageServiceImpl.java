package com.jiangbo.mall.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiangbo.mall.bo.CartBO;
import com.jiangbo.mall.bo.ImageBO;
import com.jiangbo.mall.dao.ImagePOMapper;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.pojo.CartPO;
import com.jiangbo.mall.pojo.ImagePO;
import com.jiangbo.mall.pojo.ImagePO;
import com.jiangbo.mall.service.ImageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jiangbo Cheng on 2017/10/18.
 */
@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImagePOMapper imagePOMapper;
    /**
     * 根据主键删除对象
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(Integer id) {
        ImagePO imagePO = imagePOMapper.selectByPrimaryKey(id);
        if(imagePO == null) {
            return 0;
        }
        imagePO = new ImagePO();
        imagePO.setId(id);
        imagePO.setStatus(DataStatusEnum.ABANDONED.getNum());
        return imagePOMapper.updateByPrimaryKey(imagePO);
    }

    /**
     * 插入对象
     *
     * @param record
     * @return
     */
    @Override
    public int save(ImageBO record) {
        if(record == null){
            return 0;
        }
        ImagePO imagePO = new ImagePO();
        BeanUtils.copyProperties(record, imagePO);
        Date date = new Date();
        imagePO.setCreateTime(date);
        imagePO.setUpdateTime(date);
        return imagePOMapper.insert(imagePO);
    }

    /**
     * 根据主键查询对象
     *
     * @param id
     * @return
     */
    @Override
    public ImageBO selectById(Integer id) {
        ImagePO imagePO = imagePOMapper.selectByPrimaryKey(id);
        if(imagePO == null){
            return null;
        }
        ImageBO imageBO = new ImageBO();
        BeanUtils.copyProperties(imagePO, imageBO);
        return imageBO;
    }

    /**
     * 根据参数更新对象
     *
     * @param record
     * @return
     */
    @Override
    public int updateByCondition(ImageBO record) {
        if(record == null){
            return 0;
        }
        ImagePO imagePO = new ImagePO();
        BeanUtils.copyProperties(record, imagePO);
        return imagePOMapper.updateByPrimaryKey(imagePO);
    }

    /**
     * 根据参数查询对象集合
     *
     * @param record
     * @return
     */
    @Override
    public List<ImageBO> queryListByCondition(ImageBO record) {
        if(record == null){
            record = new ImageBO();
        }
        List<ImageBO> imageBOList = null;
        try{
            ImagePO imagePO = new ImagePO();
            BeanUtils.copyProperties(record, imagePO);
            List<ImagePO> imagePOList = imagePOMapper.selectListByCondition(imagePO);
            imageBOList = this.convert(imagePOList);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }
        return imageBOList;
    }

    /**
     * 分页查询
     *
     * @param record
     * @param index
     * @param size
     * @return
     */
    @Override
    public PageInfo<ImageBO> queryListByCondition(ImageBO record, int index, int size) {
        PageInfo<ImageBO> pageInfo= new PageInfo<>();
        if(record == null){
            record = new ImageBO();
        }

        try{
            Page page = PageHelper.startPage(index, size, true);
            ImagePO imagePO = new ImagePO();
            BeanUtils.copyProperties(record, imagePO);
            List<ImagePO> imagePOList = imagePOMapper.selectListByCondition(imagePO);
            List<ImageBO> imageBOList = convert(imagePOList);

            pageInfo.setList(imageBOList);
            BeanUtils.copyProperties(page, pageInfo);
        }catch(Exception e){
            e.printStackTrace();
            throw new MallException(e);
        }

        return pageInfo;
    }

    private List<ImageBO> convert(List<ImagePO> imagePOList) {
        List<ImageBO> imageBOList = null;
        if(CollectionUtils.isNotEmpty(imagePOList)){
            imageBOList = new ArrayList<>(imagePOList.size());
            for(ImagePO ImagePO : imagePOList){
                ImageBO imageBO = new ImageBO();
                BeanUtils.copyProperties(ImagePO, imageBO);
                imageBOList.add(imageBO);
            }
        }

        return imageBOList;
    }
}
