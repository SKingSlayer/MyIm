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

CREATE  TABLE  IF NOT EXISTS Rm
(
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `rmNum` INT(32),
    PRIMARY KEY (`id`)
)ENGINE=InnoDb  DEFAULT CHARSET=utf8;
CREATE  TABLE  IF NOT EXISTS  users
(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `money` int(32),
    PRIMARY KEY (`id`)
)ENGINE=InnoDb  DEFAULT CHARSET=utf8;
CREATE  TABLE  IF NOT EXISTS friend_list
(
    `user_id` INT(11),
    `friend_id` INT(11),
    `last_talk_time` DATETIME,
    PRIMARY KEY (user_id,friend_id)
)ENGINE=InnoDb  DEFAULT CHARSET=utf8;
CREATE  TABLE  IF NOT EXISTS talk_message
(
    `id` INT(11) AUTO_INCREMENT,
    `user_id` INT(11),
    `friend_id` INT(11),
    `talk_time` DATETIME,
    `message` VARCHAR(50),
    PRIMARY KEY (id)
)ENGINE=InnoDb  DEFAULT CHARSET=utf8;