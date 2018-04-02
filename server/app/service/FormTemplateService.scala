package service

import domain.sql.Table
import javax.inject.Singleton
import shared.domain.{FormLatteTemplate, FormLatteTemplateList}

/**
  *
  * @author Jakub Tucek
  */
@Singleton
class FormTemplateService {

  def createTemplates(tables: List[Table]): FormLatteTemplateList = {
    val templates = tables.map(t => FormLatteTemplate(t.name, views.html.bootstrapForm.render(t).body))

    FormLatteTemplateList(templates)
  }
}
