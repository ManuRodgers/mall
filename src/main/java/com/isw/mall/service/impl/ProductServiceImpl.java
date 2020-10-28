package com.isw.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isw.mall.dao.ProductMapper;
import com.isw.mall.enums.ProductDetailEnum;
import com.isw.mall.enums.ResponseEnum;
import com.isw.mall.pojo.Product;
import com.isw.mall.service.ICategoryService;
import com.isw.mall.service.IProductService;
import com.isw.mall.vo.ProductDetailVo;
import com.isw.mall.vo.ProductVo;
import com.isw.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements IProductService {
  @Autowired private ProductMapper productMapper;
  @Autowired private ICategoryService categoryService;

  @Override
  public ResponseVo<PageInfo<ProductVo>> getProductList(
      Integer categoryId, Integer pageNumber, Integer pageSize) {
    Set<Integer> categoryIdSet = new HashSet<>();
    if (categoryId != null) {
      categoryService.findSubCategoryId(categoryId, categoryIdSet);
      categoryIdSet.add(categoryId);
    }
    PageHelper.startPage(pageNumber, pageSize);
    List<Product> productList = productMapper.selectByCategoryIdSet(categoryIdSet);
    List<ProductVo> productVoList =
        productList.stream().map(this::product2ProductVo).collect(Collectors.toList());
    PageInfo<ProductVo> pageInfo = new PageInfo<>(productVoList);
    pageInfo.setList(productVoList);
    return ResponseVo.success(pageInfo);
  }

  @Override
  public ResponseVo<ProductDetailVo> getProductDetail(Integer productId) {
    Product product = productMapper.selectByPrimaryKey(productId);
    if (product.getStatus().equals(ProductDetailEnum.OFF_SALE.getCode())
        || product.getStatus().equals(ProductDetailEnum.DELETE.getCode())) {
      return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
    }

    ProductDetailVo productDetailVo = ProductDetailVo.builder().build();
    BeanUtils.copyProperties(product, productDetailVo);
    //    handle sensitive properties
    productDetailVo.setStatus(productDetailVo.getStock() > 100 ? 100 : productDetailVo.getStock());
    return ResponseVo.success(productDetailVo);
  }

  private ProductVo product2ProductVo(Product product) {
    ProductVo productVo = new ProductVo();
    BeanUtils.copyProperties(product, productVo);
    return productVo;
  }
}
