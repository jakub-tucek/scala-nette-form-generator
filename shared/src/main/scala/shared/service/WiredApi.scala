package shared.service

import scala.concurrent.Future

/**
  * @author Jakub Tucek
  */
trait WiredApi {

  def now(): Future[String]
}
