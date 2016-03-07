package service

import java.util.Calendar

import model.Talk
import utils.CalendarUtils

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

/**
  * Created by Peter on 2016/3/7.
  */
import utils._
object TrackService {

  //业务逻辑和规则
  def makeSchedule(talks: List[Talk]) = {
    val sortedList = talks.groupBy(_.time % 10 == 0).values.toList.flatten

    //当前安排会议时间进度
    var stepCal = CalendarUtils.getCal
    //午餐开始时间
    val noonCal = CalendarUtils.getCal
    noonCal.set(Calendar.HOUR_OF_DAY, 12)
    noonCal.set(Calendar.MINUTE, 0)

    //下午会议开始时间
    val afterStartCal = CalendarUtils.getCal
    afterStartCal.set(Calendar.HOUR_OF_DAY, 13)
    afterStartCal.set(Calendar.MINUTE, 0)
    //Networking Event
    val networkCal = CalendarUtils.getCal
    networkCal.set(Calendar.HOUR_OF_DAY, 16);
    networkCal.set(Calendar.MINUTE, 0)
    //一天会议结束时间
    val eveningCal = CalendarUtils.getCal
    eveningCal.set(Calendar.HOUR_OF_DAY, 17)
    eveningCal.set(Calendar.MINUTE, 0)

    //总的会议时间
    //val totalTime = sortedList.map(_.time).sum
    val count = sortedList.size
    val buffer = ArrayBuffer[Talk]()
    for (i <- 0 until count) {
      if (stepCal == noonCal || stepCal.after(noonCal) && stepCal.before(afterStartCal)) {
        val lunch = new Talk(noonCal.clone().asInstanceOf[Calendar], 60, "Lunch")
        buffer += lunch
        stepCal = CalendarUtils.getCal
        stepCal.set(Calendar.HOUR_OF_DAY, 13)
        stepCal.set(Calendar.MINUTE, 0)
      } else if (stepCal == networkCal || stepCal == eveningCal || stepCal.after(networkCal) && stepCal.before(eveningCal)) {
        buffer += new Talk(stepCal.clone().asInstanceOf[Calendar], 60, "Networking Event")
        stepCal.add(Calendar.MINUTE, 60)
      } else if (stepCal.after(eveningCal)) {
        stepCal = CalendarUtils.getCal
      }
      sortedList(i).start.set(Calendar.HOUR_OF_DAY, stepCal.get(Calendar.HOUR_OF_DAY))
      sortedList(i).start.set(Calendar.MINUTE, stepCal.get(Calendar.MINUTE))
      stepCal.add(Calendar.MINUTE, sortedList(i).time)
      buffer += sortedList(i)
    }
    buffer.toList
  }

   def handlerFileSource(file:String,enc:String) ={
    //获得classpath目录下的events。txt资源文件
    val source = Source.fromInputStream(getClass.getClassLoader.getResourceAsStream("events.txt"), "UTF-8")
    //预处理，解析talk字符串中的的时间参数
    val talkList = source.getLines.map(_.toTalk).toList
    //关闭资源
    source.close
    talkList
  }
}
