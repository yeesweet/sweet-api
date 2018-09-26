#!/bin/sh
git pull
sleep 1
mvn clean package -Dmaven.test.skip=true
cp -rf /root/sweet/sweet-api/target/api.jar  /usr/local/sweet-api
sh /usr/local/sweet-api/restart.sh
