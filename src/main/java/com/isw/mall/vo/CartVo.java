package com.isw.mall.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// @JsonInclude(JsonInclude.Include.NON_NULL)
public class CartVo {
  private List<CartProductVo> cartProductVoList;

  private Boolean selectedAll;

  private BigDecimal cartTotalPrice;

  private Integer cartTotalQuantity;
}
