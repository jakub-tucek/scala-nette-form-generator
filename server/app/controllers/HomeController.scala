package controllers

import io.circe.Json
import io.circe.java8.time.TimeInstances
import io.circe.parser._
import javax.inject._
import play.api.i18n.I18nSupport
import play.api.mvc._
import service.{AutoWireServer, WiredApiService}
import shared.service.WiredApi
import shared.utils.Implicits

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(val api: WiredApiService, cc: ControllerComponents)
  extends AbstractController(cc)
    with I18nSupport
    with Implicits
    with TimeInstances {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index(path: String) = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  private val procedureCallRouter: autowire.Core.Request[Json] => Future[Result] = AutoWireServer
    .route[WiredApi](new WiredApiService)(_)
    .map(_.noSpaces).map(Ok(_))

  def api(path: String): Action[String] = {
    Action.async[String](parse.text) { request =>
      decode[Map[String, Json]](request.body) match {
        case Right(s) =>
          val procedureCallRequest = autowire.Core.Request(path.split('/'), s)
          procedureCallRouter(procedureCallRequest)
        case Left(v) =>
          println("Error" + v)
          Ok("Request failed: " + v).as("application/json").asFuture
      }
    }
  }
}