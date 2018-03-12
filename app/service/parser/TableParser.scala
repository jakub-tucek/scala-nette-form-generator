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
  private val name = """([^`\s]+)""".r

  def parse(s: String) = {
    parseAll(expression, cleanScript(s))
  }

  private def expression = table ~ ((constraint | column) *)

  private def column = columnName ~ """([^,]+)""".r ~ rowDefEnd

  private def columnName = apostrophe ~ name ~ apostrophe

  private def constraint = """(PRIMARY|UNIQUE|CONSTRAINT|CHECK|FULLTEXT|FOREIGN|INDEX|KEY|ON|SPATIAL)([^,]+)""".r ~ rowDefEnd

  private def table = "CREATE TABLE" ~ apostrophe ~ name ~ apostrophe ~ "("

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
