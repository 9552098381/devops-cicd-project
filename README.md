# Enterprise Java CI/CD Automation Project

An end-to-end CI/CD automation project for a Java Spring Boot application using Jenkins, Docker, Maven, Ansible, and GitHub.

## Project Overview

This project automates the build and deployment process of a Java application using Jenkins Declarative Pipeline.

The pipeline builds the application using Maven, creates a Docker image, deploys it to the staging environment, performs health checks, and deploys it to production after manual approval.

## Technologies Used

- Java 21
- Spring Boot
- Maven
- Jenkins
- Docker
- Ansible
- Git and GitHub
- Ubuntu Linux
- GitHub Webhooks
## Project Architecture

Developer
   |
   v
GitHub Repository
   |
   v
Jenkins Pipeline
   |
   v
Maven Build
   |
   v
Docker Image
   |
   v
Ansible Deployment
   |
   v
Staging Environment
   |
   v
Health Check
   |
   v
Manual Approval
   |
   v
Production Environment
   |
   v
Production Health Check

## CI/CD Pipeline Stages

1. **Checkout:** Clone source code from GitHub.
2. **Maven Build:** Compile and package the Java application.
3. **Docker Build:** Create a Docker image.
4. **Staging Deployment:** Deploy the application using Ansible.
5. **Health Check:** Verify application health.
6. **Manual Approval:** Get approval before production deployment.
7. **Production Deployment:** Deploy the application to production.
8. **Rollback:** Restore the previous production image if deployment or health check fails.
## Deployment Environments

| Environment | Port | Container Name |
|---|---:|---|
| Test | 8080 | cicd-demo |
| Staging | 8086 | cicd-demo-staging |
| Production | 8087 | cicd-demo-prod |

## Application Health Check

Spring Boot Actuator is used to monitor application health.

Health Check URL:

- Test: http://localhost:8080/actuator/health
- Staging: http://localhost:8086/actuator/health
- Production: http://localhost:8087/actuator/health

Expected Response:

{"status":"UP"}
## Rollback Strategy

The Jenkins pipeline stores the previously deployed production Docker image before starting a new deployment.

If the new deployment or health check fails, Jenkins attempts to restore the previous production image using Ansible.

After rollback, the application health is checked again.

## Project Testing Status

| Feature | Status |
|---|---|
| Jenkins Pipeline | Successful |
| Maven Build | Successful |
| Docker Build | Successful |
| Staging Deployment | Verified |
| Production Deployment | Verified |
| Health Check | Verified |
| Manual Staging Rollback | Tested |
| Automated Production Rollback | Logic implemented; failure test pending |
| Manual Ansible Rollback (v48 to v47) | Passed |
| GitHub Webhook | HTTP 200; Jenkins pipeline successful |

## Key Features

- Automated Java application build
- Docker image versioning
- Ansible-based deployment
- Separate staging and production environments
- Manual production approval
- Application health checks
- Production rollback logic
## Project Structure

```text
devops-cicd-project/
├── Jenkinsfile
├── README.md
├── java-app/
├── app/
└── ansible/
    ├── ansible.cfg
    ├── inventories/
    │   ├── dev/
    │   ├── staging/
    │   └── prod/
    ├── playbooks/
    └── roles/

```
## Author

**Ashwini J. Dhole**

Java Application Support Engineer | Java | AWS | DevOps

GitHub Repository:  
https://github.com/9552098381/devops-cicd-project
