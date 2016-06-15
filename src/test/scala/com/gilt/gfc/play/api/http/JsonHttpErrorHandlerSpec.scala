package com.gilt.gfc.play.api.http

import java.util.concurrent.TimeoutException

import com.gilt.gfc.play.test.BaseSpec
import org.scalatest.concurrent.ScalaFutures
import play.api.UsefulException
import play.api.libs.json.JsString
import play.api.test.FakeRequest
import play.api.test.Helpers._

class JsonHttpErrorHandlerSpec extends BaseSpec {

  "JsonHttpErrorHandler" must {
    "Return 400 with JSON content" in {
      val handler = Handler()
      val result = handler.onBadRequest(FakeRequest(GET, "/some/url"), "test-error")

      status(result) mustEqual BAD_REQUEST
      contentType(result) mustEqual Some("application/json")
      contentAsJson(result) mustEqual JsString("test-error")
    }

    "Return 404 with JSON content" in {
      val handler = Handler()
      val result = handler.onNotFound(FakeRequest(GET, "/some/url"), "test-error")

      status(result) mustEqual NOT_FOUND
      contentType(result) mustEqual Some("application/json")
      contentAsJson(result) mustEqual JsString(s"Not found: test-error")
    }

    "Return 504 with JSON content for a timeout exception" in {
      val handler = Handler()
      val result = handler.onProdServerError(FakeRequest(GET, "/some/url"), new TestExceptionWithCause("test-error", new TimeoutException("game over")))

      status(result) mustEqual GATEWAY_TIMEOUT
      contentType(result) mustEqual Some("application/json")
      contentAsJson(result) mustEqual JsString("game over")
    }

    "Return 500 with JSON content including excpetion cause" in {
      val handler = Handler()
      val result = handler.onProdServerError(FakeRequest(GET, "/some/url"), new TestExceptionWithCause("test-error", new RuntimeException("i shot the sherrif")))

      status(result) mustEqual INTERNAL_SERVER_ERROR
      contentType(result) mustEqual Some("application/json")
      contentAsJson(result) mustEqual JsString("i shot the sherrif")
    }

    "Return 500 with JSON content" in {
      val handler = Handler()
      val result = handler.onProdServerError(FakeRequest(GET, "/some/url"), new TestException("test-error"))

      status(result) mustEqual INTERNAL_SERVER_ERROR
      contentType(result) mustEqual Some("application/json")
      contentAsJson(result) mustEqual JsString("test-error")
    }

  }


  object Handler {
    def apply() = new JsonHttpErrorHandler(PlayEnv, Conf, NoSourceMapper, EmptyRouteProvider)
  }

  case class TestException(msg: String) extends UsefulException(msg)
  case class TestExceptionWithCause(msg: String, c: Exception) extends UsefulException(msg, c)

}
