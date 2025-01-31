pipeline {
    agent any

    environment {
        TAG_NAME = 'theshubhamgour/java-play'          // Docker Tag Name
        APP_VERSION = 'pre-release-v1.0.0'    // Version
        DOCKER_REPO = "${TAG_NAME}"
        DOCKER_TAG = "${APP_VERSION}"
    }

    stages {

       stage('Build') {
            steps {
                sh 'sbt clean compile'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${TAG_NAME}:${APP_VERSION} .'
            }
        }
        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                    sh 'docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD'
                    sh 'docker tag ${TAG_NAME}:${APP_VERSION} ${DOCKER_USERNAME}/${TAG_NAME}:${APP_VERSION}'
                    sh 'docker push ${DOCKER_USERNAME}/${TAG_NAME}:${APP_VERSION}'
                }
            }
        }


        }
    }