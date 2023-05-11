pipeline {
    
    environment {
       ECRURL = credentials('ecr-url')   
       REMOTEIP = credentials('ec2-ip')
    }
    
    agent { label 'jenkins_agent'}
    tools {
          maven '3.8.7'
        }
    stages {
        stage('Build') {
            steps {
               sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test'){
            steps {
              script{
                 echo 'Running unit tests'
                 sh 'mvn test'
               }
            }
        }
      stage('SonarQube analysis') {
           steps{
             script {

                withSonarQubeEnv('sonarqube') { // If you have configured more than one global server connection, you can specify its name
                sh "mvn clean verify sonar:sonar"
             }
          }
        }
    }
        stage('Build Docker'){
             steps {
                 script {
                     app = docker.build("consume-rest-api-java")
                 }
                  
             }
        }
         stage('Push to ECR') {
                steps {
                    script{
                        withCredentials([string(credentialsId: 'ecr-url', variable: 'ECRURL')]) {
                            withEnv(["ECRURL=${ECRURL}"]) {
                                docker.withRegistry(env.ECRURL, 'ecr:us-east-1:aws-deploy') {
//                                 app.push("${env.BUILD_NUMBER}")  # For tagging the image with the actual build number 
                                app.push("latest")
                            }
                        }
                    }
                  }
            }
         }
        
            stage('Deploy dockerized app on ec2') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    withCredentials([string(credentialsId: 'ec2-ip', variable: 'REMOTEIP')]) {
                            withEnv(["REMOTEIP=${REMOTEIP}"]) {
                                sshagent(credentials: ['ec2-dev']) {
                                    sh "scp -o StrictHostKeyChecking=no  deploy_app.sh ec2-user@${env.REMOTEIP}:"
                                    sh "ssh -o StrictHostKeyChecking=no ec2-user@${env.REMOTEIP} chmod a+x deploy_app.sh"
                                    sh "ssh -o StrictHostKeyChecking=no ec2-user@${env.REMOTEIP} ./deploy_app.sh ${env.ECRURL}"
                                }
                            }
                    }
                }
            }
        }
        
        stage('TEST APP') { 
            steps {
                sh "python health.py ${env.REMOTEIP}"
            }
        }
}

post{
     
        failure{
            // slackSend( channel: "#devops", color: "red", message:"${custom_msg()}")
            notifyProductionDeploy()
        }
 
        success{
            // slackSend( channel: "#devops", color: "good", message: "${custom_msg()}")
            notifyProductionDeploy()
        }
    }

}

/* Slack Notification Set */
def notifyProductionDeploy() {
if (currentBuild.currentResult == 'SUCCESS') {
    def message = "@here Build <${env.BUILD_URL}|${currentBuild.displayName}> " +
        "${currentBuild.currentResult}"
    slackSend(message: message, channel: '#devops', color: 'good')
} else {
    def message = "@here Build <${env.BUILD_URL}|${currentBuild.displayName}> " +
        "${currentBuild.currentResult}"
    slackSend(message: message, channel: '#devops', color: 'danger')
}
}
