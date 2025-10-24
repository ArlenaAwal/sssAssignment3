CREATE TABLE roles (id BIGSERIAL PRIMARY KEY, name VARCHAR(64) UNIQUE NOT NULL);
CREATE TABLE users (id BIGSERIAL PRIMARY KEY, username VARCHAR(128) UNIQUE NOT NULL, password_hash VARCHAR(255) NOT NULL, mfa_secret VARCHAR(64), enabled BOOLEAN NOT NULL DEFAULT TRUE);
CREATE TABLE user_roles (user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE, role_id BIGINT NOT NULL REFERENCES roles(id), PRIMARY KEY(user_id, role_id));
CREATE TABLE elections (id UUID PRIMARY KEY, code VARCHAR(64) UNIQUE NOT NULL, name VARCHAR(256) NOT NULL, election_salt VARCHAR(64) NOT NULL, start_time_utc TIMESTAMPTZ NOT NULL, end_time_utc TIMESTAMPTZ NOT NULL, active BOOLEAN NOT NULL);
CREATE TABLE votes (id BIGSERIAL PRIMARY KEY, election_id UUID NOT NULL REFERENCES elections(id), candidate_hash VARCHAR(128) NOT NULL, per_vote_salt VARCHAR(64) NOT NULL, timestamp_utc TIMESTAMPTZ NOT NULL, receipt_id UUID NOT NULL UNIQUE, prev_hash VARCHAR(128) NOT NULL, row_hash VARCHAR(128) NOT NULL, geo_country VARCHAR(64), geo_region VARCHAR(128));
CREATE INDEX idx_votes_election ON votes(election_id);
CREATE INDEX idx_votes_receipt ON votes(receipt_id);
CREATE TABLE audit_logs (id BIGSERIAL PRIMARY KEY, actor VARCHAR(128) NOT NULL, action VARCHAR(128) NOT NULL, resource VARCHAR(256) NOT NULL, at_utc TIMESTAMPTZ NOT NULL, ip VARCHAR(64), geo VARCHAR(128));
CREATE INDEX idx_audit_when ON audit_logs(at_utc);
