# ============================================
# TaskFlow Dockerfile（后端）
# ============================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# 复制 JAR
COPY target/taskflow-web-*.jar app.jar

# 上传目录
RUN mkdir -p /data/uploads

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]