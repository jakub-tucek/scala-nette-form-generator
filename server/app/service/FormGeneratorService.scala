package service

import domain.sql.{ColumnRequired, Table, TableColumn}
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
      case TableParserOutputSuccess(tables, _) =>
        val processedTables = tidyParserOutput(tables)
        val latteTemplateResult = formTemplateService.createLatteTemplates(processedTables)
        val phpTemplateResult = formTemplateService.createPhpTemplates(processedTables)
        ProcessFormSuccessResponse(latteTemplateResult, phpTemplateResult)
      case TableParserOutputFailure(m, in) =>
        //        SqlFailResponse(s"Parsing failed with message: $m. Remaining text to be parsed: $in")
        println(s"Parsing failed with message: $m. Remaining text to be parsed: $in")
        ProcessFormFailResponse(s"Parsing failed: $m")
    }
  }

  /**
    * Cleans and tides parser output.
    * - Adds Column required with proper value if it's missing
    *
    * @param tables tables to process
    * @return new tables list copy
    */
  private def tidyParserOutput(tables: List[Table]): List[Table] = {

    // finds col required option and if its not present, append its
    def processCol = (t: TableColumn) => {
      // find required option
      val required = t.options.find(_.isInstanceOf[ColumnRequired])

      // returns options that contain ColumnRequired for sure
      val options = if (required.isDefined) t.options else ColumnRequired(false) :: t.options
      t.copy(options = options)
    }

    tables.map { t => t.copy(cols = t.cols.map(processCol)) }
  }

}
