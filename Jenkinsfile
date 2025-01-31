pipeline {
    agent any

    environment {
        TAG_NAME = 'theshubhamgour/java-play'  // Docker Tag Name
        APP_VERSION = 'pre-release-v1.0.0'    // Version
        DOCKER_REPO = "${TAG_NAME}"
        DOCKER_TAG = "${APP_VERSION}"
    }

    stages {
        stage('Build') {
            steps {
                // Clean before build
                cleanWs()
                // We need to explicitly checkout from SCM here
                checkout scm
                echo "Building ${env.JOB_NAME}..."
            }
        }
        stage('SBT Clean') {
            steps {
                sh 'sbt clean'
            }
        }
        stage('SBT Compile') {
            steps {
                sh 'sbt compile'
            }
        }
        stage('SBT Publish to Local') {
            steps {
                sh 'sbt docker:publishLocal'
            }
        }
        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                    sh 'docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD'
                }
            }
        }
        stage('Docker Tag Image') {
            steps {
                sh 'docker tag java-play:${APP_VERSION} $DOCKER_USERNAME/java-play-${APP_VERSION}'
            }
        }
        stage('Docker Push') {
            steps {
                sh 'docker push $DOCKER_USERNAME/java-play-${APP_VERSION}'
            }
        }
        stage('Docker Cleanup') {
            steps {
                sh 'docker rmi java-play:${APP_VERSION}'
            }
        }
    }
}