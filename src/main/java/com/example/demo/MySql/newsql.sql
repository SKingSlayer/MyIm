use s1;
DROP TABLE chat_record;
CREATE  TABLE  IF NOT EXISTS chat_record
(
    `id` INT(11) AUTO_INCREMENT,
    `user_id` INT(11),
    `friend_id` INT(11),
    `record` TEXT,
    `time_stamp` DATETIME,
    PRIMARY KEY (`id`)
)ENGINE=InnoDb  DEFAULT CHARSET=utf8;
alter table chat_record add fulltext index idx_full_keyword(record);
INSERT INTO chat_record VALUES (1,1,2,'hello world',NOW());
INSERT INTO chat_record VALUES (2,2,1,'hello world',NOW());
INSERT INTO chat_record VALUES (3,2,1,'ni shi hao rn',NOW());

CREATE  TABLE  IF NOT EXISTS group_record
(
    `id` INT(11) AUTO_INCREMENT,
    `user_id` INT(11),
    `group_id` INT(11),
    `record` TEXT,
    `time_stamp` DATETIME,
    PRIMARY KEY (`id`)
)ENGINE=InnoDb  DEFAULT CHARSET=utf8;
INSERT INTO group_record VALUES (1,1,1,'hello world 1',NOW());
INSERT INTO group_record VALUES (2,2,1,'hello world 2',NOW());
CREATE TABLE IF NOT EXISTS test  (
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `rtd` float(11,2) NOT NULL,
                                     `flag` char(1) NOT NULL,
                                     `sample_factor` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `pool_identify` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     `sample_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE  TABLE  IF NOT EXISTS phb
(
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `sender_id` INT(11),
    `receiver_id` INT(11),
    `money` INT(11),
    `time_stamp` DATETIME,
    PRIMARY KEY (`id`)
)ENGINE=InnoDb  DEFAULT CHARSET=utf8;
INSERT INTO phb VALUES (1,1,2,300,NOW());
INSERT INTO phb VALUES (2,1,3,500,NOW());
INSERT INTO phb VALUES (3,1,2,300,NOW());
INSERT INTO phb VALUES (4,2,1,300,NOW());

CREATE  TABLE  IF NOT EXISTS  users
(
    `user_id`   INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(20) NOT NULL ,
    `password` VARCHAR(20) NOT NULL ,
    `email` VARCHAR(20) NOT NULL ,
    `money` INT(32),
    PRIMARY KEY (`user_id`)
)ENGINE=InnoDb  DEFAULT CHARSET=utf8;
INSERT INTO users VALUES (1,'xiaoming','xiaoming','706422117@qq.com',700);
INSERT INTO users VALUES (2,'xiaohong','xiaohong','13973905146@qq.com',500);
INSERT INTO users VALUES (3,'xiaobai','xiaobai','15243907814@qq.com',600);
INSERT INTO users VALUES (4,'xiaoli','xiaoli','19918101654@qq.com',600);
INSERT INTO users VALUES (5,'xiaoqing','xiaoqing','17055642394@qq.com',600);
INSERT INTO users VALUES (6,'xiaoli','xiaoli','18128920529@qq.com',600);



CREATE  TABLE  IF NOT EXISTS friend_list
(
    `user_id` INT NOT NULL,
    `friend_id` INT NOT NULL,
    `friend_name` VARCHAR(20) NOT NULL ,
    `time_stamp` DATETIME,
    `umsg` INT NOT NULL ,
    PRIMARY KEY (user_id,friend_id)
)ENGINE=InnoDb  DEFAULT CHARSET=utf8;
# INSERT INTO friend_list VALUES (1,2,'xiaohong',NOW(),1);
INSERT INTO friend_list VALUES (2,1,'xiaoming',NOW(),1);
INSERT INTO friend_list VALUES (2,3,'xiaobai',NOW(),1);
INSERT INTO friend_list VALUES (3,2,'xiaohong',NOW(),1);
INSERT INTO friend_list VALUES (1,5,'xiaoli',NOW(),1);
INSERT INTO friend_list VALUES (5,1,'xiaoming',NOW(),1);
INSERT INTO friend_list VALUES (1,3,'xiaobai',NOW(),1);
DROP TABLE user;
CREATE  TABLE  IF NOT EXISTS user
(
    `id` INT(11) AUTO_INCREMENT,
    `name` varchar(20),
    `age` INT(11),

    PRIMARY KEY (`id`)
)ENGINE=InnoDb  DEFAULT CHARSET=utf8;
INSERT INTO user VALUES (1,'xiaoming',20);

alter table chat_record add fulltext

    DROP TABLE chat_record;
CREATE  TABLE  IF NOT EXISTS chat_record
(
    `id` INT(11) AUTO_INCREMENT,
    `user_id` INT(11),
    `friend_id` INT(11),
    `record` TEXT,
    `time_stamp` DATETIME,
    PRIMARY KEY (`id`)
)ENGINE=InnoDb  DEFAULT CHARSET=utf8;
alter table chat_record add fulltext index idx_full_keyword(record);
INSERT INTO chat_record VALUES (1,1,2,'hello world',NOW());
INSERT INTO chat_record VALUES (2,2,1,'hello world',NOW());

CREATE  TABLE  IF NOT EXISTS group_record
(
    `id` INT(11) AUTO_INCREMENT,
    `user_id` INT(11),
    `group_id` INT(11),
    `record` TEXT,
    `time_stamp` DATETIME,
    PRIMARY KEY (`id`)
)ENGINE=InnoDb  DEFAULT CHARSET=utf8;
INSERT INTO group_record VALUES (1,1,1,'hello world 1',NOW());
INSERT INTO group_record VALUES (2,2,1,'hello world 2',NOW());

DROP TABLE  member;
CREATE  TABLE  IF NOT EXISTS member
(

    `user_id` INT(11),
    `group_id` INT(11),
    `user_name` VARCHAR(20),
    `group_name` VARCHAR(20),
    `umsg` INT(11),
    `time_stamp` DATETIME,
    PRIMARY KEY (`user_id`,`group_id`)
)ENGINE=InnoDb  DEFAULT CHARSET=utf8;
create index ig on member (`group_id`,user_id);
INSERT INTO member value (1,1,'xiaoming','group1',0,NOW());
INSERT INTO member value (2,1,'xiaohong','group1',0,NOW());
INSERT INTO member value (1,2,'xiaoming','group2',1,NOW());
INSERT INTO member value (2,2,'xiaohong','group2',1,NOW());


DROP  TABLE  my_group;
CREATE  TABLE  IF NOT EXISTS my_group
(
    `group_id` INT(11) AUTO_INCREMENT,
    `membership` INT(11),
    `group_name` varchar(20),
    `create_time` TIMESTAMP,
    UNIQUE (group_name),
    PRIMARY KEY (`group_id`)
)ENGINE=InnoDb  DEFAULT CHARSET=utf8;
INSERT INTO my_group VALUES (1,2,'group1',NOW());
INSERT INTO my_group VALUES (2,2,'group2',NOW());



create table fulltext_test (
                               id int(11) NOT NULL AUTO_INCREMENT,
                               content text NOT NULL,
                               tag varchar(255),
                               PRIMARY KEY (id),
                               FULLTEXT KEY content_tag_fulltext(content,tag)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE alive_user;
CREATE  TABLE  IF NOT EXISTS alive_user
(
    `user_id` INT(11) AUTO_INCREMENT,
    `last_talk_time` DATETIME,
    PRIMARY KEY (user_id)
)ENGINE=InnoDb  DEFAULT CHARSET=utf8;

CREATE TABLE fts_a (
                       FTS_DOC_ID BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
                       body TEXT,
                       PRIMARY KEY(FTS_DOC_ID)
);
INSERT INTO fts_a
SELECT NULL, 'Please poorridge in the pot';
INSERT INTO fts_a
SELECT NULL, 'Please poorridge hot, pease porridge cold';
INSERT INTO fts_a
SELECT NULL, 'Nine days old';
INSERT INTO fts_a
SELECT NULL, 'Some like it hot, some like it cold';
INSERT INTO fts_a
SELECT NULL, 'Some like it in the pot';
INSERT INTO fts_a
SELECT NULL, 'Nine days old';
INSERT INTO fts_a
SELECT NULL, 'I like code days';
CREATE FULLTEXT INDEX idx_fts ON fts_a(body);