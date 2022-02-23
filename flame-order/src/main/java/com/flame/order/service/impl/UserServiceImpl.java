package com.flame.order.service.impl;

import com.flame.order.mapper.UserMapper;
import com.flame.order.module.pojo.User;
import com.flame.order.service.UserService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author king
 * @Description:
 * @date 2022-02-10 09:20:02
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @GlobalTransactional
    @Override
    public boolean addUser(User user) {
//        System.out.println("事务id:" + RootContext.getXID());
        userMapper.add(user);
        return true;
    }
}