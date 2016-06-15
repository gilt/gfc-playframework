package com.gilt.gfc.play.api.http

import java.net.InetAddress

import akka.stream.Materializer
import play.api.Logger
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

abstract class AbstractLoggingFilter(implicit val mat: Materializer, ec: ExecutionContext) extends Filter {

  private val Log = Logger("requests")

  def apply(nextFilter: (RequestHeader) => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
    if(!IgnoreUserAgents.contains(requestHeader.headers.get("User-Agent").getOrElse(""))) {
      val startTime = System.currentTimeMillis
      Log.debug(s"[${requestHeader.remoteAddress}] - ${requestHeader.method} ${requestHeader.uri}: start request")
      Log.debug(s"[${requestHeader.remoteAddress}] - ${requestHeader.method} ${requestHeader.uri}: Headers: ${requestHeader.headers}")
      nextFilter(requestHeader).map { result =>
        val endTime = System.currentTimeMillis
        val requestTime = endTime - startTime
        Log.debug(s"[${requestHeader.remoteAddress}] - ${requestHeader.method} ${requestHeader.uri} took ${requestTime}ms and returned ${result.header.status}")
        result.withHeaders("Request-Time" -> s"$requestTime ms").withHeaders("Request-Processed-By" -> InetAddress.getLocalHost.getHostName)
      }
    } else {
      nextFilter(requestHeader)
    }
  }

  protected def IgnoreUserAgents: Seq[String]

}
