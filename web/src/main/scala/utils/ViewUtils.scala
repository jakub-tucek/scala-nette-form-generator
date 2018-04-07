package utils

import java.time.LocalDateTime

import scala.scalajs.js.Date

/**
  *
  * @author Jakub Tucek
  */
object ViewUtils {

  def formatDate(dateTime: LocalDateTime): String = {
    val jsDate = new Date(Date.parse(dateTime.toString))
    jsDate.toLocaleString
  }

  def getClassIfTrue(condition: Boolean, className: String): String =
    if (condition) className else ""
}
