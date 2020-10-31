package com.isw.mall.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// @JsonInclude(JsonInclude.Include.NON_NULL)
public class CartProductVo {
  private Integer productId;

  private Integer quantity;

  private String productName;

  private String productSubtitle;

  private String productMainImage;

  private BigDecimal productPrice;

  private Integer productStatus;

  private BigDecimal productTotalPrice;

  private Integer productStock;

  private Boolean productSelected;
}
