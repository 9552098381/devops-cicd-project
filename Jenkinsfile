
pipeline {
    agent any

    stages {

        // Step 1: Checkout
        stage('Checkout') {
            steps {
                echo 'Source code checked out successfully'
            }
        }

        // Step 2: Maven Build and Unit Tests
        stage('Maven Build') {
            steps {
                dir('java-app') {
                    bat 'mvn clean package'
                }
            }
        }

        // Step 3: Docker Image Build
        stage('Docker Build') {
            steps {
                dir('java-app') {
                    bat 'docker build -t cicd-demo:v%BUILD_NUMBER% .'
                }
            }
        }

        // Step 4: Docker Deployment
        stage('Docker Deploy') {
            steps {
                bat 'docker rm -f cicd-demo-container'
                bat 'docker run -d --name cicd-demo-container -p 8080:8080 cicd-demo:v%BUILD_NUMBER%'
            }
        }

        // Step 5: Application Health Check
        stage('Health Check') {
            steps {
                bat 'powershell -NoProfile -Command "Start-Sleep -Seconds 15; curl.exe --fail http://localhost:8080/actuator/health"'
            }
        }

<<<<<<< HEAD
        // Step 6: Manual Approval
        stage('Manual Approval') {
=======
stage('Manual Approval') {
    steps {
        timeout(time: 15, unit: 'MINUTES') {
            input message: 'Approve Production Deployment?',
                  ok: 'Proceed'
        }
    }
}        
stage('Test') {
>>>>>>> 2afb203 (Increase manual approval timeout to 15 minutes)
            steps {
                timeout(time: 15, unit: 'MINUTES') {
                    input message: 'Approve Production Deployment?',
                          ok: 'Proceed'
                }
            }
        }

        // Step 7: Test Confirmation
        stage('Test') {
            steps {
                echo 'Java application build and pipeline approval completed successfully'
            }
        }
    }

    // Post Build Actions
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

