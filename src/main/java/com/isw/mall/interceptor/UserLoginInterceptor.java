package com.isw.mall.interceptor;

import com.isw.mall.consts.MallConst;
import com.isw.mall.exception.UserLoginException;
import com.isw.mall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

  /**
   * @param request
   * @param response
   * @param handler
   * @return boolean
   * @throws UserLoginException
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws UserLoginException {
    log.info("preHandle");
    User user = (User) request.getSession().getAttribute(MallConst.CURRENT_USER);
    if (user == null) {
      log.info("user==null");
      throw new UserLoginException();
    }
    return true;
  }
}
