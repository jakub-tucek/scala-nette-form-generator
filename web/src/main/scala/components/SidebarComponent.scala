package components

import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._

/**
  *
  * @author Jakub Tucek
  */
object SidebarComponent {

  private val component = ScalaComponent.builder[String]("SidebarComponent")
    .render(_ => <.nav(
      ^.cls := "col-md-2 d-none d-md-block bg-light sidebar",
      <.div(
        ^.cls := "sidebar-sticky",
        <.ul(
          ^.cls := "nav flex-column",
          <.li(
            ^.cls := "nav-item",
            "Homepage"
          )
        )
      )
    )).build

  def apply() = component("")
}
