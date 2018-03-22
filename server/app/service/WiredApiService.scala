package service

import java.time.LocalDateTime

import shared.model.WiredApiModel.ApiResult
import shared.service.WiredApi
import shared.utils.Implicits

/**
  *
  * @author Jakub Tucek
  */
class WiredApiService extends WiredApi with Implicits {
  override def now(): ApiResult[LocalDateTime] = LocalDateTime.now().asResult
}

