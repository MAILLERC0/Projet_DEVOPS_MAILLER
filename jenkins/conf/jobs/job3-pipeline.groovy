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
        stage('Git'){
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/MAILLERC0/Projet_DEVOPS_MAILLER']]])   
            }
        }
        stage('mv terraform folder') {
            steps {
                sh 'cp -r ./ansible/* .'
            }
        }
        stage('RUN ansible') {
            steps {
                sh 'ansible-playbook -i inventory --user deploy playbook_install_apache.yml'
            }
        }
    }
}