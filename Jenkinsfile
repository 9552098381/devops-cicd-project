
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
             rm -rf source
            git clone --branch feature/java-app \
            https://github.com/9552098381/devops-cicd-project.git source
        '''
    }
}

        stage('Maven Build') {
            steps {
               dir('source/java-app') {
                    sh '''
    java -version
    javac -version
    mvn -version
    mvn clean package
'''
                }
            }
        }

        stage('Docker Build') {
            steps {
                 dir('source/java-app') {
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
            for i in $(seq 1 12); do
                if curl --fail --silent http://localhost:8080/actuator/health; then
                    echo "Application is healthy!"
                    exit 0
                fi

                echo "Application is starting... Attempt $i"
                sleep 5
            done

            echo "Health check failed!"
            exit 1
        '''
    }
}
        stage('Ansible Staging Deployment') {
            steps {
                dir('source') {
                    withEnv([
                        "PATH+ANSIBLE=/home/ashwini_d/ansible-venv/bin",
                        "ANSIBLE_CONFIG=ansible/ansible.cfg"
                    ]) {
                        sh '''
                            ansible-playbook \
                            -i ansible/inventories/staging/hosts.ini \
                            ansible/playbooks/deploy-docker.yml \
                            -e "docker_image=cicd-demo:v${BUILD_NUMBER}" \
                            -e "deploy_group=staging"
                        '''
                    }
                }
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

        stage('Ansible Production Deployment') {
            steps {
                dir('source') {
                    withEnv([
                        "PATH+ANSIBLE=/home/ashwini_d/ansible-venv/bin",
                        "ANSIBLE_CONFIG=ansible/ansible.cfg"
                    ]) {
                        sh '''
                            ansible --version

                            ansible-playbook \
                            -i ansible/inventories/prod/hosts.ini \
                            ansible/playbooks/deploy-docker.yml \
                            -e "docker_image=cicd-demo:v${BUILD_NUMBER}"
                        '''
                    }
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
                  testResults: 'source/java-app/target/surefire-reports/*.xml'
        }

        success {
            echo 'CI/CD Pipeline completed successfully!'
        }

        failure {
            echo 'CI/CD Pipeline failed. Please check Console Output.'
        }
    }
}
