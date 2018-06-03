package domain.sql

/**
  *
  * @author Jakub Tucek
  */
sealed trait ColumnOption

case class ColumnDefaultValueString(value: String) extends ColumnOption

case class ColumnRequired(value: Boolean = true) extends ColumnOption

case class ColumnMaxLength(length: Integer) extends ColumnOption

case class ColumnEnumTypes(types: List[String]) extends ColumnOption

case class ColumnUnrecognized(str: String) extends ColumnOption


object ColumnEnumTypes {
}