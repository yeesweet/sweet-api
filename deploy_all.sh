#!/bin/sh
sh package.sh
cp -rf /root/sweet/sweet-api/target/api.war  /usr/local/sweet-app/sweet-api/deploy
sh /usr/local/sweet-app/sweet-api/server.sh redeploy
