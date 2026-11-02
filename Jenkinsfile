
pipeline {
    agent {
        label 'ubuntu-ansible'
    }

    options {
        skipDefaultCheckout(true)
    }

    stages {

        stage('Checkout') {
    steps {
        sh '''
            git clone --branch feature/java-app \
            https://github.com/9552098381/devops-cicd-project.git source
        '''
    }
}

        stage('Maven Build') {
            steps {
                dir('java-app') {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Docker Build') {
            steps {
                dir('java-app') {
                    sh 'docker build -t cicd-demo:v${BUILD_NUMBER} .'
                }
            }
        }

        stage('Docker Deploy') {
            steps {
                sh '''
                    docker rm -f cicd-demo-container || true

                    docker run -d \
                    --name cicd-demo-container \
                    -p 8080:8080 \
                    cicd-demo:v${BUILD_NUMBER}
                '''
            }
        }

        stage('Health Check') {
            steps {
                sh '''
                    sleep 15
                    curl --fail http://localhost:8080/actuator/health
                '''
            }
        }

        stage('Manual Approval') {
            steps {
                timeout(time: 15, unit: 'MINUTES') {
                    input message: 'Approve Production Deployment?',
                          ok: 'Proceed'
                }
            }
        }

        stage('Test') {
            steps {
                echo 'Java application build completed successfully!'
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true,
                  testResults: 'java-app/target/surefire-reports/*.xml'
        }

        success {
            echo 'CI/CD Pipeline completed successfully!'
        }

        failure {
            echo 'CI/CD Pipeline failed. Please check Console Output.'
        }
    }
}
