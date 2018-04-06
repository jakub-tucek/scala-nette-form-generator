package components


import autowire._
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{Callback, _}
import services.AjaxClient
import shared.domain.{ProcessFormRequest, ProcessFormSuccessResponse}
import shared.service.WiredApi

/**
  *
  * @author Jakub Tucek
  */
object SqlFormComponent {

  private val component = ScalaComponent
    .builder[Props]("SqlForm$")
    .initialState(State(defVal)) //TODO: Remove def val
    .renderBackend[Backend]
    .build

  def apply(c: (ProcessFormSuccessResponse) => Unit) = component(Props(c))

  case class State(areaValue: String)

  case class Props(setProcessFormCallback: (ProcessFormSuccessResponse) => Unit)

  class Backend($: BackendScope[Props, State]) {

    private def onAreaChange(e: ReactEventFromInput): Callback = {
      $.setState(State(e.target.value))
    }

    private def handleSubmit(e: ReactEventFromInput): Callback = {
      e.preventDefault()

      $.state.map(s => {
        AjaxClient[WiredApi].processSql(ProcessFormRequest(s.areaValue)).call().foreach {
          s: ProcessFormSuccessResponse => $.props.map(p => p.setProcessFormCallback(s)).runNow()
        }
      })
    }

    def render(state: State): VdomTag = {
      <.div(
        <.form(
          ^.onSubmit ==> handleSubmit,
          <.div(
            ^.cls := "form-group",
            <.label(
              ^.htmlFor := "sqlArea",
              "Enter SQL to be processed"
            ),
            <.textarea(
              ^.cls := "form-control",
              ^.id := "sqlArea",
              ^.rows := 10,
              ^.value := defVal, // TODO: Remove def val
              ^.onChange ==> onAreaChange
            )
          ),
          <.div(
            ^.cls := "form-group",
            <.button(
              ^.cls := "btn btn-primary",
              "Send"
            )
          )
        )
      )
    }
  }

  val defVal =
    """
      |ET NAMES utf8;
      |SET time_zone = '+00:00';
      |SET foreign_key_checks = 0;
      |SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';
      |SET default_storage_engine = INNODB;
      |
      |
      |DROP TABLE IF EXISTS key_code_history;
      |DROP TABLE IF EXISTS page;
      |DROP TABLE IF EXISTS user;
      |DROP TABLE IF EXISTS user_details;
      |DROP TABLE IF EXISTS user_info;
      |DROP TABLE IF EXISTS notification;
      |DROP TABLE IF EXISTS room;
      |DROP TABLE IF EXISTS reservation;
      |DROP TABLE IF EXISTS key_code;
      |DROP TABLE IF EXISTS fileupload;
      |DROP TABLE IF EXISTS notification;
      |DROP TABLE IF EXISTS user_details;
      |
      |CREATE TABLE `user` (
      |  `id`              INT(11)             NOT NULL                 AUTO_INCREMENT,
      |  `firstName`       VARCHAR(255)                                 DEFAULT NULL,
      |  `lastName`        VARCHAR(255)                                 DEFAULT NULL,
      |  `email`           VARCHAR(191) UNIQUE NOT NULL,
      |  `role`            ENUM ('USER', 'ADMIN', 'SUPER_ADMIN')        DEFAULT 'USER',
      |  `password`        VARCHAR(255)        NOT NULL,
      |  `activation_key`  VARCHAR(255)                                 DEFAULT NULL,
      |  `activated`       TINYINT(4)                                   DEFAULT '0',
      |  `member`          TINYINT(4)                                   DEFAULT '0',
      |  `free_hours`      INT(11)                                      DEFAULT '2',
      |  `disabled`        TINYINT(4)                                   DEFAULT '0',
      |  `jablotron_id`    INT(11),
      |  `created_at`      TIMESTAMP                                    DEFAULT CURRENT_TIMESTAMP,
      |  `updated_at`      TIMESTAMP                                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      |  `key_code_id`     INT(11),
      |  `user_details_id` INT(11),
      |  `deleted`         TINYINT(4)                                   DEFAULT '0',
      |  PRIMARY KEY (`id`),
      |  UNIQUE KEY `user_id_uindex` (`id`),
      |  UNIQUE KEY `user_key_code_id_uindex` (`key_code_id`),
      |  CONSTRAINT user_key_code_id_fk FOREIGN KEY (key_code_id) REFERENCES key_code (id)
      |    ON DELETE CASCADE,
      |  CONSTRAINT user_user_details_id_fk FOREIGN KEY (user_details_id) REFERENCES user_details (id)
      |    ON DELETE CASCADE
      |);
      |
      |CREATE TABLE `user_details` (
      |  `id`                  INT(11) PRIMARY KEY UNIQUE                     NOT NULL AUTO_INCREMENT,
      |  # personal info
      |  `id_number`           VARCHAR(255)                                   NOT NULL,
      |  `id_number_type`      ENUM ('DRIVER_LICENSE', 'ID_CARD', 'PASSPORT') NOT NULL,
      |  # address
      |  `bank_account_number` VARCHAR(255)                                   NOT NULL,
      |  `street`              VARCHAR(255)                                   NOT NULL,
      |  `street_number`       VARCHAR(255)                                   NOT NULL,
      |  `city`                VARCHAR(255)                                   NOT NULL,
      |  `area_code`           VARCHAR(50)                                    NOT NULL,
      |  `country`             VARCHAR(255)                                   NOT NULL,
      |  `active`              TINYINT(4) DEFAULT '1',
      |  `rules_agreement`     TINYINT(4) DEFAULT '0',
      |  `created_at`          TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,
      |  `updated_at`          TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
      |);
      |
    """.stripMargin
}
