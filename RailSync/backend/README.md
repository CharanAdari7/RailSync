# RailSync Backend

Spring Boot backend for the RailSync railway operations application.

## Stack

- Java 25
- Spring Boot 4.1.1
- Spring Web
- Spring Data JPA
- PostgreSQL
- Bean Validation
- Actuator

## Development database

PostgreSQL is provided through `docker-compose.yml`.

```bash
docker compose up -d
```

The default development connection is:

- Database: `railsync`
- Username: `postgres`
- Password: `postgres`
- Port: `5432`

Environment variables can override these values:

- `RAILSYNC_DB_URL`
- `RAILSYNC_DB_USERNAME`
- `RAILSYNC_DB_PASSWORD`

## Run

```bash
mvn spring-boot:run
```

The server listens on `http://localhost:8080`.

Health endpoint:

```text
GET /actuator/health
```

## First API vertical slice

Stations use the real railway station code as their persistence identity. No artificial station ID is introduced.

```text
GET    /api/stations
GET    /api/stations/{stationCode}
POST   /api/stations
PUT    /api/stations/{stationCode}
DELETE /api/stations/{stationCode}
```

Example request:

```json
{
  "stationCode": "SC",
  "stationName": "SECUNDERABAD JN",
  "city": "Hyderabad",
  "state": "Telangana",
  "latitude": 17.4344,
  "longitude": 78.5013,
  "platformCount": 10,
  "status": "ACTIVE"
}
```

No seed or mock data is loaded by the backend.
