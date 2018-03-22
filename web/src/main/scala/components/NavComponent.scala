package components

import java.time.LocalDateTime

import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.experimental.intl.DateTimeFormatOptions
import utils.ViewUtils

/**
  *
  * @author Jakub Tucek
  */
object NavComponent {

  private val component = ScalaComponent.builder[Unit]("HelloMessage")
    .render(_ => <.nav(
      ^.cls := "navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0",
      <.a(
        ^.cls := "navbar-brand col-sm-3 col-md-2 mr-0",
        ^.href := "#",
        "Nette form generator"
      ),
      <.ul(
        ^.cls := "navbar-nav px-3",
        <.li(
          ^.cls := "nav-item text-nowrap",
          ViewUtils.formatDate(LocalDateTime.now)
        )
      )
    )
    ).build

  def apply() = component()
}