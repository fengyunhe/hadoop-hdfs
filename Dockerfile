FROM azul/zulu-openjdk-debian:11-jre-latest
MAINTAINER yangyan <277160299@qq.com>

COPY target/hadoop-hdfs-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/hadoop/applications/hadoop-hdfs-1.0-SNAPSHOT-jar-with-dependencies.jar
CMD 'java -jar /opt/hadoop/applications/hadoop-hdfs-1.0-SNAPSHOT-jar-with-dependencies.jar'