package com.isw.mall.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
  ADMIN(0),
  CUSTOMER(1),
  ;
  Integer code;
}
