package com.isw.mall.dao;

import com.isw.mall.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface CategoryMapper {

  @Select("select * from mall_category where id = #{id}")
  Category findById(@Param("id") Integer id);

  Category queryById(@Param("id") Integer id);
}
