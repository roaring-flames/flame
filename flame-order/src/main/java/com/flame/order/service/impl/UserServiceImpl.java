package com.flame.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flame.order.mapper.UserMapper;
import com.flame.order.module.pojo.User;
import com.flame.order.service.UserService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author king
 * @date 2022-02-10 09:20:02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @GlobalTransactional
    @Override
    public boolean addUser(User user) {
        System.out.println("order事务id:"+ RootContext.getXID());
        return save(user);
    }
}