# consumer-rest-api-java
Sample Springboot application to demo building REST api

# Jenkins Pipeline Setup
The Jenkins pipeline is docker based jenkins which integrates with sonarqube.
The docker-compose.yaml file consists of the jenkins master, jenkins agent, sonarqube services

## Launching the Jenkins and sonarqube
1. clone the repo
1. From your local machine, run `docker compose up -d`
1. Verify the services are up and running using `docker ps`

## Setup Jenkins
1. Go to http://localhost:8081 on your browser
1. Complete Jenkins configuration by entering the admin password.
The jenkins password can be gotten inside the running jenkins master container. Run `docker exec -it jenkins /bin/bash` to gain access to the container.
1. Install suggested plugins
![image](https://github.com/tosicky/consume-rest-api-java/assets/14918937/f3412bdf-c970-44f1-9b1c-0c54954f6876)

## Setup Sonarqube


