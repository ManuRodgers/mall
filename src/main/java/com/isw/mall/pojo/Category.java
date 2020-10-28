package com.isw.mall.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
  private Integer id;

  private Integer parentId;

  private String name;

  private Boolean status;

  private Integer sortOrder;

  private Date createTime;

  private Date updateTime;
}
