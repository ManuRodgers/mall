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
  PRODUCT_OFF_SALE_OR_DELETE(12, "product unavailable"),
  PRODUCT_NOT_EXIST(13, "product is not exist"),
  PRODUCT_STOCK_NOT_EXIST(14, "product stock is not exist"),
  CART_PRODUCT_NOT_EXIST(15, "product is not exist within selected cart"),
  ;
  Integer code;
  String description;
}
