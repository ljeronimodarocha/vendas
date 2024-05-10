pipeline { 
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }
    stages {
        stage('Scan') { 
            steps { 
              withSonarQubeEnv(installationName: 'Sonar'){
                    sh '/var/jenkins_home/tools/hudson.tasks.Maven_MavenInstallation/default/bin/mvn clean verify sonar:sonar \
                        -Dsonar.projectKey=Teste \
                        -Dsonar.host.url=http://sonarqube:9000 \
                        -Dsonar.sources=. \
                        -Dsonar.login=sqa_96ad088e81d382d7f0c06bad4e63d76c84612a06 \
                        -Dsonar.filesize.limit=100 \
                        -Dsonar.tests.inclusions=src/test/** \
                        -Dsonar.webhooks.project=http://jenkins'
                }
            }
        }
        stage ("Quality Gate") {
            steps {
                timeout(time: 60, unit: 'SECONDS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}