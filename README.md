# gfc-playframework [![Build Status](https://travis-ci.org/gilt/gfc-playframework.svg?branch=master)](https://travis-ci.org/gilt/gfc-playframework) [![Join the chat at https://gitter.im/gilt/gfc](https://badges.gitter.im/gilt/gfc.svg)](https://gitter.im/gilt/gfc?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

A library that contains a bunch of Play Framework 2.5+ utility classes. Part of the [Gilt Foundation Classes](https://github.com/gilt?query=gfc).

## Getting gfc-playframework

The latest version is 0.0.2, which is built against Scala 2.11.x.

If you're using SBT, add the following line to your build file:

```scala
libraryDependencies += "com.gilt" %% "gfc-playframework" % "0.0.2"
```

For Maven and other build tools, you can visit [search.maven.org](http://search.maven.org/#search%7Cga%7C1%7Ccom.gilt%20gfc).
(This search will also list other available libraries from the gilt fundation classes.)

## Contents and Example Usage

### com.gilt.gfc.play.api.http.JsonHttpErrorHandler

Replaces the default Play! Html error responses with JSON responses.

#### application.conf

```
play.http.errorHandler = "com.gilt.gfc.play.api.http.JsonHttpErrorHandler"
```

### com.gilt.gfc.play.api.http.AbstractLoggingFilter

HTTP filter for logging incoming requests and tagging responses with time taken as well as instance host that processed the request.

```
import javax.inject.{Inject, Singleton}
import akka.stream.Materializer
import scala.concurrent.ExecutionContext

@Singleton
class LoggingFilter @Inject() (mat: Materializer, ec: ExecutionContext) extends AbstractLoggingFilter()(mat, ec) {
  override protected def IgnoreUserAgents: Seq[String] = Seq("ELB-HealthChecker/1.0") //Ignore AWS ELB
}
```


## Publishing

**NOTE:** Pleasse use [semver](http://semver.org/) for tagging/versioning

    git tag vX.X.X
    git push --tags
    sbt publish

## License
Copyright 2014 Gilt Groupe, Inc.

Licensed under the MIT License: http://opensource.org/licenses/MIT
