package facade

import japgolly.scalajs.react._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport


/**
  *
  * @author Jakub Tucek
  */
//noinspection TypeAnnotation
object ReactHighlight {

  @JSImport("react-highlight", JSImport.Default)
  @js.native
  object RawComponent extends js.Object

  @js.native
  trait Props extends js.Object {
    var innerHTML: Boolean = js.native
    var language: String = js.native
  }


  def props(language: String): Props = {
    val p = (new js.Object).asInstanceOf[Props]
    p.innerHTML = false
    p.language = language
    p
  }

  def apply() = JsComponent[Props, Children.Varargs, Null](RawComponent)
}
