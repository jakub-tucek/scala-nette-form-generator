package service

import domain.{TableParserInput, TableParserOutputFailure, TableParserOutputSuccess}
import javax.inject.{Inject, Singleton}
import service.parser.TableParser
import shared.dto.{FormTemplateResult, SqlRequestDto, SqlSuccessResponse}

/**
  *
  * @author Jakub Tucek
  */
@Singleton
class FormGeneratorService @Inject()(val formTemplateService: FormTemplateService) {

  def processSql(sqlRequest: SqlRequestDto): SqlSuccessResponse = {
    val parserInput = TableParserInput(sqlRequest.sqlContent)

    TableParser.apply(parserInput) match {
      case TableParserOutputSuccess(tables, _) => {
        val formTemplateResult = formTemplateService.createTemplates(tables)
        SqlSuccessResponse(formTemplateResult)
      }
      case TableParserOutputFailure(m, in) => {
        //        SqlFailResponse(s"Parsing failed with message: $m. Remaining text to be parsed: $in")
        println(s"Parsing failed with message: $m. Remaining text to be parsed: $in")
        SqlSuccessResponse(FormTemplateResult(List.empty)) // TODO Process fail response
      }
    }
  }

}
