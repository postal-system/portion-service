create table if not exists portion (
  id uuid primary key,
  letter_ids uuid[],
  sending_date timestamp
);



