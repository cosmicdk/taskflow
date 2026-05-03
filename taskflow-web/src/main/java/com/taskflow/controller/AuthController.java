package com.taskflow.controller;

import com.taskflow.common.Result;
import com.taskflow.dto.LoginDTO;
import com.taskflow.dto.LoginResultDTO;
import com.taskflow.dto.RegisterDTO;
import com.taskflow.dto.UserInfoDTO;
import com.taskflow.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "认证接口", description = "注册、登录")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<UserInfoDTO> register(@Valid @RequestBody RegisterDTO dto) {
        return userService.register(dto);
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<LoginResultDTO> login(@Valid @RequestBody LoginDTO dto) {
        return userService.login(dto);
    }
}