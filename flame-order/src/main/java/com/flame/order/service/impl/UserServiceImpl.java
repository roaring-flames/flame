package com.flame.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flame.order.mapper.UserMapper;
import com.flame.order.module.pojo.User;
import com.flame.order.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author king
 * @date 2022-02-10 09:20:02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public boolean save(User entity) {
        return super.save(entity);
    }
}