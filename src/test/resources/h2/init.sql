DROP TABLE IF EXISTS subtask;
DROP TABLE IF EXISTS epic;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS status;
DROP TABLE IF EXISTS "user";


create table "user" (
    id serial primary key,
    name varchar
);

insert into "user" values (9999, 'test_user');
insert into "user" values (10000, 'test_user2');

--cоздание таблицы с статусами
create table status (
                        id serial primary key,
                        name varchar
);

--создание таблицы с тасками
create table task (
                      id serial primary key,
                      name varchar,
                      description varchar,
                      status_id int REFERENCES status (id),
                      user_id int default 9999 REFERENCES "user" (id)
);

--создание таблицы с епиками
create table epic  (
                       id serial primary key,
                       name varchar,
                       description varchar,
                       status_id int REFERENCES status (id),
                       user_id int default 9999 REFERENCES "user" (id)
);

--создание таблицы с сабтасками
create table subtask (
                         id serial primary key,
                         name varchar,
                         description varchar,
                         status_id int REFERENCES status (id),
                         epic_id int REFERENCES epic (id) ON DELETE CASCADE
);

-- добавление статуса в таблицу
INSERT INTO status (name) VALUES ('NEW');
INSERT INTO status (name) VALUES ('IN_PROGRESS');
INSERT INTO status (name) VALUES ('DONE');



