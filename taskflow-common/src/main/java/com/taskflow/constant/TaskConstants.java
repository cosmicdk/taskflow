package com.taskflow.constant;

public final class TaskConstants {
    private TaskConstants() {}

    public static final String PRIORITY_LOW = "LOW";
    public static final String PRIORITY_MEDIUM = "MEDIUM";
    public static final String PRIORITY_HIGH = "HIGH";
    public static final String PRIORITY_URGENT = "URGENT";

    public static final String STATUS_TODO = "TODO";
    public static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
    public static final String STATUS_DONE = "DONE";

    public static final Integer USER_STATUS_NORMAL = 1;
    public static final Integer USER_STATUS_DISABLED = 0;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_HEADER = "Authorization";

    public static final String REDIS_KEY_VERIFY_CODE = "taskflow:verify_code:";
    public static final String REDIS_KEY_CATEGORY_LIST = "taskflow:category_list:";
}