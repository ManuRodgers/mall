package com.isw.mall.controller;

import com.isw.mall.consts.MallConst;
import com.isw.mall.dto.CartAddProductDto;
import com.isw.mall.dto.CartUpdateProductDto;
import com.isw.mall.pojo.User;
import com.isw.mall.service.ICartService;
import com.isw.mall.vo.CartVo;
import com.isw.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@Slf4j
public class CartController {

  @Autowired private ICartService cartService;

  @GetMapping("/carts")
  public ResponseVo<CartVo> list(HttpSession session) {
    User user = (User) session.getAttribute(MallConst.CURRENT_USER);
    return cartService.list(user.getId());
  }

  @PostMapping("/carts")
  public ResponseVo<CartVo> cartAddProduct(
      @Valid @RequestBody CartAddProductDto cartAddProductDto, HttpSession session) {
    User user = (User) session.getAttribute(MallConst.CURRENT_USER);
    return cartService.add(user.getId(), cartAddProductDto);
  }

  @PutMapping("/carts/{productId}")
  public ResponseVo<CartVo> update(
      @PathVariable Integer productId,
      @Valid @RequestBody CartUpdateProductDto cartUpdateProductDto,
      HttpSession session) {
    User user = (User) session.getAttribute(MallConst.CURRENT_USER);
    return cartService.update(user.getId(), productId, cartUpdateProductDto);
  }

  @DeleteMapping("/carts/{productId}")
  public ResponseVo<CartVo> delete(@PathVariable Integer productId, HttpSession session) {
    User user = (User) session.getAttribute(MallConst.CURRENT_USER);
    return cartService.delete(user.getId(), productId);
  }

  @PutMapping("/carts/selectAll")
  public ResponseVo<CartVo> selectAll(HttpSession session) {
    User user = (User) session.getAttribute(MallConst.CURRENT_USER);
    return cartService.selectAll(user.getId());
  }

  @PutMapping("/carts/unSelectAll")
  public ResponseVo<CartVo> unSelectAll(HttpSession session) {
    User user = (User) session.getAttribute(MallConst.CURRENT_USER);
    return cartService.unSelectAll(user.getId());
  }

  @GetMapping("/carts/products/sum")
  public ResponseVo<Integer> sum(HttpSession session) {
    User user = (User) session.getAttribute(MallConst.CURRENT_USER);
    return cartService.sum(user.getId());
  }
}
