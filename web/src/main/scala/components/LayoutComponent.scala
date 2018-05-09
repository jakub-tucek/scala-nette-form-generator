package components

import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.extra.router.{Resolution, RouterCtl}
import japgolly.scalajs.react.vdom.HtmlTags
import japgolly.scalajs.react.vdom.html_<^._
import models.Locs.Loc
import org.scalajs.dom.html.Div

object LayoutComponent extends HtmlTags {

  case class Props(c: RouterCtl[Loc], r: Resolution[Loc])

  case class State()

  private val component =
    ScalaComponent.builder[Props]("LayoutComponent")
      .initialState(State())
      .renderBackend[Backend]
      .componentDidMount(_.backend.mounted())
      .build

  def apply(c: RouterCtl[Loc], r: Resolution[Loc]): Unmounted[Props, State, Backend] = component(Props(c, r))

  class Backend($: BackendScope[Props, State]) {

    def mounted(): Callback = Callback.empty


    def render(props: Props, state: State): VdomTagOf[Div] = {
      <.div(
        <.span(
          NavComponent(),
          <.div(
            ^.cls := "container-fluid",
            <.div(
              ^.cls := "row",
              SidebarComponent(),
              <.main(
                ^.role := "main",
                ^.cls := "col-md-11 ml-sm-auto pt-3 px-4",
                props.r.render()
              )
            )
          )
        )
      )
    }
  }

}
