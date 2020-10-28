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
public class ProductVo {
  private Integer id;

  private Integer categoryId;

  private String name;

  private String subtitle;

  private String mainImage;

  private BigDecimal price;

  private Integer status;
}
