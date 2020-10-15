package com.isw.mall.dao;

import com.isw.mall.MallApplicationTests;
import com.isw.mall.pojo.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CategoryMapperTest extends MallApplicationTests {
  @Autowired private CategoryMapper categoryMapper;

  @Test
  void testFindById() {
    Category category = categoryMapper.findById(100001);
    System.out.println(category.toString());
  }

  @Test
  void testQueryById() {
    Category category = categoryMapper.queryById(100001);
    System.out.println(category.toString());
  }
}
