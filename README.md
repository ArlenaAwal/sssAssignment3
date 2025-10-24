# eVote Secure — E2E (Frontend + Backend + Postgres + NGINX + Backups)

## Run with Docker (recommended)
```bash
mvn -DskipTests package
cd infra
cp .env.example .env
# edit .env -> strong passwords + APP_JWT_SECRET + PUBLIC_HOST
# add TLS certs to infra/reverse-proxy/tls/fullchain.pem and privkey.pem
docker compose --env-file .env up -d --build
```
Open `https://<PUBLIC_HOST>`.

Demo users (remove in prod): `admin/admin`, `voter/voter`.

## Dev (no Docker)
- Backend: `mvn spring-boot:run` (H2 memory DB)
- Frontend: `cd frontend && npm install && npm run dev`

## Endpoints
- `POST /api/auth/login` → JWT (MFA TOTP hook ready)
- `GET  /api/auth/me` → current user & roles
- `POST /api/admin/elections` (ADMIN) → create election
- `GET  /api/admin/elections` (ADMIN/AUDITOR) → list
- `POST /api/ballot/cast` (VOTER) → anonymous vote → receipt + timestamp (+ optional geo headers)
