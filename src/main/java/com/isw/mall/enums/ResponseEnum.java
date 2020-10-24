package com.isw.mall.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {
  ERROR(-1, "server error"),
  SUCCESS(0, "success"),
  PASSWORD_ERROR(1, "password error"),
  USERNAME_EXIST(2, "username already exists"),
  PARAM_ERROR(3, "parameter error"),
  EMAIL_EXIST(4, "email already exists"),
  NEED_LOGIN(10, "user have not logged in, Please login"),
  USERNAME_OR_PASSWORD_ERROR(11, "username or password error"),
  ;
  Integer code;
  String description;
}
