package shared.service

import java.time.OffsetDateTime

import shared.model.WiredApiModel.ApiResult;

/**
 * @author Jakub Tucek
 */
trait WiredApi {

  def now(): ApiResult[OffsetDateTime]
}
