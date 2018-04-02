package shared.domain

import io.circe.generic.JsonCodec

/**
  *
  * @author Jakub Tucek
  */
sealed trait ProcessFormResponse

@JsonCodec case class ProcessFormFailResponse(message: String) extends ProcessFormResponse

@JsonCodec case class ProcessFormSuccessResponse(formTemplateResult: FormLatteTemplateList) extends ProcessFormResponse
