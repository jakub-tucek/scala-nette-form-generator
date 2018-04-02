package service

import domain.form.{FormTemplate, FormTemplateResult}
import domain.sql.Table
import javax.inject.Singleton

/**
  *
  * @author Jakub Tucek
  */
@Singleton
class FormTemplateService {


  def createTemplates(tables: List[Table]): FormTemplateResult = {
    val templates = tables.map(t => FormTemplate(t.name, views.html.bootstrapForm.render(t).body))

    new FormTemplateResult(templates)
  }
}
