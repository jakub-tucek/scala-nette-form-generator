package components

import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.HtmlTags
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent}
import shared.domain.ProcessFormSuccessResponse
import utils.ViewUtils

/**
  *
  * @author Jakub Tucek
  */
object SqlResultComponent extends HtmlTags {

  private val initialState: State = State(List(
    Tab(0, "Latte template", (res: ProcessFormSuccessResponse) => FormTemplateResultComponent.apply(res.latteTemplates)),
    Tab(1, "Nette generator", (res: ProcessFormSuccessResponse) => "Not implemented")
  ), 0)
  private val component = ScalaComponent
    .builder[Props]("SqlResultComponent")
    .initialState(initialState)
    .renderBackend[Backend]
    .build

  def apply(p: ProcessFormSuccessResponse): Unmounted[Props, State, Backend] = component(Props(p))

  case class Props(processFormResponse: ProcessFormSuccessResponse)

  case class Tab(id: Int, name: String, childrenBuilder: (ProcessFormSuccessResponse) => TagMod)

  case class State(tabs: List[Tab], activeTabId: Int)

  class Backend($: BackendScope[Props, State]) {

    def render(props: Props, state: State): VdomTag = {
      props.processFormResponse match {
        case res: ProcessFormSuccessResponse =>
          println("render1")

          <.div(
            <.div(
              ^.cls := "nav flex-column nav-pills",
              state.tabs.toTagMod(t => {
                <.a(
                  ^.cls := s"nav-link ${ViewUtils.getClassIfTrue(t.id == state.activeTabId, "active")}",
                  ^.onClick --> handleTabClick(t.id),
                  t.name
                )
              })
            ),
            <.div(
              state.tabs
                .filter(t => t.id == state.activeTabId)
                .toTagMod(t => {
                  <.div(
                    ^.cls := "tab-pane",
                    t.childrenBuilder(res)
                  )
                })
            )
          )
        case _ => <.div("No data loaded")
      }
    }

    private def handleTabClick(tabId: Int): Callback = {
      $.modState(s => State(s.tabs, tabId))
    }
  }

}
