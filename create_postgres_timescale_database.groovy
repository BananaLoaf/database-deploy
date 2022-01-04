pipeline {
    agent any

    parameters {
        string(name: 'DATABASE_NAME', defaultValue: 'postgres', description: 'Name of the database to create')
    }

    stages {
        stage("Creating") {
            parallel {
                stage("Postgres") {
                    steps {
                        withCredentials([usernamePassword(credentialsId: '3ed0d2c1-2361-4c2a-9fa7-2812f994ce6c', passwordVariable: 'POSTGRES_ADMIN_PASSWORD', usernameVariable: 'POSTGRES_ADMIN_USER')]) {
                            sh "docker exec postgres su postgres -c \"psql -U ${POSTGRES_ADMIN_USER} -c 'create database ${params.DATABASE_NAME};'\""
                        }
                    }
                }
                stage("Timescale") {
                    steps {
                        withCredentials([usernamePassword(credentialsId: 'd6e4207a-d150-4f55-b58c-aa52812f2154', passwordVariable: 'TIMESCALE_ADMIN_PASSWORD', usernameVariable: 'TIMESCALE_ADMIN_USER')]) {
                            sh "docker exec timescale su postgres -c \"psql -U ${TIMESCALE_ADMIN_USER} -c 'create database ${params.DATABASE_NAME};'\""
                        }
                    }
                }
            }
        }
    }
}
