package com.flame.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.flame.order.module.pojo.User;
import com.flame.order.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author king
 * @Description:
 * @date 2022-02-10 09:20:02
 */
@Api(tags = "订单服务")
@Slf4j
@RestController
@RequestMapping("order")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "添加")
    @PostMapping("/add")
    public Boolean addUser(@RequestBody User record) {
        return userService.save(record);
    }

    @ApiOperation(value = "删除")
    @PostMapping("/delete")
    public Boolean deleteUser(@RequestBody User record) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getId, record.getId());
        return userService.remove(queryWrapper);
    }

    @ApiOperation(value = "修改")
    @PostMapping("/update")
    public Boolean updateUser(@RequestBody User record) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(User::getUserName, record.getUserName()).eq(User::getId, record.getId());
        return userService.update(updateWrapper);
    }

    @ApiOperation(value = "查询列表")
    @PostMapping("/select")
    public List<User> selectUser(@RequestBody User record) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getId, record.getId());
        return userService.list(queryWrapper);
    }

}