#!groovy

pipeline {
    agent any
    tools {
        maven 'maven'
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
                git 'https://github.com/Ozz007/sb3t'
            }
        }
        stage('Build') { 
            steps {
                script {
                    sh 'mvn clean compile'
                }
            }
        }
        stage('Test') { 
            when {
                expression {params.SKIP_TESTS == false}
            }
            steps {
                script {
                    sh 'mvn test'
                }
            }
        }
        stage('Package') { 
            steps {
                script {
                    sh 'mvn package'
                }
            }
        }
    }
}