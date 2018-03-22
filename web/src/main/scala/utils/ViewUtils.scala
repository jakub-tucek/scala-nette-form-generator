package utils

import java.text.SimpleDateFormat
import java.time.{LocalDateTime, OffsetDateTime}
import java.time.format.DateTimeFormatter

import org.scalajs.dom.experimental.intl.DateTimeFormatOptions

import scala.scalajs.js
import scala.scalajs.js.Date

/**
  *
  * @author Jakub Tucek
  */
object ViewUtils {

  def formatDate(dateTime: LocalDateTime): String = {
    val jsDate = new Date(Date.parse(dateTime.toString()))
    jsDate.toLocaleString
  }

}
