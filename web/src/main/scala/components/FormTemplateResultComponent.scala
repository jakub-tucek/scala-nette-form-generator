package components

import facade.ReactHighlight
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.HtmlTags
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent}
import shared.domain.FormLatteTemplateList
import utils.ViewUtils

/**
  *
  * @author Jakub Tucek
  */
object FormTemplateResultComponent extends HtmlTags {

  case class Props(templateList: FormLatteTemplateList)

  private val component =
    ScalaComponent.builder[Props]("FormTemplateResultComponent")
      .initialState(State(None))
      .renderBackend[Backend]
      .build

  def apply(p: FormLatteTemplateList): Unmounted[Props, State, Backend] = component(Props(p))

  case class State(activeTabNo: Option[Int])

  class Backend($: BackendScope[Props, State]) {

    def render(p: Props, s: State): VdomElement = p.templateList match {
      case l: FormLatteTemplateList => {
        <.div(
          <.ul(
            ^.cls := "nav nav-tabs",
            l.templates
              .zipWithIndex
              .toTagMod(zipped => {
                val index = zipped._2

                <.li(
                  ^.cls := "nav-item",
                  <.a(
                    ^.cls := s"nav-link ${ViewUtils.getClassIfTrue(s.activeTabNo.contains(index), "active")}",
                    ^.onClick --> handleTabClick(index),
                    zipped._1.templateName
                  )
                )
              })
          ),
          <.div(
            ReactHighlight()(ReactHighlight.props())(
              l.templates(s.activeTabNo.getOrElse(0)).templateContent
            )
          )
        )
      }
      case _ => <.div()
    }

    private def handleTabClick(id: Int): Callback = {
      $.setState(State(Option(id)))
    }
  }

}
