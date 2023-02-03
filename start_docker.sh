#!/bin/sh

set -e 

CONFIG_FILE_PATH=/home/config/application.properties

# template config
mkdir -p /home/config
touch ${CONFIG_FILE_PATH}


echo "spring.data.mongodb.host=${MONGO_HOST}" >> ${CONFIG_FILE_PATH}

echo "using config:"
cat ${CONFIG_FILE_PATH}

# start server
java -jar /home/blackjack-0.0.1-SNAPSHOT.jar --spring.config.location=file:///home/config/application.properties
