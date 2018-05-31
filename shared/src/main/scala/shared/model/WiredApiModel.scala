package shared.model

import scala.concurrent.Future

/**
  * Model for remote API
  *
  * @author Jakub Tucek
  */
object WiredApiModel {

  sealed trait ApiError

  case object NotFound extends ApiError

  case object UnauthorizedApi extends ApiError

  case object NoContent

  type ApiResult[T] = Future[Either[ApiError, T]]
}
