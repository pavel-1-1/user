create table voting_schedule
(
    quote_id bigint primary key,
    update_at varchar(100000) not null,
    rating varchar(50000) not null,
     CONSTRAINT fk_quote_id FOREIGN KEY (quote_id)
      REFERENCES quotes (id) ON DELETE CASCADE
)