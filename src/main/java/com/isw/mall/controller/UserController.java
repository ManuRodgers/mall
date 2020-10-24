package com.isw.mall.controller;

import com.isw.mall.consts.MallConst;
import com.isw.mall.dto.UserLoginDto;
import com.isw.mall.dto.UserRegisterDto;
import com.isw.mall.enums.ResponseEnum;
import com.isw.mall.enums.RoleEnum;
import com.isw.mall.pojo.User;
import com.isw.mall.service.IUserService;
import com.isw.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

@RestController
@Slf4j
public class UserController {
  @Autowired private IUserService userService;

  @PostMapping("/user/register")
  public ResponseVo<User> register(
      @Valid @RequestBody UserRegisterDto userRegisterDto, BindingResult bindingResult) {

    if (handleBindingResult(bindingResult) != null) {
      return handleBindingResult(bindingResult);
    }
    User user = User.builder().role(RoleEnum.CUSTOMER.getCode()).build();
    BeanUtils.copyProperties(userRegisterDto, user);
    return userService.register(user);
  }

  @PostMapping("/user/login")
  public ResponseVo<User> login(
      @Valid @RequestBody UserLoginDto userLoginDto,
      BindingResult bindingResult,
      HttpSession session) {
    if (handleBindingResult(bindingResult) != null) {
      return handleBindingResult(bindingResult);
    }
    ResponseVo<User> userResponseVo =
        userService.login(userLoginDto.getUsername(), userLoginDto.getPassword());
    //    login success and write user info session
    session.setAttribute(MallConst.CURRENT_USER, userResponseVo.getData());
    log.info("/login sessionId={}", session.getId());

    return userResponseVo;
  }

  @PostMapping("/user/logout")
  public ResponseVo<User> logout(HttpSession session) {
    log.info("/user/logout sessionId={}", session.getId());
    session.removeAttribute(MallConst.CURRENT_USER);
    return ResponseVo.success();
  }

  //  session is saved in internal storage, which will lost once Java server is restarted
  @GetMapping("/user")
  public ResponseVo<User> userInfo(HttpSession session) {
    log.info("/user sessionId={}", session.getId());
    //    get user info from session
    User user = (User) session.getAttribute(MallConst.CURRENT_USER);
    return ResponseVo.success(user);
  }

  private ResponseVo<User> handleBindingResult(BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      log.error(
          "注册提交的参数有误, {} {}",
          Objects.requireNonNull(bindingResult.getFieldError()).getField(),
          Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
      return ResponseVo.error(ResponseEnum.PARAM_ERROR);
    }
    return null;
  }
}
