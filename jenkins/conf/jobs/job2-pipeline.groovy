#!groovy

pipeline {
    agent any
    tools {
        terraform 'terraform 1.1.0'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '100'))
        ansiColor('xterm')
    }
    stages {
        stage('Git'){
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/develop']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/MAILLERC0/Projet_DEVOPS_MAILLER']]])   
            }
        }
        stage('Terraform init') { 
            steps {
                sh 'cd /var/jenkins_home/workspace/IaC/Job2/terraform'
                sh label: '', script: 'terraform init'
            }
        }
        stage('Terraform fmt') { 
            steps {
                sh 'cd /var/jenkins_home/workspace/IaC/Job2/terraform'
                sh label: '', script: 'terraform fmt'
            }
        }
        stage('Terraform validate') { 
            steps {
                sh 'cd /var/jenkins_home/workspace/IaC/Job2/terraform'
                sh label: '', script: 'terraform validate'
            }
        }
        stage('Terraform apply/destroy') { 
            steps {
                sh 'cd /var/jenkins_home/workspace/IaC/Job2/terraform'
                sh label: '', script: 'terraform ${action} --auto-approve'
            }
        }
    }
}