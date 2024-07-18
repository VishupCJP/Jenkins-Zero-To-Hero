def call() {
    pipeline {
        agent any
        stages {
            stage('Build') {
                steps {
                    echo 'Building...'
                    // Your build steps here
                }
            }
            stage('Test') {
                steps {
                    echo 'Testing...'
                    // Your test steps here
                }
            }
            stage('Deploy') {
                steps {
                    echo 'Deploying...'
                    // Your deploy steps here
                }
            }
        }
        post {
            failure {
                script {
                    currentBuild.result = 'FAILURE'
                }
                mail(to: 'vishwanath.p@cloudjournee.com',
                     subject: "Job '${env.JOB_NAME}' (${env.BUILD_NUMBER}) failed",
                     body: "Job '${env.JOB_NAME}' (${env.BUILD_NUMBER}) failed. More info at ${env.BUILD_URL}")
                slackSend(channel: '#jenkins',
                          color: 'danger',
                          message: "Job '${env.JOB_NAME}' (${env.BUILD_NUMBER}) failed. More info at ${env.BUILD_URL}")
            }
            success {
                slackSend(channel: '#jenkins',
                          color: 'good',
                          message: "Job '${env.JOB_NAME}' (${env.BUILD_NUMBER}) succeeded. More info at ${env.BUILD_URL}")
            }
        }
    }
}
