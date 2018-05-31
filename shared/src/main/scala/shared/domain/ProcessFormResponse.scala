package shared.domain

import io.circe.generic.{JsonCodec, semiauto}
import io.circe.{Decoder, Encoder}

/**
  * Process form response trait
  *
  * @author Jakub Tucek
  */
sealed trait ProcessFormResponse

// fail response with message
@JsonCodec case class ProcessFormFailResponse(message: String) extends ProcessFormResponse

// success response with result props
@JsonCodec case class ProcessFormSuccessResponse(latteTemplates: FormTemplateContainer, codeTemplates: FormTemplateContainer) extends ProcessFormResponse


object ProcessFormResponse {
  implicit val encode: Encoder[ProcessFormResponse] = semiauto.deriveEncoder
  implicit val decode: Decoder[ProcessFormResponse] = semiauto.deriveDecoder
}
