// sqa_a0367f7df6ecbbad6ca0e1bb3c5246f5b6a8495f    - github-scan
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

        stage('SonarQube Analysis') {
            steps {
                script {
                    def SCANNER_HOME = tool name: 'sonar-scanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
                    
                    withSonarQubeEnv('sonar-server') {
                        withCredentials([string(credentialsId: 'sonar-server', variable: 'SONAR_TOKEN')]) {
                            sh """
                                ${SCANNER_HOME}/bin/sonar-scanner \
                                -Dsonar.projectKey=uptime \
                                -Dsonar.projectName=uptime \
                                -Dsonar.sources=. \
                                -Dsonar.host.url=$SONAR_HOST_URL \
                                -Dsonar.login=$SONAR_TOKEN
                            """
                        }
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                script {
                    def qg = waitForQualityGate()
                    if (qg.status != 'OK') {
                        error "Quality Gate failed: ${qg.status}"
                    }
                }
            }
        }


        stage('Login to DockerHub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'NeeweeDockerHubAccount', 
                                                 usernameVariable: 'DOCKER_USER', 
                                                 passwordVariable: 'DOCKER_PASS')]) {
                    sh """
                        docker login -u $DOCKER_USER -p $DOCKER_PASS
                       """ 
                }
            }
        }
        stage('Docker Tag Image') {
            steps {
                sh 'docker tag play-java-hello-world-tutorial:${APP_VERSION} ${TAG_NAME}:${APP_VERSION}'
            }
        }
        stage('Docker Push') {
            steps {
                sh 'docker push ${TAG_NAME}:${APP_VERSION}'
            }
        }

        stage('Trivy Scan') {
            steps {
                sh '''
                    wget https://raw.githubusercontent.com/aquasecurity/trivy/main/contrib/html.tpl
                    trivy image --format template --template @./html.tpl -o report.html ${TAG_NAME}:${APP_VERSION}
                '''
            }
        }


        stage('Docker Cleanup') {
            steps {
                sh "docker rmi ${DOCKER_REPO}:${DOCKER_TAG} || true"
            }
        }
    }
}


