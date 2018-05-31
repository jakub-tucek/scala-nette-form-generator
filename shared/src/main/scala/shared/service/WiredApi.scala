package shared.service

import java.time.LocalDateTime

import shared.domain.{ProcessFormRequest, ProcessFormResponse}

import scala.concurrent.Future

/**
  * Common service trait for exposing API available on backend.
  *
  * @author Jakub Tucek
  */
trait WiredApi {

  /**
    * @return current server time
    */
  def now(): Future[LocalDateTime]

  /**
    * @param sqlRequest form representation
    * @return form response
    */
  def processSql(sqlRequest: ProcessFormRequest): Future[ProcessFormResponse]
}
