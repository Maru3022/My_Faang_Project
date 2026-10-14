--liquibase formatted sql
--changeset devpath:002-identity-and-legal
CREATE SCHEMA identity;
CREATE TABLE identity.identity_mapping (
    provider TEXT NOT NULL,
    subject TEXT NOT NULL,
    user_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (provider, subject)
);
CREATE INDEX identity_mapping_user_idx ON identity.identity_mapping (user_id);

CREATE SCHEMA legal;
CREATE TABLE legal.document_version (
    document_type TEXT PRIMARY KEY,
    version INT NOT NULL,
    effective_from TIMESTAMPTZ NOT NULL
);
CREATE TABLE legal.user_acceptance (
    user_id UUID NOT NULL,
    document_type TEXT NOT NULL,
    version INT NOT NULL,
    accepted_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (user_id, document_type)
);

INSERT INTO legal.document_version (document_type, version, effective_from) VALUES
    ('PRIVACY_POLICY', 1, now()),
    ('TERMS_OF_SERVICE', 1, now());