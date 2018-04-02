package service

import domain.{TableParserInput, TableParserOutputFailure, TableParserOutputSuccess}
import javax.inject.{Inject, Singleton}
import service.parser.TableParser
import shared.domain.{FormLatteTemplateList, ProcessFormRequest, ProcessFormSuccessResponse}

/**
  *
  * @author Jakub Tucek
  */
@Singleton
class FormGeneratorService @Inject()(val formTemplateService: FormTemplateService) {

  def processSql(sqlRequest: ProcessFormRequest): ProcessFormSuccessResponse = {
    val parserInput = TableParserInput(sqlRequest.sqlContent)

    TableParser.apply(parserInput) match {
      case TableParserOutputSuccess(tables, _) => {
        val formTemplateResult = formTemplateService.createTemplates(tables)
        ProcessFormSuccessResponse(formTemplateResult)
      }
      case TableParserOutputFailure(m, in) => {
        //        SqlFailResponse(s"Parsing failed with message: $m. Remaining text to be parsed: $in")
        println(s"Parsing failed with message: $m. Remaining text to be parsed: $in")
        ProcessFormSuccessResponse(FormLatteTemplateList(List.empty)) // TODO Process fail response
      }
    }
  }

}
