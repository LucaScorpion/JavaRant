pipeline {
    agent any

    stages {
        stage('Clean') {
            steps {
                mvn 'clean'
            }
        }
        stage('Build') {
            steps {
                mvn 'install'
            }
        }
    }
}

def mvn(String goals) {
    def mvnHome = tool 'Maven 3'
    sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore $goals"
}
