package com.taskflow.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.taskflow.common.Result;
import com.taskflow.dto.UserInfoDTO;
import com.taskflow.service.UserService;
import com.taskflow.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Tag(name = "文件接口")
@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class FileController {
    private final UserService userService;
    @Value("${taskflow.upload.path:./uploads/}")
    private String uploadPath;

    @PostMapping("/avatar")
    public Result<Map<String,String>> uploadAvatar(@RequestParam("file") MultipartFile file) throws Exception {
        Long uid = SecurityUtils.getCurrentUserId();
        String name = file.getOriginalFilename();
        if (name == null || !name.toLowerCase().matches(".*\\.(jpg|jpeg|png|gif)$")) return Result.badRequest("仅支持jpg/png/gif");
        if (file.getSize() > 2*1024*1024) return Result.badRequest("图片不超过2MB");
        String ext = FileUtil.extName(name);
        String fn = IdUtil.fastSimpleUUID() + "." + ext;
        File dir = new File(uploadPath);
        if (!dir.exists()) dir.mkdirs();
        file.transferTo(new File(dir, fn));
        String url = "/uploads/" + fn;
        UserInfoDTO dto = new UserInfoDTO(); dto.setAvatar(url);
        userService.updateProfile(uid, dto);
        Map<String,String> m = new HashMap<>(); m.put("url", url);
        return Result.ok("上传成功", m);
    }
}