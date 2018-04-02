package service

import java.time.LocalDateTime

import javax.inject.{Inject, Singleton}
import shared.domain.{ProcessFormRequest, ProcessFormSuccessResponse}
import shared.service.WiredApi
import shared.utils.Implicits

import scala.concurrent.Future

/**
  *
  * @author Jakub Tucek
  */
@Singleton
class WiredApiService @Inject()(val formGeneratorService: FormGeneratorService)
  extends WiredApi with Implicits {

  override def now(): Future[LocalDateTime] = LocalDateTime.now().asFuture

  override def processSql(sqlRequest: ProcessFormRequest): Future[ProcessFormSuccessResponse] = formGeneratorService.processSql(sqlRequest).asFuture
}

