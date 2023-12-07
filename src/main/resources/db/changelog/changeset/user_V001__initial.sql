create table users
(
    id         bigint generated always as identity unique,
    name       varchar(64) not null unique,
    email      varchar(64) not null unique,
    password   varchar(255) not null,
    created_at timestamptz DEFAULT current_timestamp
);

create table quotes
(
    id         bigint primary key generated always as identity unique,
    content    varchar(255) not null,
    user_id    bigint       not null,
    rating     int         default 0,
    version int default 0,
    created_at timestamptz DEFAULT current_timestamp,
    updated_at timestamptz DEFAULT current_timestamp,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);