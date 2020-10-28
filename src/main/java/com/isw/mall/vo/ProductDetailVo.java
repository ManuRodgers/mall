package com.isw.mall.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailVo {
  private Integer id;

  private Integer categoryId;

  private String name;

  private String subtitle;

  private String mainImage;

  private String subImages;

  private String detail;

  private BigDecimal price;

  private Integer stock;

  private Integer status;

  private Date createTime;

  private Date updateTime;
}
