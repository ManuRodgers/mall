package com.isw.mall.service.impl;

import com.isw.mall.dao.UserMapper;
import com.isw.mall.enums.ResponseEnum;
import com.isw.mall.pojo.User;
import com.isw.mall.service.IUserService;
import com.isw.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

  @Autowired private UserMapper userMapper;

  /**
   * @param user The user
   * @author Manu Rodgers
   */
  @Override
  public ResponseVo<User> register(User user) {
    //    runtimeException();
    // username cannot be duplicate
    int countByUsername = userMapper.countByUsername(user.getUsername());
    if (countByUsername > 0) {
      return ResponseVo.error(ResponseEnum.USERNAME_EXIST);
    }
    // email cannot be duplicate
    int countByEmail = userMapper.countByEmail(user.getEmail());
    if (countByEmail > 0) {
      return ResponseVo.error(ResponseEnum.EMAIL_EXIST);
    }
    // MD5 digest algorithm and set password
    user.setPassword(
        DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));
    //  insert user into database
    final int resultCount = userMapper.insertSelective(user);
    if (resultCount == 0) {
      return ResponseVo.error(ResponseEnum.ERROR);
    }
    return ResponseVo.success();
  }

  @Override
  public ResponseVo<User> login(String username, String password) {
    User user = userMapper.selectByUsername(username);

    if (user == null) {
      //     no available username
      return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
    }

    if (!user.getPassword()
        .equalsIgnoreCase(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))) {
      log.error("incorrect password");
      return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
    }
    user.setPassword("");
    return ResponseVo.success(user);
  }

  private void runtimeException() {
    throw new RuntimeException("accident error");
  }
}
