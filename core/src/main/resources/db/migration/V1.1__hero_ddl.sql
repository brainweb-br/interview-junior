CREATE TABLE hero
(
    id             Serial PRIMARY KEY,
    name           VARCHAR(255)     NOT NULL UNIQUE,
    race           VARCHAR(255)     NOT NULL,
    power_stats_id Serial             NOT NULL,
    enabled        BOOLEAN          NOT NULL DEFAULT TRUE,
    created_at     TIMESTAMPTZ      NOT NULL DEFAULT now(),
    updated_at     TIMESTAMPTZ      NOT NULL DEFAULT now(),
    CHECK ( race IN ('HUMAN', 'ALIEN', 'DIVINE', 'CYBORG')),
    CONSTRAINT FK_power_stats FOREIGN KEY (power_stats_id) REFERENCES power_stats(id)
);
