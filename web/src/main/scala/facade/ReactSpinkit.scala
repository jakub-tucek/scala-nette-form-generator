package facade

import japgolly.scalajs.react._
import models.SpinType

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport


/**
  *
  * @author Jakub Tucek
  */
object ReactSpinkit {

  @JSImport("react-spinkit", JSImport.Default)
  @js.native
  object RawComponent extends js.Object

  def props(s: SpinType) = {
    val p = (new js.Object).asInstanceOf[Props]
    p.name = s.typeName
    p.fadeIn = "none"
    p
  }

  @js.native
  trait Props extends js.Object {
    var name: String = js.native
    var fadeIn: String = js.native
  }

  def apply() = JsComponent[Props, Children.Varargs, Null](RawComponent)
}


