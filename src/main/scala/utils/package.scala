/**
  * Created by Peter on 2016/3/7.
  */
import java.util.Calendar
package object utils {
  implicit class StringUtil(s: String) {
    private val p = "[0-9]+".r

    def toTupl = {
      val min = p.findFirstIn(s).getOrElse("60").toInt
      val talk = (min, s)
      talk
    }

    def toTalk = {
      val t = this.toTupl
      val talk = new Talk(CalendarUtils.getCal, t._1, t._2)
      talk
    }
  }

  object TrackGen {
    def schedule(talks: List[Talk]) = {
      var stepCal = CalendarUtils.getCal
      val noonCal = CalendarUtils.getCal
      noonCal.set(Calendar.HOUR_OF_DAY, 12)
      val afterStartCal = CalendarUtils.getCal
      afterStartCal.set(Calendar.HOUR_OF_DAY,13)
      val eveningCal = CalendarUtils.getCal
      eveningCal.set(Calendar.HOUR_OF_DAY, 17)

      talks.foreach { t =>
        if(stepCal == eveningCal || stepCal.after(eveningCal)) {
          stepCal = CalendarUtils.getCal
        }
        if(stepCal == noonCal || (stepCal.after(noonCal) && stepCal.before(afterStartCal))){
          stepCal = CalendarUtils.getCal
          stepCal.set(Calendar.HOUR_OF_DAY,13)
        }
        t.start.set(Calendar.HOUR_OF_DAY, stepCal.get(Calendar.HOUR_OF_DAY))
        t.start.set(Calendar.MINUTE, stepCal.get(Calendar.MINUTE))
        stepCal.add(Calendar.MINUTE,t.time)
      }

      //val totalTime = talks.map(_.time).sum
      //val day = if (totalTime % 420 == 0) totalTime % 420 else totalTime % 420 + 1

      /*  val list = talks.map{t =>
        if(stepCal == noonCal){

        }
        t.start.set(Calendar.HOUR_OF_DAY,stepCal.get(Calendar.HOUR_OF_DAY))
        t.start.set(Calendar.MINUTE,stepCal.get(Calendar.MINUTE))
        stepCal.add(Calendar.MINUTE,t.time)
        t
      }*/
    }
  }
}