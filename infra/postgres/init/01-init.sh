#!/bin/sh
set -eu
echo "[initdb] Creating roles and users..."

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-SQL
  -- logical roles
  DO $$
  BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'admin') THEN
      CREATE ROLE admin NOLOGIN;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'auditor') THEN
      CREATE ROLE auditor NOLOGIN;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'app_user') THEN
      CREATE ROLE app_user NOLOGIN;
    END IF;
  END$$;

  -- env-driven users
  DO $$
  BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = '$PG_APP_USER') THEN
      CREATE USER "$PG_APP_USER" WITH PASSWORD '$PG_APP_PASSWORD';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = '$PG_AUDITOR_USER') THEN
      CREATE USER "$PG_AUDITOR_USER" WITH PASSWORD '$PG_AUDITOR_PASSWORD';
    END IF;
  END$$;

  -- map users to roles
  GRANT app_user TO "$PG_APP_USER";
  GRANT auditor  TO "$PG_AUDITOR_USER";
  GRANT admin    TO "$POSTGRES_USER";

  CREATE EXTENSION IF NOT EXISTS pgcrypto;
SQL

echo "[initdb] Roles & users created."
