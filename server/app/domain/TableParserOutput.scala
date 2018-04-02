package domain

import domain.sql.Table
import service.parser.TableParser

/**
  * Output of table parser
  */
sealed trait TableParserOutput

case class TableParserOutputSuccess(tables: List[Table], next: TableParserInput) extends TableParserOutput {
  override def toString = s"ParserOutputSuccess($tables, $next)"
}

case class TableParserOutputFailure(message: String, next: TableParser.Input) extends TableParserOutput {
  override def toString = s"ParserOutputFailure($message, $next)"
}




