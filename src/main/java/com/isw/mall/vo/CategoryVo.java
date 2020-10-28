package com.isw.mall.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// @JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryVo {
  private Integer id;

  private Integer parentId;

  private String name;

  private Integer sortOrder;

  private List<CategoryVo> subCategories;
}
