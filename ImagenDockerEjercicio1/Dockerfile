FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

ARG JDK21_URL="https://aka.ms/download-jdk/microsoft-jdk-21.0.8-linux-x64.tar.gz"

ARG MAVEN_VERSION="3.9.9"
ARG MAVEN_URL="https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz"

ARG SONAR_VERSION="5.0.1.3006"
ARG SONAR_URL="https://binaries.sonarsource.com/Distribution/sonar-scanner-cli/sonar-scanner-cli-${SONAR_VERSION}-linux.zip"

RUN apt-get update && \
    apt-get install -y wget unzip software-properties-common curl ca-certificates gnupg git tar

WORKDIR /opt/java
RUN mkdir java21 \
    && wget -qO /tmp/java21.tar.gz $JDK21_URL \
    && tar -xzf /tmp/java21.tar.gz -C /opt/java/java21 --strip-components=1 \
    && rm /tmp/java21.tar.gz
ENV JAVA_HOME="/opt/java/java21"
ENV PATH="${JAVA_HOME}/bin:${PATH}"

WORKDIR /opt/apache-maven
RUN wget -qO /tmp/maven.tar.gz $MAVEN_URL && \
    tar -xzf /tmp/maven.tar.gz -C /opt/apache-maven --strip-components=1 && \
    rm /tmp/maven.tar.gz && \
    ln -s /opt/apache-maven/bin/mvn /usr/local/bin/mvn \
    && mkdir -p /opt/apache-maven/.m2/repository
ENV MAVEN_CONFIG="/opt/apache-maven/.m2"
ENV MAVEN_HOME="/opt/apache-maven"
ENV PATH="${MAVEN_HOME}/bin:${PATH}"

WORKDIR /opt/sonar-scanner
RUN wget -qO /tmp/sonar.zip $SONAR_URL \
    && unzip /tmp/sonar.zip -d /tmp \
    && mv /tmp/sonar-scanner-*/* /opt/sonar-scanner \
    && rm /tmp/sonar.zip \
    && ln -s /opt/sonar-scanner/bin/sonar-scanner /usr/local/bin/sonar-scanner \
    && sed -i 's/use_embedded_jre=true/use_embedded_jre=false/g' /opt/sonar-scanner/bin/sonar-scanner

ENV PATH="/opt/sonar-scanner/bin:${PATH}"

WORKDIR /workspace