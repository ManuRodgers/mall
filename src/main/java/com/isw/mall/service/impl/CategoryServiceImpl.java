package com.isw.mall.service.impl;

import com.isw.mall.consts.MallConst;
import com.isw.mall.dao.CategoryMapper;
import com.isw.mall.pojo.Category;
import com.isw.mall.service.ICategoryService;
import com.isw.mall.vo.CategoryVo;
import com.isw.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService {
  @Autowired private CategoryMapper categoryMapper;

  @Override
  public ResponseVo<List<CategoryVo>> selectAll() {
    List<Category> categories = categoryMapper.selectAll();
    //    lambda + stream
    List<CategoryVo> categoryVoList =
        categories.stream()
            .filter(category -> category.getParentId().equals(MallConst.ROOT_PARENT_ID))
            .map(this::category2CategoryVo)
            .sorted(Comparator.comparing(CategoryVo::getSortOrder).reversed())
            .collect(Collectors.toList());
    //    query for subCategory

    findSubCategory(categoryVoList, categories);
    return ResponseVo.success(categoryVoList);
  }

  @Override
  public void findSubCategoryId(Integer id, Set<Integer> resultSet) {
    List<Category> categories = categoryMapper.selectAll();
    findSubCategoryId(id, resultSet, categories);
  }

  private void findSubCategoryId(Integer id, Set<Integer> resultSet, List<Category> categories) {
    for (Category category : categories) {
      if (category.getParentId().equals(id)) {
        // these categories are the subcategory of this category with specified id
        Integer subCategoryId = category.getId();
        resultSet.add(subCategoryId);
        findSubCategoryId(subCategoryId, resultSet, categories);
      }
    }
  }

  /**
   * time consuming: http(wechat pay api) > disk(mysql internal internet + disk ) > internal
   * storage(java). So within for loop, don't make http requests or DB queries
   *
   * @param categoryVoList
   * @param categories
   */
  private void findSubCategory(List<CategoryVo> categoryVoList, List<Category> categories) {

    for (CategoryVo categoryVo : categoryVoList) {
      List<CategoryVo> subCategoryVoList = new ArrayList<>();
      for (Category category : categories) {
        // if we can find subCategoryVo then we add it to subCategoryVoList and
        // continue to query for sub
        if (categoryVo.getId().equals(category.getParentId())) {
          CategoryVo subCategoryVo = category2CategoryVo(category);
          subCategoryVoList.add(subCategoryVo);
        }
        subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
        categoryVo.setSubCategories(subCategoryVoList);
        findSubCategory(subCategoryVoList, categories);
      }
    }
  }

  private CategoryVo category2CategoryVo(Category category) {
    CategoryVo categoryVo = CategoryVo.builder().build();
    BeanUtils.copyProperties(category, categoryVo);
    return categoryVo;
  }
}
