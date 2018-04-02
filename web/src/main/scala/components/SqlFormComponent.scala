package components


import autowire._
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{Callback, _}
import services.AjaxClient
import shared.domain.{ProcessFormRequest, ProcessFormSuccessResponse}
import shared.service.WiredApi

/**
  *
  * @author Jakub Tucek
  */
object SqlFormComponent {

  private val component = ScalaComponent
    .builder[Unit]("SqlForm$")
    .initialState(State(""))
    .renderBackend[Backend]
    .build

  def apply() = component()

  case class State(areaValue: String)

  class Backend($: BackendScope[Unit, State]) {


    private def onAreaChange(e: ReactEventFromInput): Callback = {
      $.setState(State(e.target.value))
    }

    private def handleSubmit(e: ReactEventFromInput): Callback = {
      $.state.map(s => {
        println(s)
        AjaxClient[WiredApi].processSql(ProcessFormRequest(s.areaValue)).call().foreach {
          s: ProcessFormSuccessResponse => println(s)
        }
      })
    }

    def render(state: State): VdomTag = {
      <.div(
        <.form(
          ^.onSubmit ==> handleSubmit,
          <.div(
            ^.cls := "form-group",
            <.label(
              ^.htmlFor := "sqlArea",
              "Enter SQL to be processed"
            ),
            <.textarea(
              ^.cls := "form-control",
              ^.id := "sqlArea",
              ^.rows := 20,
              ^.onChange ==> onAreaChange
            )
          ),
          <.div(
            ^.cls := "form-group",
            <.button(
              ^.cls := "btn btn-primary",
              "Send"
            ),
            state.areaValue
          )
        )
      )
    }
  }
}
