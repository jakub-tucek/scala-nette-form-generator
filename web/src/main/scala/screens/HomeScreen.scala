package screens

import java.time.LocalDateTime

import autowire._
import components.SqlFormComponent
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._
import models.Locs.Loc
import services.AjaxClient
import shared.service.WiredApi
import utils.{ScalaJsCodecs, ViewUtils}

object HomeScreen extends ScalaJsCodecs {

  case class Props(c: RouterCtl[Loc])

  private val component = ScalaComponent
    .builder[Props]("HomeScreen")
    .initialState(State(""))
    .renderBackend[Backend]
    .componentDidMount(_.backend.mounted())
    .build

  case class State(time: String)

  def apply(c: RouterCtl[Loc]) = component(Props(c))

  class Backend($: BackendScope[Props, State]) {

    def mounted() = Callback {
      AjaxClient[WiredApi].now().call().foreach {
        s: LocalDateTime => $.setState(State(ViewUtils.formatDate(s))).runNow()
      }
    }

    def render(props: Props, state: State): VdomTag = {
      <.div(
        <.h2("Lorem Ipsum"),
        <.div(
          "Time on backend is: " + state.time
        ),
        <.div(
          SqlFormComponent()
        )
      )
    }
  }

}
