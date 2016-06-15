package com.gilt.gfc.play.api.http

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.gilt.gfc.play.test.BaseSpec
import play.api.mvc.Results
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AbstractLoggingFilterSpec extends BaseSpec with Results {

  "AbstractLoggingFilter" must {

    "Add Request-Time & Request-Processed-By headers" in {
      val filter = TestLoggingFilter(Seq.empty)
      val result = filter.apply(rh => Future.successful(Ok))(FakeRequest(GET, "/some/url"))

      status(result) mustEqual OK
      headers(result).contains("Request-Time") mustEqual true
      headers(result).contains("Request-Processed-By") mustEqual true
    }

    "Ignore specified User-Agents" in {
      val filter = TestLoggingFilter(Seq("SuperAnnoyingBot"))
      val result = filter.apply(rh => Future.successful(Ok))(FakeRequest(GET, "/some/url").withHeaders("User-Agent" -> "SuperAnnoyingBot"))

      status(result) mustEqual OK
      headers(result).contains("Request-Time") mustEqual false
    }

  }


  object TestLoggingFilter {
    implicit val sys = ActorSystem()
    implicit val mat = ActorMaterializer()
    def apply(ignoreUserAgents: Seq[String]) = new AbstractLoggingFilter {
      override protected val IgnoreUserAgents: Seq[String] = ignoreUserAgents
    }
  }

}
