--liquibase formatted sql
--changeset devpath:001-foundation
CREATE SCHEMA profile;
CREATE TABLE profile.user_profile (
    user_id UUID PRIMARY KEY,
    display_name VARCHAR(100),
    locale VARCHAR(35) NOT NULL DEFAULT 'en',
    timezone VARCHAR(64) NOT NULL DEFAULT 'UTC',
    daily_minutes INTEGER CHECK (daily_minutes BETWEEN 5 AND 480),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    version BIGINT NOT NULL DEFAULT 0
);
CREATE TABLE event_publication (
    id UUID PRIMARY KEY,
    listener_id TEXT NOT NULL,
    event_type TEXT NOT NULL,
    serialized_event TEXT NOT NULL,
    publication_date TIMESTAMP WITH TIME ZONE NOT NULL,
    completion_date TIMESTAMP WITH TIME ZONE
);
CREATE INDEX event_publication_incomplete_idx ON event_publication (publication_date) WHERE completion_date IS NULL;
