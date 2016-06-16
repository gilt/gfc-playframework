# gfc-playframework [![Build Status](https://travis-ci.com/gilt/gfc-playframework.svg?token=ehYmhiZsnqWFWAoybfVc&branch=master)](https://travis-ci.com/gilt/gfc-playframework) [![Join the chat at https://gitter.im/gilt/gfc](https://badges.gitter.im/gilt/gfc.svg)](https://gitter.im/gilt/gfc?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

A library that contains a bunch of Play Framework 2.5+ utility classes. Part of the gilt foundation classes.

## Example Usage

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
    sbt +publish

## License
Copyright 2014 Gilt Groupe, Inc.

Licensed under the MIT License: http://opensource.org/licenses/MIT