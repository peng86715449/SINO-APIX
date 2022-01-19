#基础镜像：拉取alpine版本的
FROM registry.cn-shanghai.aliyuncs.com/sinosdx/alpine-oraclejdk8:skywalking-agent-v2
MAINTAINER pengjiahu jiahu.peng@sinosdx.com
# 添加变量，如果使用dockerfile-maven-plugin，则会自动替换这里的变量内容
ARG JAR_FILE=target/*.jar
ARG JAR_PORT=8888
ARG ARG_JAVA_OPTS="-server -Xms1g -Xmx1g -Xmn512m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=256m -XX:-OmitStackTraceInFastThrow"
ENV ENV_JAVA_OPTS=${ARG_JAVA_OPTS}

#将打包好的spring程序拷贝到容器中的指定位置
ADD ${JAR_FILE} /usr/app/app.jar
ADD docs/skywalking/ /usr/app/agent/plugins/
#配置时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
#容器对外暴露8888端口
EXPOSE ${JAR_PORT}
#容器启动后需要执行的命令
ENTRYPOINT [ "sh", "-c", "java $ENV_JAVA_OPTS -Djava.security.egd=file:/dev/./urandom $APP_ENV -jar /usr/app/app.jar"]
