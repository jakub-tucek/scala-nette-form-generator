package service

import java.time.LocalDateTime

import javax.inject.Singleton
import shared.service.WiredApi
import shared.utils.Implicits

import scala.concurrent.Future

/**
  *
  * @author Jakub Tucek
  */
@Singleton
class WiredApiService extends WiredApi with Implicits {
  override def now(): Future[String] = LocalDateTime.now().toString.asFuture
}

