
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
                    bat 'docker build -t cicd-demo:latest .'
                }
            }
        }

        stage('Docker Deploy') {
            steps {
                bat 'docker rm -f cicd-demo-container'
                bat 'docker run -d --name cicd-demo-container -p 8080:8080 cicd-demo:latest'
            }
        }

        stage('Test') {
            steps {
                echo 'Java application build completed'
            }
        }
    }
}
