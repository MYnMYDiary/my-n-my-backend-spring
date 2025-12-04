CREATE TABLE users (
    id             UUID          PRIMARY KEY,
    created_at     TIMESTAMPTZ   NULL,
    updated_at     TIMESTAMPTZ   NULL,
    provider       VARCHAR(50)   NULL,
    provider_id    VARCHAR(100)  NULL,
    name           VARCHAR(100)  NULL,
    email          VARCHAR(255)  NULL,
    nickname       VARCHAR(100)  NULL,
    profile_image  VARCHAR(500)  NULL,
    password VARCHAR(100),
    role VARCHAR(100)
);

CREATE TABLE diary (
    id             BIGSERIAL      PRIMARY KEY,
    user_id        UUID           NOT NULL,
    category_id    BIGINT         NOT NULL,
    created_at     TIMESTAMPTZ    NULL,
    updated_at     TIMESTAMPTZ    NULL,
    year           VARCHAR(4)     NULL,
    month          VARCHAR(2)     NULL,
    title          VARCHAR(255)   NULL,
    content        TEXT           NULL,
    like_count     INTEGER        NULL,
    comment_count  INTEGER        NULL,
    CONSTRAINT fk_diary_user
       FOREIGN KEY (user_id) REFERENCES users(id)
);

ALTER TABLE users
    ADD COLUMN password VARCHAR(100);
ALTER TABLE users
    ADD COLUMN role VARCHAR(100);