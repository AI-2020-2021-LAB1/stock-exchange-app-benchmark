alter table tests add column stock_count int4 not null default 0;
alter table tests alter column stock_count drop default;
