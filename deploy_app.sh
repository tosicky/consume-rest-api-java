#!/bin/bash

if [ $( sudo docker ps -a | grep -w 'consumer-rest-api-java' | wc -l ) -gt 0 ]; then
  echo "consumer-rest-api-java app container exists"
  echo "Killing container..."
  STOPPED_CONTAINER=$(sudo docker stop consumer-rest-api-java)
  test $? -eq 0 && echo "$STOPPED_CONTAINER container stopped" || echo "Container FAILED to stop"
  REMOVED_CONTAINER=$(sudo docker rm consumer-rest-api-java)
  test $? -eq 0 && echo "$REMOVED_CONTAINER container removed" || echo "Container FAILED to remove"
  #if [ $( docker ps -a | grep -w prometheus | wc -l ) -eq 0 ]; then
  #  echo "Container removed"
  #fi
else
  echo "consumer-rest-api-java Container does not exist"
fi


ECR=$1
# IMAGE_TAG=$2

# Pull app image from ecr
sudo docker pull $ECR/consumer-rest-api-java:latest

# Run app in docker container
sudo docker run -d --name consumer-rest-api-java -p 80:8080 $ECR/consumer-rest-api-java:latest
