pipeline {
    agent any

    stages {
        stage("Deploying") {
            parallel {
                stage("Postgres") {
                    steps {
                        withCredentials([usernamePassword(credentialsId: 'postgres-admin-user-credentials', passwordVariable: 'POSTGRES_ADMIN_PASSWORD', usernameVariable: 'POSTGRES_ADMIN_USER')]) {
                            sh "docker-compose -p postgres-stack -f postgres-compose.yaml up -d --build"
                        }
                    }
                }
                stage("Timescale") {
                    steps {
                        withCredentials([usernamePassword(credentialsId: 'timescale-admin-user-credentials', passwordVariable: 'TIMESCALE_ADMIN_PASSWORD', usernameVariable: 'TIMESCALE_ADMIN_USER')]) {
                            sh "docker-compose -p timescale-stack -f timescale-compose.yaml up -d --build"
                        }
                    }
                }
            }
            
        }
    }
}
