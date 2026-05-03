package com.taskflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taskflow.common.BusinessException;
import com.taskflow.common.Result;
import com.taskflow.constant.TaskConstants;
import com.taskflow.dto.*;
import com.taskflow.entity.User;
import com.taskflow.mapper.UserMapper;
import com.taskflow.security.JwtTokenProvider;
import com.taskflow.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtTokenProvider jwtTokenProvider;

    @Override @Transactional
    public Result<UserInfoDTO> register(RegisterDTO dto) {
        long count = this.count(new LambdaQueryWrapper<User>().eq(User::getUsername, dto.getUsername()));
        if (count > 0) return Result.fail("用户名已被注册");
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setNickname(dto.getNickname() != null ? dto.getNickname() : dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setStatus(TaskConstants.USER_STATUS_NORMAL);
        this.save(user);
        return Result.ok("注册成功", UserInfoDTO.from(user));
    }

    @Override
    public Result<LoginResultDTO> login(LoginDTO dto) {
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, dto.getUsername()));
        if (user == null) return Result.fail("用户名或密码错误");
        if (user.getStatus() == null || user.getStatus() == 0) return Result.fail("账号已被禁用");
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) return Result.fail("用户名或密码错误");
        String token = jwtTokenProvider.generateToken(user.getId(), user.getUsername());
        return Result.ok(new LoginResultDTO(token, jwtTokenProvider.getExpirationSeconds(), UserInfoDTO.from(user)));
    }

    @Override public UserInfoDTO getCurrentUser(Long userId) {
        User user = this.getById(userId);
        if (user == null) throw new BusinessException(404, "用户不存在");
        return UserInfoDTO.from(user);
    }

    @Override @Transactional
    public Result<UserInfoDTO> updateProfile(Long userId, UserInfoDTO dto) {
        User user = this.getById(userId);
        if (user == null) return Result.fail("用户不存在");
        if (dto.getNickname() != null) user.setNickname(dto.getNickname());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getAvatar() != null) user.setAvatar(dto.getAvatar());
        this.updateById(user);
        return Result.ok("更新成功", UserInfoDTO.from(user));
    }
}