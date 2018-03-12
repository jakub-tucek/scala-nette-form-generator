package service

import javax.inject.Singleton

import domain.ParserOutput
import domain.form.DefinitionForm
import service.api.ScriptParser

/**
  *
  * @author Jakub Tucek
  */
@Singleton
class MySqlParser extends ScriptParser {

  override def parse(definition: DefinitionForm): ParserOutput = {
    // TODO
    new ParserOutput()
  }

}
