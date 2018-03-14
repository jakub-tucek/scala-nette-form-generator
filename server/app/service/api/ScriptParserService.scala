package service.api

import domain.{ParserInput, ParserResult}

/**
  *
  * @author Jakub Tucek
  */
trait ScriptParserService {

  def parse(parserInput: ParserInput): ParserResult

}
