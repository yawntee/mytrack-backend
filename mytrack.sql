create table project
(
    id      int auto_increment comment '项目id'
        primary key,
    name    varchar(255)         not null comment '项目名',
    content text                 not null comment '项目内容',
    enable  tinyint(1) default 0 not null
)
    comment '项目表';

create table user
(
    id       int auto_increment comment '用户id'
        primary key,
    username varchar(255)         not null comment '用户名',
    password char(60)             not null comment '密码(bcrypt)',
    name     varchar(255)         not null comment '用户昵称',
    role     tinytext             not null comment '角色',
    banned   tinyint(1) default 0 not null comment '是否已封禁',
    constraint user_username_uindex
        unique (username)
);

create table version
(
    id         int auto_increment comment '版本id'
        primary key,
    project_id int               null comment '所属项目id',
    code       varchar(255)      not null comment '版本号',
    status     tinyint default 0 not null comment '版本状态(0-开发中,1-测试中,2-已完成)',
    constraint version_project_id_fk
        foreign key (project_id) references project (id)
)
    comment '版本表';

create table bug
(
    id          bigint auto_increment comment 'bug id'
        primary key,
    version_id  int               not null comment '所属版本id',
    subject     tinytext          not null comment '摘要',
    content     text              not null comment '内容',
    assignee_id int               null comment '负责人',
    status      tinyint default 0 not null comment 'bug状态(0-未解决,1-已解决待验证,2-已验证)',
    constraint bug_user_id_fk
        foreign key (assignee_id) references user (id),
    constraint bug_version_id_fk
        foreign key (version_id) references version (id)
)
    comment 'bug表';

