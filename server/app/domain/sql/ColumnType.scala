package domain.sql

/**
  *
  * @author Jakub Tucek
  */
sealed trait ColumnType


case class ColumnVarchar() extends ColumnType

case class ColumnTinyInt() extends ColumnType

case class ColumnInt() extends ColumnType

case class ColumnTimestamp() extends ColumnType

case class ColumnEnum() extends ColumnType

case class ColumnText() extends ColumnType
