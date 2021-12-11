#!groovy

pipeline {
    agent any
    tools {
        terraform 'terraform 1.1.0'
    }
    environment {
        TEST = 'TEST'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '100'))
        ansiColor('xterm')
    }
    stages {
        stage('Git'){
            steps {
                git credentialsId: 'Jenkins_DEVOPS', url: 'https://github.com/MAILLERC0/Projet_DEVOPS_MAILLER'
            }
        }
        stage('Terraform init') { 
            steps {
                sh label: '', script: 'terraform init'
            }
        }
        stage('Terraform apply') { 
            steps {
                sh label: '', script: 'terraform apply --auto-approve'
            }
        }
    }
}