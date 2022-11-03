create table if not exists questionnaire.account
(
    id       varchar(50)             not null
        primary key,
    username varchar(50)             null,
    password varchar(50)             null,
    name     varchar(50)             null,
    identity int                     null,
    phone    varchar(50) default '0' null,
    info     text                    null,
    state    int                     null
)
    collate = utf8_bin;

create index phone
    on questionnaire.account (phone);

create table if not exists questionnaire.`group`
(
    id          varchar(50) not null
        primary key,
    name        varchar(50) not null,
    description varchar(50) null,
    create_time datetime    not null,
    tenant_id   varchar(50) null,
    state       int         null
)
    collate = utf8_bin;

create table if not exists questionnaire.group_user
(
    group_id varchar(50) not null,
    user_id  varchar(50) not null,
    constraint group_user_FK
        foreign key (user_id) references questionnaire.account (id)
            on update cascade on delete cascade,
    constraint group_user_FK1
        foreign key (group_id) references questionnaire.`group` (id)
            on update cascade on delete cascade
)
    collate = utf8_bin;

create table if not exists questionnaire.question
(
    id      varchar(50) not null
        primary key,
    user_id varchar(50) null,
    info    varchar(50) null,
    type    varchar(50) null,
    constraint question_FK
        foreign key (user_id) references questionnaire.account (id)
            on update cascade on delete cascade
)
    collate = utf8_bin;

create table if not exists questionnaire.questionnaire
(
    id           varchar(50) not null
        primary key,
    name         varchar(50) not null,
    create_time  datetime    not null,
    type         varchar(50) not null comment '问卷种类领域:农类,科学类,生活类......',
    info         json        not null,
    creator_id   varchar(50) not null,
    tenant_id    varchar(50) not null,
    high_quality int         null,
    state        int         not null
)
    collate = utf8_bin;

create table if not exists questionnaire.account_questionnaire
(
    account_id       varchar(50) not null,
    tenant_id        varchar(50) not null,
    questionnaire_id varchar(50) not null,
    constraint account_questionnaire_FK
        foreign key (account_id) references questionnaire.account (id)
            on update cascade on delete cascade,
    constraint account_questionnaire_FK_1
        foreign key (questionnaire_id) references questionnaire.questionnaire (id)
)
    collate = utf8_bin;

create table if not exists questionnaire.released_questionnaire
(
    questionnaire_id varchar(50) not null
        primary key,
    relesed_time     datetime    not null,
    finished_time    datetime    not null,
    style            varchar(50) null comment '风格',
    type             varchar(50) null comment '问卷设置',
    answers          int         null comment '得到的问卷数',
    url              varchar(50) null comment '网址',
    state            int         null comment '状态',
    info             text        null comment '发布者信息,租户信息',
    target           text        null,
    tenant_id        varchar(50) not null comment '租户id'
)
    collate = utf8_bin;

create table if not exists questionnaire.answer
(
    id               varchar(50) not null
        primary key,
    questionnaire_id varchar(50) null,
    account_id       varchar(50) null comment '答者账号id',
    info             longtext    null,
    create_time      date        not null,
    constraint answer_FK
        foreign key (questionnaire_id) references questionnaire.released_questionnaire (questionnaire_id)
)
    collate = utf8_bin;

create table if not exists questionnaire.tenant_group
(
    tenant_id varchar(50)   not null,
    group_id  varchar(50)   not null,
    valid     int default 0 not null,
    constraint tenant_group_FK
        foreign key (tenant_id) references questionnaire.account (id)
            on update cascade on delete cascade
)
    collate = utf8_bin;

create table if not exists questionnaire.tenant_user
(
    tenant_id varchar(50) not null,
    user_id   varchar(50) not null,
    constraint tenant_account
        foreign key (tenant_id) references questionnaire.account (id)
            on update cascade on delete cascade,
    constraint user_account
        foreign key (user_id) references questionnaire.account (id)
            on update cascade on delete cascade
)
    collate = utf8_bin;

