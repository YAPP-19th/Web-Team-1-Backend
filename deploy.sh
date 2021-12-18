#!/bin/bash

REPOSITORY=/home/ec2-user/giljob
cd $REPOSITORY

APP_NAME=giljob
JAR_NAME=$(ls $REPOSITORY/ | grep '.jar' | grep -v 'plain')
JAR_PATH=$REPOSITORY/$JAR_NAME

echo "> build 파일명: $JAR_NAME" >> /home/ec2-user/deploy.log

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않음" >> /home/ec2-user/deploy.log
else
  echo "> kill -9 $CURRENT_PID" >> /home/ec2-user/deploy.log
  kill -9 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH 에 실행 권한 추가" >> /home/ec2-user/deploy.log
chmod +x $JAR_PATH

echo "> $JAR_PATH 실행" >> /home/ec2-user/deploy.log
nohup java -jar -Dspring.config.location=/home/ec2-user/application.yml $JAR_PATH >> /home/ec2-user/deploy.log 2>/home/ec2-user/deploy_err.log

echo "> 배포 성공" >> /home/ec2-user/deploy.log
