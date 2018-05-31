package service

import domain.sql.Table
import javax.inject.Singleton
import shared.domain.{FormTemplate, FormTemplateContainer, LatteTemplateType, PhpTemplateType}

/**
  *
  * @author Jakub Tucek
  */
@Singleton
class FormTemplateService {

  def createLatteTemplates(tables: List[Table]): FormTemplateContainer = {
    val templates = tables.map(t => FormTemplate(t.name, views.html.bootstrapForm.render(t).body))

    FormTemplateContainer(templates, LatteTemplateType())
  }

  def createPhpTemplates(tables: List[Table]): FormTemplateContainer = {

    FormTemplateContainer(List(), PhpTemplateType())
  }
}
