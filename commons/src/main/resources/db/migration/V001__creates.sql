
    create table tbs_hiscore (
       id  bigserial not null,
        create_time timestamp,
        delete_time timestamp,
        last_updated timestamp,
        match_id int8,
        user_id int8,
        primary key (id)
    );

    create table tbs_mines_match (
       id  bigserial not null,
        create_time timestamp,
        delete_time timestamp,
        last_updated timestamp,
        cleared int4 not null,
        data text,
        end_time timestamp,
        height int4 not null,
        mines int4 not null,
        mines_discovered int4 not null,
        score int4 not null,
        start_time timestamp,
        status int4,
        width int4 not null,
        user_id int8,
        primary key (id)
    );

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
        nickname varchar(60) not null,
        password varchar(200) not null,
        recovery_code varchar(200),
        recovery_expires timestamp,
        primary key (id)
    );
create index idx_user_hiscore on tbs_hiscore (user_id);
create index idx_user_match on tbs_mines_match (user_id);

    alter table tbs_user
       add constraint uk_user_mail unique (email);

    alter table tbs_user
       add constraint uk_user_nick unique (nickname);

    alter table tbs_hiscore
       add constraint FKbt2mrxsejkxpblmmr2ucs1fuw
       foreign key (match_id)
       references tbs_mines_match;

    alter table tbs_hiscore
       add constraint FK567r8ornynx38vcuq61cuu0jh
       foreign key (user_id)
       references tbs_user;

    alter table tbs_mines_match
       add constraint FKax85el9up7uuk74bconjwqce2
       foreign key (user_id)
       references tbs_user;
