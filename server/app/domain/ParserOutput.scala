package domain

import domain.sql.Table
import service.parser.TableParser


sealed trait ParserOutput

case class ParserOutputSuccess(tables: List[Table], next: ParserInput) extends ParserOutput {
  override def toString = s"ParserOutputSuccess($tables, $next)"
}

case class ParserOutputFailure(message: String, next: TableParser.Input) extends ParserOutput {
  override def toString = s"ParserOutputFailure($message, $next)"
}




