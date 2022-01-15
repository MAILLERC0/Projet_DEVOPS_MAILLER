#!groovy

pipeline {
    agent any
    tools {
        terraform 'terraform-11'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '100'))
        ansiColor('xterm')
    }
    stages {
        stage('mv terraform folder') {
            steps {
                sh 'cp -r ./terraform/* .'
            }
        }
        stage('Terraform init') { 
            steps {
                sh label: '', script: 'terraform init'
            }
        }
        stage('Terraform fmt') { 
            steps {
                sh label: '', script: 'terraform fmt'
            }
        }
        stage('Terraform validate') { 
            steps {
                sh label: '', script: 'terraform validate'
            }
        }
        stage('Terraform apply/destroy') { 
            steps {
                sh 'ls'
                sh label: '', script: 'terraform ${action} --auto-approve'
            }
        }
    }
}