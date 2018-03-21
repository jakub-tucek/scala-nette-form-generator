package shared.utils

import shared.model.WiredApiModel.ApiResult
import scala.concurrent.Future
import shared.model.WiredApiModel.ApiResult

trait Implicits {

  implicit class ExtGeneric[T](t: T) {

    def asFuture: Future[T] = Future.successful(t)

    def asOption: Option[T] = Option(t)

    def asResult: ApiResult[T] = Future.successful(Right(t))

    def |>[R](f: T => R): R = f(t)

    def condOption(condition: Boolean): Option[T] = if (condition) Some(t) else None
  }

}

object Implicits extends Implicits
