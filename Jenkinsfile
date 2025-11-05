pipeline {
  agent any

  options {
    timestamps()
    disableConcurrentBuilds()
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
        // Sørg for at mvnw kan køres i Linux-containeren
        sh 'chmod +x mvnw'
      }
    }

    stage('Test') {
      steps {
        // Kør KUN tests via Maven Wrapper
        sh './mvnw -B -U -DskipTests=false test'
      }
      post {
        // Indsaml Surefire/Failsafe-rapporter, hvis de findes
        always {
          junit allowEmptyResults: true, testResults: '''
            **/target/surefire-reports/*.xml,
            **/target/failsafe-reports/*.xml
          '''
        }
      }
    }
  }

  post {
    success { echo 'ja' }   // alt gik igennem
    failure { echo 'nej' }  // noget fejlede
  }
}
