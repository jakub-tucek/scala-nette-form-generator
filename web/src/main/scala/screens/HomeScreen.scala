package screens

import java.time.LocalDateTime

import autowire._
import components.{SqlFormComponent, SqlResultComponent}
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._
import models.Locs.Loc
import services.AjaxClient
import shared.domain.ProcessFormSuccessResponse
import shared.service.WiredApi
import utils.{ScalaJsCodecs, ViewUtils}

object HomeScreen extends ScalaJsCodecs {

  case class Props(c: RouterCtl[Loc])

  private val component = ScalaComponent
    .builder[Props]("HomeScreen")
    .initialState(State("", null))
    .renderBackend[Backend]
    .componentDidMount(_.backend.mounted())
    .build

  case class State(time: String, processFormResponse: ProcessFormSuccessResponse)

  def apply(c: RouterCtl[Loc]): Unmounted[Props, State, Backend] = component(Props(c))

  class Backend($: BackendScope[Props, State]) {

    private def setProcessForm(processFormResponse: ProcessFormSuccessResponse): Unit = {
      $.modState(s => State(s.time, processFormResponse)).runNow()
    }

    def mounted() = Callback {
      AjaxClient[WiredApi].now().call().foreach {
        res: LocalDateTime => $.modState(s => State(ViewUtils.formatDate(res), s.processFormResponse)).runNow()
      }
    }

    def render(props: Props, state: State): VdomTag = {
      <.div(
        ^.cls := "row",
        <.div(
          ^.cls := "col-md-6",
          SqlFormComponent(setProcessForm)
        ),
        <.div(
          ^.cls := "col-md-6",
          SqlResultComponent(state.processFormResponse)
        ),
        <.div(
          "Time on backend is: " + state.time
        )
      )
    }

  }

}
