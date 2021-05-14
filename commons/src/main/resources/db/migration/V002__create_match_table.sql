
    create table tbs_mines_match (
       id  bigserial not null,
        create_time timestamp,
        delete_time timestamp,
        last_updated timestamp,
        cleared int4 not null,
        data varchar(255),
        end_time timestamp,
        height int4 not null,
        mines int4 not null,
        mines_discovered int4 not null,
        start_time timestamp,
        width int4 not null,
        user_id int8,
        status int4,
        primary key (id)
    );

    alter table tbs_mines_match 
       drop constraint if exists idx_user_match;

    alter table tbs_mines_match 
       add constraint idx_user_match unique (user_id);

    alter table tbs_mines_match 
       add constraint FKax85el9up7uuk74bconjwqce2 
       foreign key (user_id) 
       references tbs_user;
