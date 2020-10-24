package com.isw.mall.service;

import com.isw.mall.pojo.User;
import com.isw.mall.vo.ResponseVo;

public interface IUserService {
  /**
   * @author Manu Rodgers
   * @param user The user
   */
  ResponseVo<User> register(User user);

  ResponseVo<User> login(String username, String password);
}
