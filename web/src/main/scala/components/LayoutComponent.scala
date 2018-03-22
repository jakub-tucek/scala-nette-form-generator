package components

import _root_.utils.HtmlTags
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.{Resolution, RouterCtl}
import models.Locs.Loc

object LayoutComponent extends HtmlTags {

  case class Props(c: RouterCtl[Loc], r: Resolution[Loc])

  case class State()

  private val component =
    ScalaComponent.builder[Props]("LayoutComponent")
      .initialState(State())
      .renderBackend[Backend]
      .componentDidMount(_.backend.mounted())
      .build

  def apply(c: RouterCtl[Loc], r: Resolution[Loc]) = component(Props(c, r))

  class Backend($: BackendScope[Props, State]) {

    def mounted() = Callback.empty


    def render(props: Props, state: State) = {
      <.span(
        NavComponent(),
        <.div(
          ^.cls := "container-fluid",
          <.div(
            ^.cls := "row",
            SidebarComponent(),
            <.main(
              ^.role := "main",
              ^.cls := "col-md-9 ml-sm-auto col-lg-10 pt-3 px-4",
              <.div(
                ^.cls := "d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom",
                <.h1(
                  ^.cls := "h2",
                  "Nette generator"
                )
              ),
              props.r.render()
            )
          )
        )
      )
    }
  }
}
