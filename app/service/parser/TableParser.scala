package service.parser

import scala.language.postfixOps
import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers

/**
  *
  * @author Jakub Tucek
  */
object TableParser extends RegexParsers {
  override protected val whiteSpace: Regex = """(\s|;.*)+""".r

  private val apostrophe = opt("`")
  private val rowDefEnd = """,?""".r
  private val name = apostrophe ~> """([^`\s]+)""".r <~ apostrophe
  private val size = "(" ~> """[0-9]+""".r <~ ")" ~ opt(",")

  private val enumType = "'" ~> """[A-Z_]+""".r <~ """\',?""".r

  private val colType = (("VARCHAR" | "TINYINT" | "INT") ~ size) | "TIMESTAMP" | ("ENUM" ~ "(" ~> (enumType *) <~ ")")


  def parse(s: String) = {
    parseAll(expression, cleanScript(s))
  }

  private def defaultVal = "DEFAULT" ~> ("NULL" | "'" ~> """[0-9]+""" <~ "'")

  private def expression = table ~ ((constraint | column) *)

  private def required = "NOT " ~ "NULL" ^^ { _ => 'required }

  private def column = name ~ colType <~ """([^,]+)""".r ~ rowDefEnd

  private def constraint = """(PRIMARY|UNIQUE|CONSTRAINT|CHECK|FULLTEXT|FOREIGN|INDEX|KEY|ON|SPATIAL)([^,]+)""".r ~ rowDefEnd ^^ { _ => "" }

  private def table = "CREATE TABLE" ~> name <~ "("

  private def cleanScript(s: String) = {
    s.replaceAll("#.*$", "")
      .replaceAll("\n", "")
      .replaceAll("$\\s+", "")
      .replaceAll("\\s+", " ")
      .split(";")
      .filter {
        _.matches("(?i)^CREATE TABLE.*+")
      }
      .mkString(";")
  }

}
