use MyData;
CREATE TABLE IF NOT EXISTS test  (
          `id` int(11) NOT NULL AUTO_INCREMENT,
          `rtd` float(11,2) NOT NULL,
          `flag` char(1) NOT NULL,
          `sample_factor` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
          `pool_identify` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
          `sample_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
          PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
create  table  if not exists CharHistory
(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `send` int(32),
    `recv` int(32),
    `myTime` datetime not null,
    `history` varchar(1000) not null,
    primary key (`id`)
)ENGINE=myisam  DEFAULT CHARSET=utf8;
create  table  if not exists Rm
(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `rmNum` int(32),
    primary key (`id`)
)ENGINE=InnoDb  DEFAULT CHARSET=utf8;
create  table  if not exists users
(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `money` int(32),
    primary key (`id`)
)ENGINE=InnoDb  DEFAULT CHARSET=utf8;
CREATE  TABLE  IF NOT EXISTS friend_list
(
    `user_id` INT(11),
    `friend_id` INT(11),
    `last_talk_time` DATETIME,
    primary key (user_id,friend_id)
)ENGINE=InnoDb  DEFAULT CHARSET=utf8;