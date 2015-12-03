#  Copyright 2014 IBM
#
#   Licensed under the Apache License, Version 2.0 (the "License");
#   you may not use this file except in compliance with the License.
#   You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.

FROM tomcat8-maven3-java8
MAINTAINER Sachin Junghare "sachin.junghare@infosys.com"

RUN yum -y install wget yum-cron && \
	   yum -y update && \
	   yum -y install wget git-core &&\
	   yum clean

USER  root

WORKDIR /usr/local/stocks-quote-service

# Get remote containt into the directory
RUN git config --global http.proxy http://sachin.junghare:Dec2011B@10.74.91.103:80
RUN git clone https://github.com/sachininfy/stocks-quote-service.git /usr/local/stocks-quote-service
RUN ls -ltr

ADD pom.xml /usr/local/stocks-quote-service/pom.xml
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

ADD src /usr/local/stocks-quote-service/src
RUN ["mvn", "package"]
CMD ["java", "-war", "/usr/local/stocks-quote-service/target/StocksService.war"]
	
#RUN rm -rf /usr/local/tomcat/webapps/*
COPY ./target/StocksService.war /usr/local/tomcat/webapps/StocksService.war

# Define command to run the application when the container starts
CMD ["catalina.sh", "run"]