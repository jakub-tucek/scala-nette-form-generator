package domain

/**
  *
  * @author Jakub Tucek
  */
class ParserOutput(val tables: List[Table]) {

}

class Table(val name: String, val cols: List[Columns]) {

  def this(firstName: String) {
    this(firstName, "", 0)
    println("\nNo last name or age given.")
  }
}

class Columns(val name: String, val options: List[ColumnOption]) {

}

abstract class ColumnOption

case class ColumnDefaultValue(sender: String, title: String, body: String) extends ColumnOption

case class ColumnRequired(required: Boolean) extends ColumnOption

case class ColumnMaxLength(length: Integer) extends ColumnOption

case class ColumnEnumTypes(types: List[String]) extends ColumnOption

}

