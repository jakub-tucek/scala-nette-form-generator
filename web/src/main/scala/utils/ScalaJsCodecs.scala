package utils

import java.time.LocalDateTime

import cats.syntax.either._
import io.circe.Decoder

/**
  *
  * @author Jakub Tucek
  */
trait ScalaJsCodecs {

  implicit val decodeLocalDateTime: Decoder[LocalDateTime] = Decoder.decodeString.emap {
    str => Either.catchNonFatal(LocalDateTime.parse(str)).leftMap(t => "LocalDateTime")
  }

}
