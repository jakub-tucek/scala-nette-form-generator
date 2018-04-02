package components

import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{Callback, _}

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

    def onAreaChange(e: ReactEventFromInput): Callback = {
      $.setState(State(e.target.value))
    }

    def handleSubmit(e: ReactEventFromInput): Callback = {
      e.preventDefault()
      $.modState(state => {
        println(state)
        state
      })
    }
  }

}
