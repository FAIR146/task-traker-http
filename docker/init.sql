DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS epic;
DROP TABLE IF EXISTS subTask;

create table task (
    id serial primary key,
    name varchar,
    description varchar,
    status varchar
);

create table epic (
    id serial primary key,
    name varchar,
    description varchar,
    status varchar
);

create table subTask (
    id serial primary key,
    name varchar,
    description varchar,
    status varchar,
    epic_id int REFERENCES epic (id)
);