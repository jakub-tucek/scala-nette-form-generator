package components

import facade.ReactSpinkit
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._
import models.SpinFoldingCube

/**
  *
  * @author Jakub Tucek
  */
object SpinnerComponent {

  private val component = ScalaComponent.builder[Props]("SpinnerComponent")
    .renderP((_, p) => <.div(
      if (p.shouldDisplay) ":yes" else "NO",
      if (p.shouldDisplay) <.div(
        ^.position := "fixed",
        ^.width := "100%",
        ^.height := "100%",
        ^.top := "0",
        ^.left := "0",
        ^.right := "0",
        ^.backgroundColor := "rgba(0,0,0,0.3)",
        <.div(
          ^.position := "absolute",
          ^.top := "40%",
          ^.left := "50%",
          ReactSpinkit()(ReactSpinkit.props(SpinFoldingCube()))()
        )
      ) else EmptyVdom
    )).build

  def apply(props: Props) = component(props)

  case class Props(shouldDisplay: Boolean = false)

}
