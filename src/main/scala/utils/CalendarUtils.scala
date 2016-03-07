package utils

import java.util.Calendar
object CalendarUtils {
  def getCal = {
      val cal = Calendar.getInstance
      cal.set(Calendar.HOUR_OF_DAY, 9)
      cal.set(Calendar.MINUTE, 0)
      cal.set(Calendar.SECOND, 0)
      cal
    }
}