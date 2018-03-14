package domain.input

/**
  *
  * @author Jakub Tucek
  */
class BaseInput(name: String, required: Boolean, val maxLength: Int) extends AbstractInput(name, required) {

}
