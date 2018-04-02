package shared.service

import java.time.LocalDateTime

import shared.domain.{ProcessFormRequest, ProcessFormSuccessResponse}

import scala.concurrent.Future

/**
  * @author Jakub Tucek
  */
trait WiredApi {

  def now(): Future[LocalDateTime]

  def processSql(sqlRequest: ProcessFormRequest): Future[ProcessFormSuccessResponse]
}
