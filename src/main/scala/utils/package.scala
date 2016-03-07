/**
  * Created by Peter on 2016/3/6.
  */

import scala.collection.mutable.{ArrayBuffer}
import model.Talk


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
}