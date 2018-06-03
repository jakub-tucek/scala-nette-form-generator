package service

import domain.sql.Table
import javax.inject.Singleton
import shared.domain.{FormTemplate, FormTemplateContainer, LatteTemplateType, PhpTemplateType}
import domain.sql._

/**
  * Service responsible for creation of templates based on parsed data.
  *
  * @author Jakub Tucek
  */
@Singleton
class FormTemplateService {

  def createLatteTemplates(tables: List[Table]): FormTemplateContainer = {
    // generate latte (bootstrap) tempates
    val templates = tables map { t => FormTemplate(getFormattedName(t.name), views.html.bootstrapForm.render(t).body) }

    FormTemplateContainer(templates, LatteTemplateType())
  }

  def createPhpTemplates(tables: List[Table]): FormTemplateContainer = {
    // generate php templates
    val templates = tables map { t =>
      val formattedName = getFormattedName(t.name)
      val formDefinition = getFormDefinition(t)
      FormTemplate(formattedName, views.html.phpForm.render(t, formattedName, formDefinition).body)
    }
    FormTemplateContainer(templates, PhpTemplateType())
  }

  /**
    * Formats string to CamelCase. Capitalizes each letter after '_' and removes delimiter.
    * First letter is capitalized too.
    *
    * @param n original name
    * @return formatted name
    */
  private def getFormattedName(n: String): String = n.split('_').map(_.capitalize).mkString("").capitalize

  private def getFormDefinition(table: Table): String = {
    val lineStart = "        " // base line starting whitespaces
    val optionStart = s"\n$lineStart    " // directive whitespaces
    val sb = new StringBuilder

    // iterate over columns are generes field definition
    table.cols foreach { col =>
      // add starting whitespaces
      sb.append(lineStart)

      val formattedName = getFormattedName(col.name)
      // check col type and specify field type
      col.colType match {
        case ColumnInt() | ColumnVarchar() | ColumnTimestamp() =>
          // add base text field
          sb.append(s"""$$form->addText("${col.name}", "$formattedName")""")
        case ColumnTinyInt() =>
          // add checkbox text field
          sb.append(s"""$$form->addCheckbox("${col.name}", "$formattedName")""")
        case ColumnEnum() =>
          // add select for enum column
          sb.append(s"""$$form -> addSelect("${col.name}", "$formattedName", """)

          // tries to find enum types in col.options, uses empty list if not found
          // found types are set to select definition
          col.options.find(_.isInstanceOf[ColumnEnumTypes]).getOrElse(ColumnEnumTypes(List())) match {
            case a: ColumnEnumTypes => sb.append(a.types.map(t => s""""$t"""").mkString(s"[", ", ", "]"))
            case _ => sb.append("[]")
          }
          sb.append(")")
        case ColumnText() =>
          // text area
          sb.append(s"""$$form->addTextArea("${col.name}", "$formattedName")""")
        case _ =>
      }

      // check if type is number
      // if yes, add rule that value must be a number
      col.colType match {
        case ColumnInt() => sb.append(s"""$optionStart->addRule(Form::Numeric, "${col.name} must be a number!"""")
        case _ =>
      }

      // iterates through col options
      // appends them to column
      col.options.foreach {
        case ColumnRequired(value) =>
          sb.append(s"""$optionStart->setRequired($value""")
          if (value) sb.append(""", "Field is required"""")
          sb.append(")")
        case ColumnMaxLength(len) => sb.append(s"""$optionStart->addRule(Form::MAX_LENGTH, "Value is way too long", $len)""")
        case _ =>
      }

      // appends ending comma and newlines
      sb.append(";\n\n")

    }
    sb.toString
  }
}
