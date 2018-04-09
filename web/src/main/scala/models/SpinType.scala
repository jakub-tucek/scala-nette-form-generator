package models

/**
  *
  * @author Jakub Tucek
  */
sealed trait SpinType {
  val typeName: String
}

case class SpinFoldingCube() extends SpinType {
  override val typeName: String = "folding-cube"
}
