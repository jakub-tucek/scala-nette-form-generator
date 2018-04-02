package domain.form

/**
  *
  * @author Jakub Tucek
  */


case class FormTemplateResult(templates: List[FormTemplate])

case class FormTemplate(templateName: String, templateContent: String)
