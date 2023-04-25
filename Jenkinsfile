pipeline {
//     environment {
//         registry = "https://816886327690.dkr.ecr.us-east-2.amazonaws.com"
//       }
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
//                  sh '''#!/bin/bash
//                         os_arch=$(uname -m)
                        
//                         if [[ "$os_arch" != "amd64" ]] || [[ "$os_arch" != "arm64" ]] || [[ "$os_arch" != "aarch64" ]]; then
//                             docker build -t consume-rest-api-java .
                        
//                         else
//                             docker build -t consume-rest-api-java --build-arg ARCH=aarch64/ .
                        
//                         fi
                        
//                     '''
                 script {
//                     app = docker.build("consume-rest-api-java", "--build-arg ARCH=amd64/ .")
//                      app = docker.build("consume-rest-api-java", "--platform linux/amd64 .")
                     app = docker.build("consume-rest-api-java")
                 }
                  
             }
        }
//         stage('Publish Image') {
//              steps {
//                 script{
//                     echo 'Deploy script goes here!'
//                     docker.withRegistry(registry,'ecr:us-east-2:aws-ecr-creds'){
//                     app.push("${env.BUILD_NUMBER}")
//                     app.push("latest")
//                     }
//                 }
//              }
//         }
//         stage('Build Docker Image') { 
//                 steps { 
//                     script{
//                      app = docker.build("cms")
//                     }
//                 }
//             }
         stage('Push to ECR') {
                steps {
                    script{
                        withCredentials([string(credentialsId: 'ecr-url', variable: 'ECRURL')]) {
                            withEnv(["ECRURL=${ECRURL}"]) {
                                docker.withRegistry("$ECRURL", 'ecr:ca-central-1:aws-deploy') {
                                app.push("${env.BUILD_NUMBER}")
                                app.push("latest")
                            }
                        }
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
