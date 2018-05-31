package shared.domain

import io.circe.generic.{JsonCodec, semiauto}
import io.circe.{Decoder, Encoder}


// latte template list container class
@JsonCodec case class FormTemplateContainer(templates: List[FormTemplate], templateType: TemplateType)

// template type trait
sealed trait TemplateType

@JsonCodec case class LatteTemplateType() extends TemplateType

@JsonCodec case class PhpTemplateType() extends TemplateType

// latte template
@JsonCodec case class FormTemplate(templateName: String, templateContent: String)

// specify template type encoder / decoder implicitly due to annotation limitation on trait
object TemplateType {
  implicit val encode: Encoder[TemplateType] = semiauto.deriveEncoder
  implicit val decode: Decoder[TemplateType] = semiauto.deriveDecoder
}