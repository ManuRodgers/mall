package com.isw.mall.service.impl;

import com.isw.mall.MallApplicationTests;
import com.isw.mall.enums.ResponseEnum;
import com.isw.mall.service.ICategoryService;
import com.isw.mall.vo.CategoryVo;
import com.isw.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class CategoryServiceImplTest extends MallApplicationTests {
  @Autowired private ICategoryService categoryService;

  @Test
  void selectAll() {
    ResponseVo<List<CategoryVo>> listResponseVo = categoryService.selectAll();
    Assertions.assertEquals(ResponseEnum.SUCCESS.getCode(), listResponseVo.getStatus());
  }

  @Test
  void findSubCategoryId() {
    Set<Integer> set = new HashSet<>();
    categoryService.findSubCategoryId(100001, set);
    log.info("set={}", set);
  }
}
