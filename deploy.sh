#!/bin/bash

REPOSITORY=/home/ec2-user/giljob
cd $REPOSITORY

APP_NAME=giljob
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할것 없음."
else
  echo "> kill -9 $CURRENT_PID"
  kill -9 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH 에 실행권한 추가"
chmod +x $JAR_PATH

echo "> $JAR_PATH 실행"
nohup java -jar -Dspring.config.location=/home/ec2-user/application.yml $JAR_PATH > /dev/null 2> /dev/null < /dev/null &

echo "> 배포 완료"