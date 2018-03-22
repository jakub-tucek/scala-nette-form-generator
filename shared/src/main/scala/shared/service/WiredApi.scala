package shared.service

import java.time.LocalDateTime

import shared.model.WiredApiModel.ApiResult

/**
  * @author Jakub Tucek
  */
trait WiredApi {

  def now(): ApiResult[LocalDateTime]
}
