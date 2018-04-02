package service

import domain.sql.Table
import javax.inject.Singleton
import shared.dto.{FormTemplate, FormTemplateResult}

/**
  *
  * @author Jakub Tucek
  */
@Singleton
class FormTemplateService {

  def createTemplates(tables: List[Table]): FormTemplateResult = {
    val templates = tables.map(t => FormTemplate(t.name, views.html.bootstrapForm.render(t).body))

    FormTemplateResult(templates)
  }
}
