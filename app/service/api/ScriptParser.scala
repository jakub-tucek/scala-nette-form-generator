package service.api

import domain.ParserOutput
import domain.form.DefinitionForm

/**
  *
  * @author Jakub Tucek
  */
trait ScriptParser {

  def parse(definition: DefinitionForm): ParserOutput

}
