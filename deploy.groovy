pipeline {
    agent any

    stages {
        stage("Deploying") {
            parallel {
                stage("Postgres") {
                    steps {
                        withCredentials([usernamePassword(credentialsId: '3ed0d2c1-2361-4c2a-9fa7-2812f994ce6c', passwordVariable: 'POSTGRES_ADMIN_PASSWORD', usernameVariable: 'POSTGRES_ADMIN_USER')]) {
                            sh "docker-compose -p postgres-stack -f postgres-compose.yaml up -d --build"
                        }
                    }
                }
                stage("Timescale") {
                    steps {
                        withCredentials([usernamePassword(credentialsId: 'd6e4207a-d150-4f55-b58c-aa52812f2154', passwordVariable: 'TIMESCALE_ADMIN_PASSWORD', usernameVariable: 'TIMESCALE_ADMIN_USER')]) {
                            sh "docker-compose -p timescale-stack -f timescale-compose.yaml up -d --build"
                        }
                    }
                }
            }
            
        }
    }
}
