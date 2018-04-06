package screens

import java.time.LocalDateTime

import autowire._
import components.SqlFormComponent
import facade.ReactHighlight
import japgolly.scalajs.react._
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

  def apply(c: RouterCtl[Loc]) = component(Props(c))

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
      println(state)

      var content = ""
      if (state.processFormResponse != null) {
        content = state.processFormResponse.formTemplateResult.templates(0).templateContent
      }
      <.div(
        <.div(
          "Time on backend is: " + state.time
        ),
        <.hr(),
        <.div(
          SqlFormComponent(setProcessForm)
        ),
        ReactHighlight()(ReactHighlight.props())(
          content
        )
      )
    }

  }

}
