package shared.domain

import io.circe.generic.JsonCodec

/**
  *
  * @author Jakub Tucek
  */
@JsonCodec case class FormLatteTemplateList(templates: List[FormLatteTemplate])

@JsonCodec case class FormLatteTemplate(templateName: String, templateContent: String)
