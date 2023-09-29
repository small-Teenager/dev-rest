#FROM openjdk:8-jre
# docker 中使用arthas 需要安装jdk https://arthas.aliyun.com/doc/docker.html
FROM openjdk:8-jdk
#作者
MAINTAINER yaodong199@icloud.com

#将宿主机jar包拷贝到容器中，此命令会将jar包拷贝到容器的根路径/下
ADD target/*.jar /application.jar

#暴露端口8080
EXPOSE 8080
# 设置JVM初始堆内存为512M 设置JVM最大堆内存为256M
ENV JVM_OPTIONS="-Xmx512m -Xms256m"

#容器启动时执行的命令
CMD ["java", "-jar" ,"/application.jar"]




