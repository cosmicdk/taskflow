package com.taskflow.controller;

import com.taskflow.common.Result;
import com.taskflow.dto.UserInfoDTO;
import com.taskflow.service.UserService;
import com.taskflow.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户接口", description = "个人中心")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/info")
    public Result<UserInfoDTO> info() {
        return Result.ok(userService.getCurrentUser(SecurityUtils.getCurrentUserId()));
    }

    @Operation(summary = "修改个人信息")
    @PutMapping("/profile")
    public Result<UserInfoDTO> updateProfile(@RequestBody UserInfoDTO dto) {
        return userService.updateProfile(SecurityUtils.getCurrentUserId(), dto);
    }
}