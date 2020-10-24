package com.isw.mall.service.impl;

import com.isw.mall.MallApplicationTests;
import com.isw.mall.enums.ResponseEnum;
import com.isw.mall.enums.RoleEnum;
import com.isw.mall.pojo.User;
import com.isw.mall.service.IUserService;
import com.isw.mall.vo.ResponseVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

// unit test only testing for functionality
// do not actually pollute the database
@Transactional
class UserServiceImplTest extends MallApplicationTests {

  public static final String USERNAME = "manu";
  public static final String PASSWORD = "123456";

  @Autowired private IUserService userService;

  @Test
  void register() {
    User manu =
        User.builder()
            .username(USERNAME)
            .password(PASSWORD)
            .email("manu@qq.com")
            .role(RoleEnum.ADMIN.getCode())
            .build();
    userService.register(manu);
  }

  @Test
  void login() {
    ResponseVo<User> userResponseVo = userService.login(USERNAME, PASSWORD);
    Assertions.assertEquals(ResponseEnum.SUCCESS.getCode(), userResponseVo.getStatus());
  }
}
