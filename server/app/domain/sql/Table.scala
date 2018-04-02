package domain.sql

/**
  *
  * @author Jakub Tucek
  */
case class Table(name: String, cols: List[TableColumn]) {

  override def toString = s"Table($name, $cols)"
}

case class TableColumn(name: String, colType: ColumnType, options: List[ColumnOption]) {

  override def toString = s"TableColumn($name, $colType, $options)"
}