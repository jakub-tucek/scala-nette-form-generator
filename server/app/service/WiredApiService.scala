package service

import java.time.OffsetDateTime

import shared.model.WiredApiModel.ApiResult
import shared.service.WiredApi
import shared.utils.Implicits

/**
  *
  * @author Jakub Tucek
  */
class WiredApiService extends WiredApi with Implicits {
  override def now(): ApiResult[OffsetDateTime] = OffsetDateTime.now().asResult
}

