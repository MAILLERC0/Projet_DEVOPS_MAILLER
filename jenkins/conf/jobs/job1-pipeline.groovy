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
                sh 'mvn clean compile'
            }
        }
        stage('RUN Tests') { 
            when {
                expression {params.SKIP_TESTS == false}
            }
            steps {
                sh 'mvn test'
            }
        }
        stage('Jar creation') { 
            steps {
                sh 'mvn package'
            }
        }
        stage('Move .jar in Jenkins Workspace'){                         
            steps{  
                sh "mv /var/jenkins_home/workspace/CI/Job1/sb3t-ws/target/sb3t-ws-1.0-SNAPSHOT.jar /var/jenkins_home/workspace/CI/Job1/${params.'VERSION'}-${params.'VERSION_TYPE'}"
            }
        }
        stage('Terraform job loading') {
            steps {
                build job: 'job2-pipeline', parameters: [choice(name: 'action', value: 'apply')]
            }
        }
    }
}