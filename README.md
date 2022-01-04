# database-deploy

## Manual deployment

```bash
# Postgres
export POSTGRES_ADMIN_USER=postgres
export POSTGRES_ADMIN_PASSWORD=password

# TimescaleDB
export TIMESCALE_ADMIN_USER=timescale
export TIMESCALE_ADMIN_PASSWORD=password

docker-compose -p postgres-stack -f postgres-compose.yaml up -d --build
docker-compose -p timescale-stack -f timescale-compose.yaml up -d --build
```

Create database

```bash
docker exec -it postgres su postgres -c "psql -U $POSTGRES_ADMIN_USER -c 'create database sample_database_name;'"
docker exec -it timescale su postgres -c "psql -U $TIMESCALE_ADMIN_USER -c 'create database sample_database_name;'"
```