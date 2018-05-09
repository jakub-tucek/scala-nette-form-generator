package components

import java.time.LocalDateTime

import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent}
import utils.ViewUtils

import scala.scalajs.js

/**
  *
  * @author Jakub Tucek
  */
object NavComponent {

  private val component = ScalaComponent
    .builder[Unit]("NavComponent")
    .initialState(getCurrentDate)
    .renderBackend[Backend]
    .componentDidMount(_.backend.mounted())
    .componentWillUnmount(_.backend.unmounted())
    .build

  def apply() = component()

  private def getCurrentDate: String = ViewUtils.formatDate(LocalDateTime.now)

  class Backend($: BackendScope[Unit, String]) {
    var interval: js.UndefOr[js.timers.SetIntervalHandle] = js.undefined

    def mounted() = Callback {
      interval = js.timers.setInterval(1000)(refreshTime())
    }

    def refreshTime() = $.setState(getCurrentDate).runNow()

    def unmounted() = Callback {
      interval foreach js.timers.clearInterval
      interval = js.undefined
    }

    def render(state: String): VdomTag = {
      <.nav(
        ^.cls := "navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0",
        <.a(
          ^.cls := "navbar-brand col-sm-3 col-md-2 mr-0",
          ^.href := "#",
          "Nette form generator"
        ),
        <.ul(
          ^.cls := "navbar-nav px-3",
          <.li(
            ^.color := "white",
            ^.cls := "nav-item text-nowrap",
            state
          )
        )
      )
    }
  }
}