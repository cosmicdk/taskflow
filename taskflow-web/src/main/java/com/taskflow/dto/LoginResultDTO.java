package com.taskflow.dto;

import lombok.Data;

@Data
public class LoginResultDTO {
    private String token;
    private Long expiresIn;
    private UserInfoDTO user;

    public LoginResultDTO(String token, Long expiresIn, UserInfoDTO user) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.user = user;
    }
}