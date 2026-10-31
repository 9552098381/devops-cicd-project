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

        stage('Test') {
            steps {
                echo 'Java application build and test completed'
            }
        }
    }
}
