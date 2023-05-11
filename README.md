# consumer-rest-api-java
Sample Springboot application to demo building REST api

# Jenkins Pipeline Setup
The Jenkins pipeline is docker based jenkins which integrates with sonarqube.
The docker-compose.yaml file consists of the jenkins master, jenkins agent, sonarqube services

## Launching the Jenkins and sonarqube
1. clone the repo
1. Open the docker-compose.yaml and under the `agent` service, change the value of the JENKINS_AGENT_SSH_PUBKEY environment variable to your own generated public key.
To generate a keypair, run `ssh-keygen -t rsa -b 4096`. This will generate a private and a public key. 
1. From your local machine, run `docker compose up -d`
1. Verify the services are up and running using `docker ps`

## Setup Jenkins
1. Go to http://localhost:8081 on your browser
1. Complete Jenkins configuration by entering the admin password.
The jenkins password can be gotten inside the running jenkins master container. Run `docker exec -it jenkins /bin/bash` to gain access to the container.
1. Set up jenkins user information and password
1. Install suggested plugins
![image](https://github.com/tosicky/consume-rest-api-java/assets/14918937/f3412bdf-c970-44f1-9b1c-0c54954f6876)
1. Install the following jenkins plugins needed to run the jenkins CI/CD pipeline:
** Amazon ECR plugin
** CloudBees Credentials Plugin
** Docker Pipeline
** SSH Agent
** SonarQube Scanner for Jenkins
** Slack Notification Plugin

## Configuring Jenkins Agent on Jenkins Master
Ref: https://www.jenkins.io/doc/book/using/using-agents/
1. Create a Jenkins SSH credential
1. Go to your Jenkins dashboard;
1. Go to Manage Jenkins option in main menu and click on the Manage Credentials button;
![image](https://github.com/tosicky/consume-rest-api-java/assets/14918937/5b7d5003-4243-4d68-b77a-7ee2bb143d48)

1. select the drop option Add Credentials from the global item;
![image](https://github.com/tosicky/consume-rest-api-java/assets/14918937/2897b332-52bf-4200-845b-934dd860f8c1)

1. Fill in the form:
** Kind: SSH Username with private key;
** id: jenkins
** description: The jenkins ssh key
** username: jenkins
** Private Key: select Enter directly and press the Add button to insert the content of your private key generated in a previous step (above)
![image](https://github.com/tosicky/consume-rest-api-java/assets/14918937/9fcec328-1421-4062-9287-eb8427d52176)

** Click the Create button 

### Setup and Add the Jenkins Agent to the Jenkins Master
1. Go to your Jenkins dashboard;
1. Go to Manage Jenkins option in main menu;
1. Go to Manage Nodes and clouds item;
1. Go to New Node option in side menu;
1. Fill the Node/agent name and select the type; (e.g. Name: jenkins_agent, Type: Permanent Agent)
1. Now fill the fields:
** Remote root directory; (e.g.: /home/jenkins )
** label; (set the label to -> jenkins_agent )
** usage; ( only build jobs with label expression…​)
** Launch method; ( Launch agents by SSH )
** Host; ( jenkins-agent )
** Credentials; ( jenkins )
** Host Key verification Strategy; ( Manually trusted key verification …​ )
** Click on the Advanced field, set `Port to 22`, set JavaPath to `/opt/java/openjdk/bin/java`
1. Press the Save button and the agent1 will be registered, but offline for the time being. Click on it to see the launch process is successful.
![image](https://github.com/tosicky/consume-rest-api-java/assets/14918937/68307d60-3475-4979-98a6-d4ff1f607de9)


## Integrating Jenkins and Slack Notification

## Setup Sonarqube
1. Go to http://localhost:9090 on your browser
1. Log in with the default sonarqube credential - username: admin, password: admin 
N.B: You can change the password after the initial login
1. Go to My Account
![image](https://github.com/tosicky/consume-rest-api-java/assets/14918937/6ed14912-5db7-41d6-87af-e0b020a4371c)

1. Click on Security tab
1. Generate a token and store somewhere. You will need to add that token to Jenkins Credentials  


## Storing Pipeline Credentials on Jenkins
To run this pipeline as is, the following credentials should be created in Jenkins with the following as id.
![image](https://github.com/tosicky/consume-rest-api-java/assets/14918937/1f0dd171-47b1-41c0-9438-00ad2933438a)


** `ec2-dev` - The ec2 username (ec2-user) and ssh private key
** `ec2-ip` - The ec2 IP
** `ecr-url`- The ECR URL without the `https`
** `git` - Your git username and password
** `aws-deploy` - The AWS IAM user access key and secret key
** `sonar-token` - Sonarqube access token
** `slack` - slack app oauth2 token




## Integrating Jenkins and Sonarqube



