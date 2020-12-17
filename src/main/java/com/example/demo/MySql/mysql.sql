use MyData;
CREATE TABLE test (
          `id` int(11) NOT NULL AUTO_INCREMENT,
          `rtd` float(11,2) NOT NULL,
          `flag` char(1) NOT NULL,
          `sample_factor` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
          `pool_identify` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
          `sample_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
          PRIMARY KEY (`id`)
        ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;