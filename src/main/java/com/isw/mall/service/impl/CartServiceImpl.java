package com.isw.mall.service.impl;

import com.google.gson.Gson;
import com.isw.mall.dao.ProductMapper;
import com.isw.mall.dto.CartAddProductDto;
import com.isw.mall.dto.CartUpdateProductDto;
import com.isw.mall.enums.ProductDetailEnum;
import com.isw.mall.enums.ResponseEnum;
import com.isw.mall.pojo.Cart;
import com.isw.mall.pojo.Product;
import com.isw.mall.service.ICartService;
import com.isw.mall.vo.CartProductVo;
import com.isw.mall.vo.CartVo;
import com.isw.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CartServiceImpl implements ICartService {
  private static final String CART_REDIS_KEY_TEMPLATE = "cart_%d";
  @Autowired private ProductMapper productMapper;
  @Autowired private StringRedisTemplate redisTemplate;
  private final Gson gson = new Gson();

  @Override
  public ResponseVo<CartVo> add(Integer uid, CartAddProductDto cartAddProductDto) {
    Integer quantity = 1;
    Product product = productMapper.selectByPrimaryKey(cartAddProductDto.getProductId());
    //    商品是否存在
    if (product == null) {
      return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
    }
    //    商品是否是在售状态
    if (!product.getStatus().equals(ProductDetailEnum.ON_SALE.getCode())) {
      return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
    }
    //    商品的库存是否充足
    if (product.getStock() <= 0) {
      return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_NOT_EXIST);
    }
    HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
    String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
    String value = opsForHash.get(redisKey, String.valueOf(product.getId()));
    Cart cart = null;
    if (StringUtils.isEmpty(value)) {
      //      without this product so add product
      cart =
          Cart.builder()
              .quantity(quantity)
              .productId(product.getId())
              .productSelected(cartAddProductDto.getSelected())
              .build();
    } else {
      //      with this product so add quantity 1
      cart = gson.fromJson(value, Cart.class);
      cart.setQuantity(cart.getQuantity() + quantity);
    }
    opsForHash.put(redisKey, String.valueOf(product.getId()), gson.toJson(cart));
    return list(uid);
  }

  @Override
  public ResponseVo<CartVo> list(Integer uid) {
    HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
    String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
    Map<String, String> entries = opsForHash.entries(redisKey);
    CartVo cartVo = CartVo.builder().build();
    List<CartProductVo> cartProductVoList = new ArrayList<>();
    Boolean selectedAll = true;
    BigDecimal cartTotalPrice = BigDecimal.ZERO;
    Integer cartTotalQuantity = 0;
    for (Map.Entry<String, String> entry : entries.entrySet()) {
      Integer productId = Integer.valueOf(entry.getKey());
      Cart cart = gson.fromJson(entry.getValue(), Cart.class);
      // TODO: need to optimize this because we should not have access to database within for loop
      Product product = productMapper.selectByPrimaryKey(productId);
      if (product != null) {
        CartProductVo cartProductVo =
            CartProductVo.builder()
                .productId(productId)
                .productName(product.getName())
                .productMainImage(product.getMainImage())
                .productPrice(product.getPrice())
                .productSelected(cart.getProductSelected())
                .quantity(cart.getQuantity())
                .productStatus(product.getStatus())
                .productStock(product.getStock())
                .productSubtitle(product.getSubtitle())
                .productTotalPrice(
                    product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())))
                .build();
        cartProductVoList.add(cartProductVo);
        if (!cart.getProductSelected()) {
          selectedAll = false;
        }
        cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
      }
      cartTotalQuantity += cart.getQuantity();
    }
    cartVo.setCartProductVoList(cartProductVoList);
    cartVo.setCartTotalPrice(cartTotalPrice);
    cartVo.setCartTotalQuantity(cartTotalQuantity);
    cartVo.setSelectedAll(selectedAll);
    return ResponseVo.success(cartVo);
  }

  @Override
  public ResponseVo<CartVo> update(
      Integer uid, Integer productId, CartUpdateProductDto cartUpdateProductDto) {
    HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
    String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
    String value = opsForHash.get(redisKey, String.valueOf(productId));
    if (StringUtils.isEmpty(value)) {
      return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
    }
    Cart cart = gson.fromJson(value, Cart.class);
    if (cartUpdateProductDto.getQuantity() != null && cartUpdateProductDto.getQuantity() >= 0) {
      cart.setQuantity(cartUpdateProductDto.getQuantity());
    }
    if (cartUpdateProductDto.getSelected() != null) {
      cart.setProductSelected(cartUpdateProductDto.getSelected());
    }
    opsForHash.put(redisKey, String.valueOf(productId), gson.toJson(cart));

    return list(uid);
  }

  @Override
  public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
    HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
    String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
    String value = opsForHash.get(redisKey, String.valueOf(productId));
    if (StringUtils.isEmpty(value)) {
      return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
    }
    opsForHash.delete(redisKey, String.valueOf(productId));

    return list(uid);
  }

  @Override
  public ResponseVo<CartVo> selectAll(Integer uid) {
    String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
    HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
    List<Cart> cartList = getCartList(uid);
    for (Cart cart : cartList) {
      cart.setProductSelected(true);
      opsForHash.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
    }
    return list(uid);
  }

  @Override
  public ResponseVo<CartVo> unSelectAll(Integer uid) {
    HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
    String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
    List<Cart> cartList = getCartList(uid);
    for (Cart cart : cartList) {
      cart.setProductSelected(false);
      opsForHash.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
    }
    return list(uid);
  }

  @Override
  public ResponseVo<Integer> sum(Integer uid) {
    Integer sum = getCartList(uid).stream().map(Cart::getQuantity).reduce(0, Integer::sum);
    return ResponseVo.success(sum);
  }

  private List<Cart> getCartList(Integer uid) {
    List<Cart> cartList = new ArrayList<>();
    String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
    HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
    Map<String, String> entries = opsForHash.entries(redisKey);
    for (Map.Entry<String, String> entry : entries.entrySet()) {
      cartList.add(gson.fromJson(entry.getValue(), Cart.class));
    }
    return cartList;
  }
}
