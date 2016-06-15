package com.gilt.gfc.play.api.http

import javax.inject._

import play.api._
import play.api.http.{DefaultHttpErrorHandler, MimeTypes}
import play.api.libs.json.Json
import play.api.mvc.Results._
import play.api.mvc._
import play.api.routing.Router

import scala.concurrent._

@Singleton
class JsonHttpErrorHandler @Inject()(env: Environment,
                                     config: Configuration,
                                     sourceMapper: OptionalSourceMapper,
                                     router: Provider[Router]) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {

  private val Log = Logger("requests")

  /**
    * ====================================  Client Errors - 4xx  ====================================
    */

  // called when a route is found, but it was not possible to bind the request parameters
  override def onBadRequest(request: RequestHeader, error: String): Future[Result] = {
    Log.warn(s"Got a bad request [${request.method} - ${request.rawQueryString}] - $error")
    Future.successful(BadRequest(Json.toJson(error)).as(MimeTypes.JSON))
  }

  override def onNotFound(request: RequestHeader, error: String): Future[Result] = {
    Future.successful(NotFound(Json.toJson(s"Not found: $error")).as(MimeTypes.JSON))
  }

  /**
    * ====================================  Server Errors - 5xx  ====================================
    */

  override def onDevServerError(request: RequestHeader, exception: UsefulException): Future[Result] = on5xxError(request, exception)

  override def onProdServerError(request: RequestHeader, exception: UsefulException): Future[Result] = on5xxError(request, exception)

  override def logServerError(request: RequestHeader, exception: UsefulException): Unit = {
    Log.error(s"Exception processing request [${request.method} ${request.uri}]", exception)
  }

  private def on5xxError(request: RequestHeader, exception: UsefulException): Future[Result] = {
    Option(exception.getCause) match {
      case Some(cause: TimeoutException) => Future.successful(GatewayTimeout(Json.toJson(Option(cause.getMessage).getOrElse(exception.getMessage))).as(MimeTypes.JSON))
      case Some(cause) => Future.successful(InternalServerError(Json.toJson(cause.getMessage)).as(MimeTypes.JSON))
      case None => Future.successful(InternalServerError(Json.toJson(exception.getMessage)).as(MimeTypes.JSON))
    }
  }

}
