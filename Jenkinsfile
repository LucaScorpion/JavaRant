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
        stage('Results') {
            steps {
                junit '**/target/*-reports/TEST-*.xml'
            }
        }
    }
}

def mvn(String goals) {
    def mvnHome = tool 'Maven 3'
    sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore -Djavarant.test.smoke $goals"
}
