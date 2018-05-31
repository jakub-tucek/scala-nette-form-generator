package shared.domain

import io.circe.generic.JsonCodec

// process form request
// contains user input
@JsonCodec case class ProcessFormRequest(sqlContent: String)
