package com.isw.mall.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.isw.mall.MallApplicationTests;
import com.isw.mall.dto.CartAddProductDto;
import com.isw.mall.dto.CartUpdateProductDto;
import com.isw.mall.enums.ResponseEnum;
import com.isw.mall.service.ICartService;
import com.isw.mall.vo.CartVo;
import com.isw.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
class CartServiceImplTest extends MallApplicationTests {
  @Autowired private ICartService cartService;
  private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

  private final Integer productId = 26;
  private final Integer uid = 1;

  @BeforeEach
  public void add() {
    ResponseVo<CartVo> responseVo =
        cartService.add(
            uid, CartAddProductDto.builder().productId(productId).selected(true).build());
    log.info("add cart product");
    log.info("list={}", gson.toJson(responseVo));
    Assertions.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
  }

  @Test
  public void list() {
    ResponseVo<CartVo> list = cartService.list(uid);
    log.info("list={}", gson.toJson(list));
    Assertions.assertEquals(ResponseEnum.SUCCESS.getCode(), list.getStatus());
  }

  @Test
  public void update() {
    ResponseVo<CartVo> responseVo =
        cartService.update(
            uid, 26, CartUpdateProductDto.builder().quantity(8).selected(false).build());
    log.info("list={}", gson.toJson(responseVo));
    Assertions.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
  }

  @AfterEach
  void delete() {
    log.info("delete cart product");
    ResponseVo<CartVo> responseVo = cartService.delete(uid, productId);
    log.info("list={}", gson.toJson(responseVo));
    Assertions.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
  }

  @Test
  void selectAll() {
    ResponseVo<CartVo> responseVo = cartService.selectAll(uid);
    log.info("list={}", gson.toJson(responseVo));
    Assertions.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
  }

  @Test
  void unSelectAll() {
    ResponseVo<CartVo> responseVo = cartService.unSelectAll(uid);
    log.info("list={}", gson.toJson(responseVo));
    Assertions.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
  }

  @Test
  void sum() {
    ResponseVo<Integer> sum = cartService.sum(uid);
    log.info("list={}", gson.toJson(sum));
    Assertions.assertEquals(ResponseEnum.SUCCESS.getCode(), sum.getStatus());
  }
}
