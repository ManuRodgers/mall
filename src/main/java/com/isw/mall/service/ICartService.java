package com.isw.mall.service;

import com.isw.mall.dto.CartAddProductDto;
import com.isw.mall.dto.CartUpdateProductDto;
import com.isw.mall.vo.CartVo;
import com.isw.mall.vo.ResponseVo;

public interface ICartService {

  ResponseVo<CartVo> add(Integer uid, CartAddProductDto cartAddProductDto);

  ResponseVo<CartVo> list(Integer uid);

  ResponseVo<CartVo> update(
      Integer uid, Integer productId, CartUpdateProductDto cartUpdateProductDto);

  ResponseVo<CartVo> delete(Integer uid, Integer productId);

  ResponseVo<CartVo> selectAll(Integer uid);

  ResponseVo<CartVo> unSelectAll(Integer uid);

  ResponseVo<Integer> sum(Integer uid);
}
