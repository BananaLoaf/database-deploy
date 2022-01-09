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
                        withCredentials([usernamePassword(credentialsId: 'postgres-admin-user-credentials', passwordVariable: 'POSTGRES_ADMIN_PASSWORD', usernameVariable: 'POSTGRES_ADMIN_USER')]) {
                            sh "docker exec postgres su postgres -c \"psql -U ${POSTGRES_ADMIN_USER} -c 'create database ${params.DATABASE_NAME};'\""
                        }
                    }
                }
                stage("Timescale") {
                    steps {
                        withCredentials([usernamePassword(credentialsId: 'timescale-admin-user-credentials', passwordVariable: 'TIMESCALE_ADMIN_PASSWORD', usernameVariable: 'TIMESCALE_ADMIN_USER')]) {
                            sh "docker exec timescale su postgres -c \"psql -U ${TIMESCALE_ADMIN_USER} -c 'create database ${params.DATABASE_NAME};'\""
                        }
                    }
                }
            }
        }
    }
}
