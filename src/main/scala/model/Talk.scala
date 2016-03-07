package model



import java.util.Calendar
import java.text.SimpleDateFormat
import java.lang.Comparable

class Talk(val start: Calendar, val time: Int, val title: String) extends Ordered[Talk] {
  private val df = new SimpleDateFormat("hh:mma")
  
  def end = {
    val e = start.clone().asInstanceOf[Calendar]
    e.add(Calendar.MINUTE, time)
    e
  }

  override def toString = {
    df.format(start.getTime) + "--" + df.format(this.end.getTime) + " " + title
  }

  def compare(that: Talk) = {
    if (this.start == that.start) 0 else if (this.start.after(that.start)) 1 else -1
  }

}