import service.TrackService

/**
  * Created by Peter on 2016/3/4.
  */
object MainApp {
  def main(args: Array[String]): Unit = {
    //文件名和文件编码变量
    val fileName = "events.txt"
    val encoding = "UTF-8"
    //把文件转成Talk集合,完成会议schedule
    val list = TrackService.makeSchedule(TrackService.handlerFileSource(fileName,encoding))
    //打印列出
    list.foreach { println}
  }
}
