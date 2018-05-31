package shared.utils

import shared.model.WiredApiModel.ApiResult

import scala.concurrent.Future

// Implicits trait
trait Implicits {

  // extension generics for easier working with future
  implicit class ExtGeneric[T](t: T) {

    def asFuture: Future[T] = Future.successful(t)

    def asOption: Option[T] = Option(t)

    def asResult: ApiResult[T] = Future.successful(Right(t))

    def |>[R](f: T => R): R = f(t)

    def condOption(condition: Boolean): Option[T] = if (condition) Some(t) else None
  }

}

// companion object
object Implicits extends Implicits
