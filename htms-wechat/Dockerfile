FROM daocloud.io/maven:3-jdk-8
VOLUME /tmp

ADD pom.xml /tmp/build/
ADD src /tmp/build/src
        #构建应用
RUN cd /tmp/build && mvn clean package
        #拷贝编译结果到指定目录
        && mv target
#ADD htms-wechat-0.0.1-SNAPSHOT.jar app.jar
#RUN bash -c 'touch /app.jar'
#EXPOSE 8761
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]