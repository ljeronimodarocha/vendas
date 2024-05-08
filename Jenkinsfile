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
                        -Dsonar.login=sqp_fac72501b99112c4ad181d52b848072a8e0c18e1'
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