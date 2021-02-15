CREATE TABLE power_stats
(
    id           SERIAL PRIMARY KEY,
    strength     SMALLINT         NOT NULL,
    agility      SMALLINT         NOT NULL,
    dexterity    SMALLINT         NOT NULL,
    intelligence SMALLINT         NOT NULL,
    created_at   TIMESTAMPTZ      NOT NULL DEFAULT now(),
    updated_at   TIMESTAMPTZ      NOT NULL DEFAULT now()
);
