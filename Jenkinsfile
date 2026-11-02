pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Source code checked out successfully'
            }
        }

        stage('Maven Build') {
            steps {
                dir('java-app') {
                    bat 'mvn clean package'
                }
            }
        }

        stage('Docker Build') {
            steps {
                dir('java-app') {
                    bat 'docker build -t cicd-demo:v%BUILD_NUMBER% .'
                }
            }
        }

        stage('Docker Deploy') {
            steps {
                bat 'docker rm -f cicd-demo-container'
                bat 'docker run -d --name cicd-demo-container -p 8080:8080 cicd-demo:v%BUILD_NUMBER%'
            }
        }

        stage('Health Check') {
            steps {
                bat 'powershell -NoProfile -Command "Start-Sleep -Seconds 15; curl.exe --fail http://localhost:8080/actuator/health"'
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
                echo 'Java application build completed'
            }
        }
    }

    post {
        always {
            junit 'java-app/target/surefire-reports/*.xml'
        }
        success {
            echo 'CI/CD Pipeline completed successfully!'
        }
        failure {
            echo 'CI/CD Pipeline failed. Please check Console Output.'
        }
    }
}
