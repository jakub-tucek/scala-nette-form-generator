package screens

import java.time.OffsetDateTime

import autowire._
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.HtmlTags
import japgolly.scalajs.react.vdom.html_<^._
import models.Locs.Loc
import services.AjaxClient
import shared.service.WiredApi

object HomeScreen extends HtmlTags {

  case class Props(c: RouterCtl[Loc])

  case class State()

  private val component = ScalaComponent
    .builder[Props]("HomeScreen")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(_.backend.mounted())
    .build

  def apply(c: RouterCtl[Loc]) = component(Props(c))

  class Backend($: BackendScope[Props, State]) {

    def mounted() = Callback {
      AjaxClient[WiredApi].now().call().foreach {
        s: String => println(s)
      }
    }

    def render(props: Props, state: State): VdomTag = {
      <.div(
        <.h2("Lorem Ipsum"),
        <.div(
          OffsetDateTime.now.toString
        )
      )
    }
  }

}
