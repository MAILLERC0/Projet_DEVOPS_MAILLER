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