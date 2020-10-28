package com.isw.mall.service;

import com.github.pagehelper.PageInfo;
import com.isw.mall.vo.ProductDetailVo;
import com.isw.mall.vo.ProductVo;
import com.isw.mall.vo.ResponseVo;

public interface IProductService {

  ResponseVo<PageInfo<ProductVo>> getProductList(
      Integer categoryId, Integer pageNumber, Integer pageSize);

  ResponseVo<ProductDetailVo> getProductDetail(Integer productId);
}
