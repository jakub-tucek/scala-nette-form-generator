package domain

import service.parser.TableParser


sealed trait ParserOutput

case class ParserOutputSuccess(tables: List[Table], next: ParserInput) extends ParserOutput {
  override def toString = s"ParserOutputSuccess($tables, $next)"
}

case class ParserOutputFailure(message: String, next: TableParser.Input) extends ParserOutput {
  override def toString = s"ParserOutputFailure($message, $next)"
}

case class Table(name: String, cols: List[TableColumn]) {

  override def toString = s"Table($name, $cols)"
}

case class TableColumn(name: String, colType: String, options: List[ColumnOption]) {

  override def toString = s"TableColumn($name, $colType, $options)"
}

sealed trait ColumnOption

case class ColumnDefaultValueString(value: String) extends ColumnOption

case class ColumnRequired() extends ColumnOption

case class ColumnMaxLength(length: Integer) extends ColumnOption

case class ColumnEnumTypes(types: List[String]) extends ColumnOption

case class ColumnUnrecognized(str: String) extends ColumnOption

