package com.gilt.gfc.play.test

import java.io.File

import com.google.inject.util.Providers
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.routing.Router
import play.api.{Configuration, Environment, Mode, OptionalSourceMapper}

class BaseSpec extends PlaySpec with MockitoSugar {

  protected val PlayEnv = Environment(new File("."), getClass.getClassLoader, Mode.Test)
  protected val Conf = Configuration.empty
  protected val NoSourceMapper = new OptionalSourceMapper(None)
  protected val EmptyRouteProvider = Providers.of(Router.empty)

}
