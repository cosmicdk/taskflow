package com.taskflow.dto;

import com.taskflow.entity.User;
import lombok.Data;
import java.io.Serializable;

@Data
public class UserInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String nickname;
    private String avatar;
    private String email;

    public static UserInfoDTO from(User user) {
        UserInfoDTO dto = new UserInfoDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setNickname(user.getNickname());
        dto.setAvatar(user.getAvatar());
        dto.setEmail(user.getEmail());
        return dto;
    }
}