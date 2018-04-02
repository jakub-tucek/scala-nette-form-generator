package shared.service

import java.time.LocalDateTime

import shared.dto.{SqlRequestDto, SqlSuccessResponse}

import scala.concurrent.Future

/**
  * @author Jakub Tucek
  */
trait WiredApi {

  def now(): Future[LocalDateTime]

  def processSql(sqlRequest: SqlRequestDto): Future[SqlSuccessResponse]
}
