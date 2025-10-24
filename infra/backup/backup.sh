#!/bin/sh
set -eu
STAMP=$(date +%Y%m%d-%H%M%S)
OUT=/var/backups/${STAMP}-${POSTGRES_DB}.sql.gz
echo "[backup] Dumping to $OUT"
pg_dump -h "$PGHOST" -U "$POSTGRES_USER" -d "$POSTGRES_DB" -F p | gzip -9 > "$OUT"
echo "[backup] Done"
find /var/backups -type f -name "*.sql.gz" -mtime +7 -delete || true
