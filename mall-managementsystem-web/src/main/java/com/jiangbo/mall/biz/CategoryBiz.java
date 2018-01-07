package com.jiangbo.mall.biz;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiangbo.mall.bo.ActivityBO;
import com.jiangbo.mall.bo.CategoryBO;
import com.jiangbo.mall.enums.DataStatusEnum;
import com.jiangbo.mall.exception.MallException;
import com.jiangbo.mall.service.CategoryService;
import com.jiangbo.mall.vo.ActivityVO;
import com.jiangbo.mall.vo.CategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品目录
 * @author Jiangbo Cheng on 2017/10/24.
 */
@Component
public class CategoryBiz {
    @Autowired
    CategoryService categoryService;

    /**
     * 根据条件获得目录集合
     * @param categoryVO
     * @return
     */
    public PageInfo<CategoryVO> getCategorysByCondition(CategoryVO categoryVO, int pageIndex, int pageSize) throws MallException {
        try{
            CategoryBO categoryBO = new CategoryBO();
            BeanUtils.copyProperties(categoryVO, categoryBO);
            PageInfo<CategoryBO> categoryBOPageInfo = categoryService.queryListByCondition(categoryBO, pageIndex, pageSize);

            return convert(categoryBOPageInfo);
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }
    }

    /**
     * 根据条件获得目录集合
     * @param categoryVO
     * @return
     */
    public List<CategoryVO> getCategorysByCondition(CategoryVO categoryVO) throws MallException {
        try{
            if(categoryVO == null){
                categoryVO = new CategoryVO();
            }

            CategoryBO categoryBO = new CategoryBO();
            BeanUtils.copyProperties(categoryVO, categoryBO);
            List<CategoryBO> categoryBOList = categoryService.queryListByCondition(categoryBO);

            return convert(categoryBOList);
        }catch(Exception e){
            throw new MallException(e.getMessage());
        }
    }

    private PageInfo<CategoryVO> convert(PageInfo<CategoryBO> categoryBOPageInfo){
        PageInfo<CategoryVO> pageInfo = new PageInfo<>();
        List<CategoryVO> categoryVOList;
        if(CollectionUtils.isNotEmpty(categoryBOPageInfo.getList())){
            categoryVOList = new ArrayList<>(categoryBOPageInfo.getList().size());
            for(CategoryBO categoryBO : categoryBOPageInfo.getList()){
                CategoryVO categoryVO = new CategoryVO();
                BeanUtils.copyProperties(categoryBO, categoryVO);
                categoryVOList.add(categoryVO);
            }
            BeanUtils.copyProperties(categoryBOPageInfo, pageInfo);
            pageInfo.setList(categoryVOList);
        }

        return pageInfo;
    }

    /**
     * 集合转换对象
     * @param categoryBOs
     * @return
     */
    private List<CategoryVO> convert(List<CategoryBO> categoryBOs) {
        List<CategoryVO> categoryVOList = null;
        if(CollectionUtils.isNotEmpty(categoryBOs)){
            categoryVOList = new ArrayList<>(categoryBOs.size());
            for(CategoryBO categoryBO : categoryBOs){
                StringBuffer nameStr = new StringBuffer(categoryBO.getName());
                appendName(categoryBO,nameStr);
                categoryBO.setName(nameStr.toString());

                CategoryVO categoryVO = new CategoryVO();
                BeanUtils.copyProperties(categoryBO, categoryVO);
                categoryVOList.add(categoryVO);
            }
        }
        return categoryVOList;
    }

    /**
     * 查询出父节点名称
     * @param categoryBO
     * @param nameStr
     */
    private void appendName(CategoryBO categoryBO, StringBuffer nameStr){
        if(categoryBO.getParentId().intValue() > 0) {
            CategoryBO tempBO = categoryService.selectById(categoryBO.getParentId());
            nameStr.append("<-").append(tempBO.getName());
            appendName(tempBO, nameStr);
        }
    }

    /**
     * 根据Id查询目录
     * @param categoryId
     * @return
     */
    public CategoryVO getById(Integer categoryId){
        CategoryVO categoryVO = new CategoryVO();
        CategoryBO categoryBO = categoryService.selectById(categoryId);
        if(categoryBO != null){
            BeanUtils.copyProperties(categoryBO, categoryVO);
            if(categoryVO.getParentId().intValue() > 0){
                CategoryBO temp = categoryService.selectById(categoryVO.getParentId());
                if(temp != null) {
                    categoryVO.setParent(temp.getName());
                }
            }
            return categoryVO;
        }
        return null;
    }

    /**
     * 新增目录
     * @param categoryVO
     * @return
     */
    public boolean save(CategoryVO categoryVO){
        CategoryBO categoryBO = new CategoryBO();
        BeanUtils.copyProperties(categoryVO, categoryBO);
        categoryBO.setStatus(DataStatusEnum.NORMAL.getNum());
        return categoryService.save(categoryBO) > 0 ? true : false;
    }

    /**
     * 根据id逻辑删除
     * @param categoryId
     * @return
     */
    public boolean deleteById(Integer categoryId){
        CategoryBO categoryBO = categoryService.selectById(categoryId);
        if(categoryBO != null){
            categoryBO = new CategoryBO();
            categoryBO.setId(categoryId);
            categoryBO.setStatus(DataStatusEnum.ABANDONED.getNum());
            return categoryService.updateByCondition(categoryBO) > 0 ? true : false;
        }
        return false;
    }

    /**
     * 根据条件更新目录
     * @param categoryVO
     * @return
     */
    public boolean updateByCondition(CategoryVO categoryVO){
        CategoryBO categoryBO = new CategoryBO();
        BeanUtils.copyProperties(categoryVO, categoryBO);
        return categoryService.updateByCondition(categoryBO) > 0 ? true:false;
    }

    /**
     * 查询所有有效的目录
     * @return
     */
    public List<CategoryVO> getAllValid() {
        List<CategoryBO> categoryBOList = categoryService.queryListByCondition(null);
        return this.convert(categoryBOList);
    }
}
