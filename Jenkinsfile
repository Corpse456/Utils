pipeline {
    agent {
        label 'delphi'
    }
    options {
        timestamps()
    }
    parameters {
        booleanParam(name: 'UPDATE_DB', defaultValue: false, description: '')
    }
    stages {
        stage('Build') {
            steps {
                script {
                    if ("Branch indexing" == currentBuild.getBuildCauses()?.first().shortDescription) {
                        return
                    }

                    env._DB_ACCOUNT = env.BRANCH_NAME
                    build job: 'TECH-817_Deploy_Pipeline', parameters: [string(name: 'UPDATE_DB', value: env.UPDATE_DB), string(name: '_DB_ACCOUNT', value: env._DB_ACCOUNT)]
                }
            }
        }
    }
    post {
        always {
            script {
                currentBuild.result = currentBuild.currentResult
            }
            junit allowEmptyResults: true, testResults: '**/test-results/test/*.xml'
        }
        failure {
            sendEmail()
        }
        changed {
            script {
                if (currentBuild.result != 'FAILURE') {
                    sendEmail()
                }
            }
        }
        success {
            cleanWs notFailBuild: true
        }
    }
}
