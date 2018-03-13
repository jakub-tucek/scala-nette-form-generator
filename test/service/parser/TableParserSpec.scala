package service.parser

import domain.{ParserInput, ParserOutputSuccess}
import org.scalatest.FlatSpec

/**
  *
  * @author Jakub Tucek
  */
class TableParserSpec extends FlatSpec {

  val input =
    """
      |SET NAMES utf8;
      |SET time_zone = '+00:00';
      |SET foreign_key_checks = 0;
      |SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';
      |SET default_storage_engine = INNODB;
      |
      |
      |DROP TABLE IF EXISTS key_code_history;
      |DROP TABLE IF EXISTS page;
      |DROP TABLE IF EXISTS user;
      |DROP TABLE IF EXISTS user_info;
      |DROP TABLE IF EXISTS notification;
      |DROP TABLE IF EXISTS room;
      |DROP TABLE IF EXISTS reservation;
      |DROP TABLE IF EXISTS key_code;
      |DROP TABLE IF EXISTS fileupload;
      |DROP TABLE IF EXISTS notification;
      |DROP TABLE IF EXISTS user_info;
      |
      |CREATE TABLE `user` (
      |  `id`             INT(11)      NOT NULL                 AUTO_INCREMENT,
      |  `lastName`       VARCHAR(255)                          DEFAULT NULL,
      |  `email`          VARCHAR(255) NOT NULL,
      |  `role`           ENUM ('USER', 'ADMIN', 'SUPER_ADMIN') DEFAULT 'USER',
      |  `activation_key` VARCHAR(255)                          DEFAULT NULL,
      |  `activated`      TINYINT(4)                            DEFAULT '0',
      |  `free_hours`     INT(11)                               DEFAULT '2',
      |  `disabled`       TINYINT(4)                            DEFAULT '0',
      |  `created_at`     TIMESTAMP                           DEFAULT CURRENT_TIMESTAMP,
      |  `updated_at`     TIMESTAMP                           DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      |  `key_code_id`    INT(11),
      |  `deleted`        TINYINT(4)                            DEFAULT '0',
      |  PRIMARY KEY (`id`),
      |  UNIQUE KEY `user_id_uindex` (`id`),
      |  CONSTRAINT user_user_info_id_fk FOREIGN KEY (user_info_id) REFERENCES user_info (id) ON DELETE CASCADE
      |);
      |CREATE TABLE `key_code` (
      |  `id`         INT(11) NOT NULL AUTO_INCREMENT,
      |  `key_code`   VARCHAR(100),
      |  `desc`       VARCHAR(255),
      |  `created_at` TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
      |  `updated_at` TIMESTAMP        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      |  PRIMARY KEY (`id`)
      |);
    """.stripMargin


  "TableParserSpec" should "Parse tables" in {
    val a = TableParser.parse(new ParserInput(input))

    assert(a.isInstanceOf[ParserOutputSuccess])

    a match {
      case s: ParserOutputSuccess => {
        assert(s.tables.nonEmpty)
      }
    }
  }
}

