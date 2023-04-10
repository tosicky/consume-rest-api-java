pipeline {
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
        stage('Build Docker'){
             steps {
                 script{
                    app = docker.build("consume-rest-api-java")
                  }
             }
        }
        stage('Push Image-ECR') {
             steps {
                script{
                    echo 'Deploy script goes here!'
                    docker.withRegistry('https://816886327690.dkr.ecr.us-east-2.amazonaws.com','ecr:us-east-2:aws-ecr-creds'){
                    app.push("${env.BUILD_NUMBER}")
                    app.push("latest")
                    }
                }
             }
        }

    }
}