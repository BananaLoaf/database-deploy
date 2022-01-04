pipeline {
    agent any

    parameters {
        string(name: 'DATABASE_NAME', defaultValue: 'postgres', description: 'Name of the database to create')
    }

    stages {
        stage("Creating") {
            steps {
                withCredentials([usernamePassword(credentialsId: '3ed0d2c1-2361-4c2a-9fa7-2812f994ce6c', passwordVariable: 'POSTGRES_ADMIN_PASSWORD', usernameVariable: 'POSTGRES_ADMIN_USER')]) {
                    sh "docker exec -it postgres su postgres -c \"psql -U ${POSTGRES_ADMIN_USER} -c 'create database ${params.DATABASE_NAME};'\""
                }
            }
        }
    }
}
