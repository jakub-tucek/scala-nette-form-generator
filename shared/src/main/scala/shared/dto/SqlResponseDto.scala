package shared.dto

import io.circe.generic.JsonCodec

/**
  *
  * @author Jakub Tucek
  */
sealed trait SqlResponseDto

@JsonCodec case class SqlFailResponse(message: String) extends SqlResponseDto

@JsonCodec case class SqlSuccessResponse(formTemplateResult: FormTemplateResult) extends SqlResponseDto
