package components

import facade.ReactHighlight
import japgolly.scalajs.react.component.Scala.Unmounted
import japgolly.scalajs.react.vdom.HtmlTags
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, ScalaComponent}
import shared.domain.FormLatteTemplateList

/**
  *
  * @author Jakub Tucek
  */
object FormTemplateResultComponent extends HtmlTags {

  case class Props(templateList: FormLatteTemplateList)

  private val component =
    ScalaComponent.builder[Props]("FormTemplateResultComponent")
      .renderBackend[Backend]
      .build

  def apply(p: FormLatteTemplateList): Unmounted[Props, Unit, Backend] = component(Props(p))


  class Backend(bs: BackendScope[Props, Unit]) {

    def render(p: Props): VdomElement = p.templateList match {
      case l: FormLatteTemplateList => {
        <.ul(
          ^.cls := "nav nav-tabs",
          l.templates.toTagMod(t => <.li(
            <.h3(t.templateName),
            ReactHighlight()(ReactHighlight.props())(
              t.templateContent
            )
          ))
        )
      }
      case _ => <.div()
    }
  }

}
