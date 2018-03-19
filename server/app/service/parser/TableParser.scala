package service.parser


import domain._

import scala.language.postfixOps
import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers

/**
  *
  * @author Jakub Tucek
  */
object TableParser extends RegexParsers {
  override protected val whiteSpace: Regex = """(\s|;)+""".r

  private val apostrophe = opt("`")
  private val rowDefEnd = """,?""".r
  private val textUntilComma = """.+?(?=(\)(;|$)|,))""".r
  private val name = apostrophe ~> """([^`\s]+)""".r <~ apostrophe
  private val intValue = "(" ~> """[0-9]+""".r <~ ")" ~ opt(",")
  private val enumType = "'" ~> """[A-Z_]+""".r <~ """\',?""".r

  private val colType = "VARCHAR" | "TINYINT" | "INT" | "TIMESTAMP" | "ENUM"

  private def baseTypeValue = intValue ^^ (v => ColumnMaxLength(v.toInt))

  private def enumTypes = "(" ~> (enumType *) <~ ")" ^^ (t => ColumnEnumTypes(t))

  def apply(input: ParserInput): ParserOutput = {
    val cleanedScript = cleanScript(input.in)
    val res = parseAll(expression, cleanedScript)
    println(res)
    res match {
      case Success(result, _) => ParserOutputSuccess(result, input)
      case NoSuccess(msg, in) => ParserOutputFailure(msg, in)
    }
  }

  private def expression: Parser[List[Table]] = rep(table)

  private def defaultValue: Parser[ColumnDefaultValueString] = "DEFAULT" ~> ("NULL" | defaultValueString) ^^ { v => ColumnDefaultValueString(v) }

  private def defaultValueString: Parser[String] = "'" ~> """[^']+""".r <~ "'"

  private def required: Parser[ColumnRequired] = "NOT" ~ "NULL" ^^ { _ => ColumnRequired() }

  private def column = name ~ colType ~ columnOptions <~ rowDefEnd

  private def columnOptions: Parser[List[ColumnOption]] = (baseTypeValue | enumTypes | required | defaultValue | unknownColOption) *

  private def columnTyped = column ^^ { case n ~ t ~ o => TableColumn(n, t, o) }

  private def constraint = """(PRIMARY|UNIQUE|CONSTRAINT|CHECK|FULLTEXT|FOREIGN|INDEX|KEY|ON|SPATIAL)""".r ~ textUntilComma ~ rowDefEnd ^^ (_ => Nil)

  private def table: Parser[Table] = {
    "CREATE TABLE" ~> name ~ "(" ~ rep(columnTyped) <~ rep(constraint) <~ opt(")") ^^ { case n ~ _ ~ cols => Table(n, cols) }
  }

  private def cleanScript(s: String) = {
    s.replaceAll("#.*$", "")
      .replaceAll("\n|\r", "")
      .replaceAll("$\\s+", "")
      .replaceAll("\\s+", " ")
      .split(";")
      .filter {
        _.matches("(?i)^CREATE TABLE.*+")
      }
      .mkString(";")
  }

  private def unknownColOption: Parser[ColumnUnrecognized] = """[A-Z_]+""".r ^^ (u => ColumnUnrecognized(u))
}
