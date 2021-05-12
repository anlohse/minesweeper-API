
    create table tbs_user (
       id  bigserial not null,
        create_time timestamp,
        delete_time timestamp,
        last_updated timestamp,
        activation_code varchar(200),
        activation_expires timestamp,
        birth_date date,
        dt_locked timestamp,
        email varchar(100) not null,
        last_name varchar(120) not null,
        name varchar(60) not null,
        password varchar(200) not null,
        recovery_code varchar(200),
        recovery_expires timestamp,
        primary key (id)
    );

    alter table tbs_user 
       add constraint uk_user_mail unique (email);
