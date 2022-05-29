package com.rare_earth_track.admin.controller;


import com.rare_earth_track.admin.service.RetUserService;
import com.rare_earth_track.common.api.CommonPage;
import com.rare_earth_track.common.api.CommonResult;
import com.rare_earth_track.mgb.model.RetUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户controller
 * @author hhoa
 **/
@RestController
@Tags({@Tag(name="用户管理", description = "RetUserController")})
public class RetUserController {
    private final RetUserService userService;

    public RetUserController(RetUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "分页获取用户列表")
    @GetMapping("/users")
    public CommonResult<CommonPage<RetUser>> list(@Parameter(description = "页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                  @Parameter(description = "页面大小") @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){
        return CommonResult.success(CommonPage.restPage(userService.list(pageNum, pageSize)));
    }

    @Operation(summary = "通过用户名获取用户")
    @GetMapping("/users/{username}")
    public CommonResult<RetUser> getUser(@PathVariable("username") String username){
        RetUser userByName = userService.getUserByName(username);
        return CommonResult.success(userByName);
    }
    @Operation(summary = "通过用户参数获取用户信息")
    @GetMapping
    public CommonResult<List<RetUser>> getUserByUserParam(RetUser user){
        List<RetUser> users = userService.getUser(user);
        return CommonResult.success(users);
    }

    @Operation(summary = "更新用户资料")
    @PatchMapping("/user")
    public CommonResult<String> updateUser(@RequestBody RetUser newUser){
        userService.updateUser(newUser);
        return CommonResult.success(null);
    }
    @Operation(summary = "更改用户角色")
    @PatchMapping("/users/{userId}/role")
    public CommonResult<RetUser> updateUserRole(@PathVariable("userId") Long userId,
                                                @RequestParam("roleId") Long roleId){
        userService.updateUserRole(userId, roleId);
        return CommonResult.success(null);
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/users/{userId}")
    public CommonResult<String> deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUserByUserId(userId);
        return CommonResult.success(null);
    }
}
