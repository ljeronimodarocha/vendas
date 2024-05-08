pipeline { 
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }
    stages {
        stage('Scan') { 
            steps { 
                withSonarQubeEnv(installationName: 'First'){
                    sh 'mvn clean verify sonar:sonar \
                        -Dsonar.projectKey=Teste \
                        -Dsonar.host.url=http://sonarqube:9001 \
                        -Dsonar.login=sqp_7e7128e4cfcb3655aced4bcf12a02255b6d885b4'
                }
            }
        }
        stage('Quality Gate'){
            steps {
              timeout(time: 30, unit: 'SECONDS'){
                waitForQualityGate abortPipeline: true
              }
            }
        }
    }
}