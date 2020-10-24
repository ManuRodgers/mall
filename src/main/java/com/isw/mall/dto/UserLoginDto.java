package com.isw.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginDto {

  //  @NotEmpty for array list
  //  @NotBlank for string
  //  @NotNull can be null
  @NotBlank private String username;

  @NotBlank private String password;
}
