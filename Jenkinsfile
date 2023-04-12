pipeline {
    environment {
        registry = "https://816886327690.dkr.ecr.us-east-2.amazonaws.com"
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
        stage('Build Docker'){
             steps {
                 script{
                    app = docker.build("consume-rest-api-java", "--build-arg ARCH=amd64/ .")
                  }
             }
        }
        stage('Publish Image') {
             steps {
                script{
                    echo 'Deploy script goes here!'
                    docker.withRegistry(registry,'ecr:us-east-2:aws-ecr-creds'){
                    app.push("${env.BUILD_NUMBER}")
                    app.push("latest")
                    }
                }
             }
        }
        stage("Cleanup") {
             steps {
              echo 'Removing unused docker containers and images..'
              sh 'docker ps -aq | xargs --no-run-if-empty docker rm'
              // keep intermediate images as cache, only delete the final image
               sh 'docker images -q | xargs --no-run-if-empty docker rmi'
             }
        }
    }
}