package com.isw.mall.exception;

import com.isw.mall.enums.ResponseEnum;
import com.isw.mall.vo.ResponseVo;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RuntimeExceptionHandler<T> {
  @ExceptionHandler(RuntimeException.class)
  @ResponseBody
  public ResponseVo<T> handleRuntimeException(RuntimeException runtimeException) {
    return ResponseVo.error(ResponseEnum.ERROR, runtimeException.getMessage());
  }

  @ExceptionHandler(UserLoginException.class)
  @ResponseBody
  public ResponseVo<T> handleUserLoginException() {
    return ResponseVo.error(ResponseEnum.NEED_LOGIN);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public ResponseVo<T> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    BindingResult bindingResult = e.getBindingResult();

    return ResponseVo.error(ResponseEnum.NEED_LOGIN, bindingResult);
  }
}
