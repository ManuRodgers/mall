package com.isw.mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.isw.mall.MallApplicationTests;
import com.isw.mall.enums.ResponseEnum;
import com.isw.mall.service.IProductService;
import com.isw.mall.vo.ProductDetailVo;
import com.isw.mall.vo.ProductVo;
import com.isw.mall.vo.ResponseVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ProductServiceImplTest extends MallApplicationTests {
  @Autowired private IProductService productService;

  @Test
  void getProductList() {
    ResponseVo<PageInfo<ProductVo>> productList = productService.getProductList(null, 2, 3);
    Assertions.assertEquals(ResponseEnum.SUCCESS.getCode(), productList.getStatus());
  }

  @Test
  void getProductDetail() {
    ResponseVo<ProductDetailVo> productDetail = productService.getProductDetail(26);
    Assertions.assertEquals(ResponseEnum.SUCCESS.getCode(), productDetail.getStatus());
  }
}
