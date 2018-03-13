package service

import javax.inject.Singleton

import domain.{ParserInput, ParserResult, ParserResultFail}
import service.api.ScriptParserService

/**
  *
  * @author Jakub Tucek
  */
@Singleton
class MySqlParserService extends ScriptParserService {

  override def parse(parserInput: ParserInput): ParserResult = {
    // TODO
    new ParserResultFail(new ParserInput(""), "asd")
  }

}
