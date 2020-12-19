alter table responses add column memory_used decimal not null default 0;
alter table responses add column memory_usage decimal not null default 0;
alter table responses add column cpu_usage decimal not null default 0;

alter table responses alter column memory_used drop default;
alter table responses alter column memory_usage drop default;
alter table responses alter column cpu_usage drop default;
