#!/bin/bash
# #################################################################
# command shell
# ##################################################################
#获取上一级目录
base_home=$(cd `dirname $0`/..; pwd)
#获取启动jar包名称
app_name=$(cd $base_home && ls *.jar)
#jdk启动参数
java_opts="-server -Xms128m -Xmx256m -Xmn128m -Xss256k -Duser.dir=$base_home -Djava.security.egd=file:/dev/.
./dev/urandom"
#springboot启动参数
spring_config=""

is_exist(){
  pid=`ps -ef|grep $base_home/$app_name|grep -v grep|awk '{print $2}' `
  if [ -z "${pid}" ]; then
    return 1
  else
    return 0
  fi
}

start(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${app_name} is already running. pid=${pid} ."
  else
    nohup java -jar $java_opts $base_home/$app_name $spring_config > $base_home/bin/run.log 2>&1 &
    echo "${app_name} starting!"
  fi
}

stop(){
  is_exist
  if [ $? -eq "0" ]; then
    kill -9 $pid
    if [ $? -eq "0" ]; then
        echo "${app_name} stop success!"
    fi
  else
    echo "${app_name} is not running"
  fi
}

status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${app_name} is running. Pid is ${pid}"
  else
    echo "${app_name} is NOT running."
  fi
}

restart(){
  stop
  start
}

usage(){
  echo "start|stop|status|restart"
}

case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac