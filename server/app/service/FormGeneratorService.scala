package service

import domain.{TableParserInput, TableParserOutputFailure, TableParserOutputSuccess}
import javax.inject.{Inject, Singleton}
import service.parser.TableParser
import shared.domain._

/**
  *
  * @author Jakub Tucek
  */
@Singleton
class FormGeneratorService @Inject()(val formTemplateService: FormTemplateService) {

  def processSql(sqlRequest: ProcessFormRequest): ProcessFormResponse = {
    val parserInput = TableParserInput(sqlRequest.sqlContent)

    TableParser.apply(parserInput) match {
      case TableParserOutputSuccess(tables, _) => {
        val latteTemplateResult = formTemplateService.createLatteTemplates(tables)
        val phpTemplateResult = formTemplateService.createPhpTemplates(tables)
        ProcessFormSuccessResponse(latteTemplateResult, phpTemplateResult)
      }
      case TableParserOutputFailure(m, in) => {
        //        SqlFailResponse(s"Parsing failed with message: $m. Remaining text to be parsed: $in")
        println(s"Parsing failed with message: $m. Remaining text to be parsed: $in")
        ProcessFormFailResponse(s"Parsing failed: $m")
      }
    }
  }

}
