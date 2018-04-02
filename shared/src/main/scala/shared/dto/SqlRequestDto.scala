package shared.dto

import io.circe.generic.JsonCodec

/**
  *
  * @author Jakub Tucek
  */
@JsonCodec case class SqlRequestDto(sqlContent: String)
