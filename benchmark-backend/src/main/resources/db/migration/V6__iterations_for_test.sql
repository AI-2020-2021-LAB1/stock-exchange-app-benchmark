alter table tests add column if not exists iterations int4 not null default 0;
alter table tests alter column iterations drop default;
