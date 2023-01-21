FROM azul/zulu-openjdk-debian:11-jre-latest
MAINTAINER yangyan <277160299@qq.com>

ENV HADOOP_VERSION 3.3.4
COPY target/hadoop-hdfs-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/hadoop/applications/hadoop-hdfs-1.0-SNAPSHOT-jar-with-dependencies.jar
ENV HADOOP_HOME=/opt/hadoop-$HADOOP_VERSION
CMD 'java -jar /opt/hadoop/applications/hadoop-hdfs-1.0-SNAPSHOT-jar-with-dependencies.jar'