alter table tests add column user_count int4 not null default 100;
alter table tests alter column user_count drop default;
