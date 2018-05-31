package service

import domain.sql.Table
import javax.inject.Singleton
import shared.domain.{FormTemplate, FormTemplateContainer, LatteTemplateType, PhpTemplateType}

/**
  * Service responsible for creation of templates based on parsed data.
  *
  * @author Jakub Tucek
  */
@Singleton
class FormTemplateService {

  def createLatteTemplates(tables: List[Table]): FormTemplateContainer = {
    // generate latte (bootstrap) tempates
    val templates = tables map { t => FormTemplate(formatTableName(t.name), views.html.bootstrapForm.render(t).body) }

    FormTemplateContainer(templates, LatteTemplateType())
  }

  def createPhpTemplates(tables: List[Table]): FormTemplateContainer = {
    // generate php templates
    val templates = tables map { t =>
      val formattedName = formatTableName(t.name)
      FormTemplate(formattedName, views.html.phpForm.render(t, formattedName).body)
    }
    FormTemplateContainer(templates, PhpTemplateType())
  }

  /**
    * Formats table name to CamelCase. Capitalizes each letter after '_' and removes delimiter.
    * First letter is capitalized too.
    *
    * @param n original name
    * @return formatted name
    */
  private def formatTableName(n: String): String = n.split('_').map(_.capitalize).mkString("").capitalize
}
