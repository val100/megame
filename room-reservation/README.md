# Room Reservation App

## Run locally (Docker Compose)

Prereqs: Docker Desktop/Engine

```bash
# from repo root or room-reservation directory
cd room-reservation

# Build and start Postgres + app
docker compose up --build

# App is now on http://localhost:8080
```

To stop and remove containers:
```bash
docker compose down -v
```

## Config
- Active profile: `prod` (Postgres)
- DB creds: user `roomres`, password `roomres`, db `roomres`
- Change via env vars: `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`

## Build jar locally
```bash
mvn -B -ntp clean package
```

## CI
GitHub Actions workflow `room-reservation-ci.yml` builds and tests on every branch. On `main`/`master`, it also builds and pushes a Docker image to GHCR at `ghcr.io/<owner>/<repo>/room-reservation:latest`.

## Next steps
- Add REST controllers and DTOs
- Wire authentication and payments
- Add frontend
