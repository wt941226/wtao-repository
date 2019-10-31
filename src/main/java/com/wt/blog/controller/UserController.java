package com.wt.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wt.blog.base.service.IUserService;
import com.wt.blog.dto.LoginDto;
import com.wt.blog.entity.User;
import com.wt.security.jwt.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author wtao
 * @since 2019-10-27
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/blog/user")
@Slf4j
public class UserController {

    @Resource
    private IUserService iUserService;
    @Resource
    private ThreadPoolTaskExecutor executor;

    @ApiOperation("用户登录")
    @PostMapping("login")
    public ResponseEntity<User> login(@RequestBody LoginDto dto) throws UnsupportedEncodingException {

        User result = iUserService.getOne(new QueryWrapper<User>().eq("account", dto.getUsername()));
        if (result.getPassword().equals(dto.getPassword())) {
            String token = JWTUtils.sign(dto.getUsername(), dto.getPassword());
            String username = JWTUtils.getUsername(token);
            System.out.println("login() username by token :" + username);
            System.out.println(token);
            /*  response.setHeader("token", token);*/
            result.setToken(token);
            return ResponseEntity.ok(result);
            //return new o(200, "登录成功", token);
        } else {
            throw new UnauthorizedException();
        }
    }

    @ApiOperation("获取用户")
    @GetMapping("getUser/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") Integer id) {


       /* executor.submit(() -> {
            System.out.println("hahhaa");
        });*/
        //throw new WtaoException(AuthExceptionEnum.TOKEN_IS_EMPTY);
        return null;
    }
}

