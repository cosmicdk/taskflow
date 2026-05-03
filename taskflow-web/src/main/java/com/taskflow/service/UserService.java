package com.taskflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taskflow.dto.*;
import com.taskflow.entity.User;

public interface UserService extends IService<User> {
    Result<UserInfoDTO> register(RegisterDTO dto);
    Result<LoginResultDTO> login(LoginDTO dto);
    UserInfoDTO getCurrentUser(Long userId);
    Result<UserInfoDTO> updateProfile(Long userId, UserInfoDTO dto);
}