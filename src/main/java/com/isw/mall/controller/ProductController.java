package com.isw.mall.controller;

import com.github.pagehelper.PageInfo;
import com.isw.mall.service.IProductService;
import com.isw.mall.vo.ProductDetailVo;
import com.isw.mall.vo.ProductVo;
import com.isw.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ProductController {

  @Autowired private IProductService productService;

  @GetMapping("/products")
  public ResponseVo<PageInfo<ProductVo>> getProductsByCategoryId(
      @RequestParam(required = false) Integer categoryId,
      @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
      @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
    return productService.getProductList(categoryId, pageNumber, pageSize);
  }

  @GetMapping("/products/{productId}")
  public ResponseVo<ProductDetailVo> getProductsByCategoryId(@PathVariable Integer productId) {
    return productService.getProductDetail(productId);
  }
}
