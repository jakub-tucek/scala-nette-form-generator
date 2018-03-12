package service.parser

import org.scalatest.FlatSpec

/**
  *
  * @author Jakub Tucek
  */
class TableParserSpec extends FlatSpec {

  "TableParserSpec" should "Parser tables" in {
    val input = "SET NAMES utf8;\nSET time_zone = '+00:00';\nSET foreign_key_checks = 0;\nSET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';\nSET default_storage_engine = INNODB;\n\n\nDROP TABLE IF EXISTS key_code_history;\nDROP TABLE IF EXISTS page;\nDROP TABLE IF EXISTS user;\nDROP TABLE IF EXISTS user_info;\nDROP TABLE IF EXISTS notification;\nDROP TABLE IF EXISTS room;\nDROP TABLE IF EXISTS reservation;\nDROP TABLE IF EXISTS key_code;\nDROP TABLE IF EXISTS fileupload;\nDROP TABLE IF EXISTS notification;\nDROP TABLE IF EXISTS user_info;\n\nCREATE TABLE `user` (\n  `id`             INT(11)      NOT NULL                 AUTO_INCREMENT,\n  `firstName`      VARCHAR(255)                          DEFAULT NULL,\n  `lastName`       VARCHAR(255)                          DEFAULT NULL,\n  `email`          VARCHAR(255) NOT NULL,\n  `role`           ENUM ('USER', 'ADMIN', 'SUPER_ADMIN') DEFAULT 'USER',\n  `password`       VARCHAR(255) NOT NULL,\n  `activation_key` VARCHAR(255)                          DEFAULT NULL,\n  `activated`      TINYINT(4)                            DEFAULT '0',\n  `member`         TINYINT(4)                            DEFAULT '0',\n  `free_hours`     INT(11)                               DEFAULT '2',\n  `disabled`       TINYINT(4)                            DEFAULT '0',\n  `created_at`     TIMESTAMP                           DEFAULT CURRENT_TIMESTAMP,\n  `updated_at`     TIMESTAMP                           DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n  `key_code_id`    INT(11),\n  `user_info_id`   INT(11),\n  `deleted`        TINYINT(4)                            DEFAULT '0',\n  PRIMARY KEY (`id`),\n  UNIQUE KEY `user_id_uindex` (`id`),\n  UNIQUE KEY `user_key_code_id_uindex` (`key_code_id`),\n  UNIQUE KEY `user_email_uindex` (`email`),\n  CONSTRAINT user_key_code_id_fk FOREIGN KEY (key_code_id) REFERENCES key_code (id) ON DELETE CASCADE,\n  CONSTRAINT user_user_info_id_fk FOREIGN KEY (user_info_id) REFERENCES user_info (id) ON DELETE CASCADE\n);"

    println(TableParser.parse(input))
  }
}

