package shared.service

import java.time.LocalDateTime

import scala.concurrent.Future

/**
  * @author Jakub Tucek
  */
trait WiredApi {

  def now(): Future[LocalDateTime]
}
