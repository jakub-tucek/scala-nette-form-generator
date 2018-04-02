package shared.domain

import io.circe.generic.JsonCodec

/**
  *
  * @author Jakub Tucek
  */
@JsonCodec case class ProcessFormRequest(sqlContent: String)
