pipeline { 
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }
    stages {
        stage('Scan') { 
            steps { 
                withSonarQubeEnv(){
                    sh 'mvn clean verify sonar:sonar \
                        -Dsonar.projectKey=Teste \
                        -Dsonar.host.url=http://sonarqube:9000 \
                        -Dsonar.login=sqa_96ad088e81d382d7f0c06bad4e63d76c84612a06'
                }
            }
        }
      
    }
}