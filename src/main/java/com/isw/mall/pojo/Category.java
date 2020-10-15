package com.isw.mall.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// POJO Plain old Java
// PO Persistent Object
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
  private Integer id;
  private Integer parentId;
  private String name;
  private Integer status;
  private Integer sortOrder;
  private Date createTime;
  private Date updateTime;
}
