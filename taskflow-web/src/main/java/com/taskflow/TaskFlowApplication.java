package com.taskflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskFlowApplication.class, args);
        System.out.println("""

            ╔══════════════════════════════════════════════╗
            ║         🚀 TaskFlow 启动成功！               ║
            ║                                              ║
            ║  📖 API 文档:  http://localhost:8080/doc.html ║
            ║  🔧 开发环境:  application-dev.yml           ║
            ║  📦 数据库:    MySQL 8.0                     ║
            ╚══════════════════════════════════════════════╝
            """);
    }
}