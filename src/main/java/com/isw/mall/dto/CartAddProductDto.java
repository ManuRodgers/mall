package com.isw.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartAddProductDto {

  //  @NotEmpty for array list set
  //  @NotBlank for string
  //  @NotNull can be null
  @NotNull private Integer productId;

  private Boolean selected = true;
}
