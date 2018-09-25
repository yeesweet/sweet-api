#!/bin/sh
git pull
sleep 1
mvn clean package -Dmaven.test.skip=true -Pproduction
