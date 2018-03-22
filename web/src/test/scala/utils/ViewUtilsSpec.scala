package utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import org.scalatest.{FlatSpec, Matchers}

class ViewUtilsSpec extends FlatSpec with Matchers {

  "ViewUtils" should "format date" in {

      val date = LocalDateTime.of(2018,1,1,1,1)

      val formattedString = ViewUtils.formatDate(date)

      formattedString should be "01. 01. 2018 01:01"
  }
}