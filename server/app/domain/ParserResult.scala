package domain

/**
  *
  * @author Jakub Tucek
  */
abstract class ParserResult(val input: ParserInput) {

}

class ParserResultSuccess(input: ParserInput, val result: ParserOutput) extends ParserResult(input) {

}

class ParserResultFail(input: ParserInput, val message: String) extends ParserResult(input) {

}
