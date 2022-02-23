package com.flame.order.mapper;

import com.flame.order.module.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author king
 * @date 2022-02-10 09:20:02
 */
@Mapper
public interface UserMapper  {

 int add(User user);
}
