package com.isw.mall.controller;

import com.isw.mall.service.ICategoryService;
import com.isw.mall.vo.CategoryVo;
import com.isw.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
  @Autowired private ICategoryService categoryService;

  @GetMapping("/categories")
  public ResponseVo<List<CategoryVo>> selectAll() {
    return categoryService.selectAll();
  }
}
