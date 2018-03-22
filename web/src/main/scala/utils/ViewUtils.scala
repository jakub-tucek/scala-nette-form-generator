package utils

import java.text.SimpleDateFormat
import java.time.{LocalDateTime, OffsetDateTime}
import java.time.format.DateTimeFormatter

import org.scalajs.dom.experimental.intl.DateTimeFormatOptions

/**
  *
  * @author Jakub Tucek
  */
object ViewUtils {

  private val format = DateTimeFormatter.ofPattern("DD. MM. YYYY HH:mm")

  def formatDate(dateTime: LocalDateTime): String = dateTime.format(format)

}
