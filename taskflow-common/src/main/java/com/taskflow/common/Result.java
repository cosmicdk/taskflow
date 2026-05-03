package com.taskflow.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer code;
    private String message;
    private T data;
    private Long timestamp;

    private Result() { this.timestamp = System.currentTimeMillis(); }
    private Result(Integer code, String message) { this(); this.code = code; this.message = message; }
    private Result(Integer code, String message, T data) { this(code, message); this.data = data; }

    public static <T> Result<T> ok() { return new Result<>(200, "操作成功"); }
    public static <T> Result<T> ok(T data) { return new Result<>(200, "操作成功", data); }
    public static <T> Result<T> ok(String message, T data) { return new Result<>(200, message, data); }
    public static <T> Result<T> fail() { return new Result<>(500, "操作失败"); }
    public static <T> Result<T> fail(String message) { return new Result<>(500, message); }
    public static <T> Result<T> fail(Integer code, String message) { return new Result<>(code, message); }
    public static <T> Result<T> unauthorized() { return new Result<>(401, "未授权，请登录"); }
    public static <T> Result<T> forbidden() { return new Result<>(403, "无权限访问"); }
    public static <T> Result<T> notFound() { return new Result<>(404, "资源不存在"); }
    public static <T> Result<T> badRequest(String message) { return new Result<>(400, message); }
}