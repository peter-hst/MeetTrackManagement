import model.Talk
import org.junit.Test


/**
  * Created by Peter on 2016/3/7.
  */
class ListTest {
  @Test def hello: Unit ={
    println("Hello,World")
  }
  @Test def strToTulp(): Unit ={
    val s = "Ruby on Rails: Why We Should Move On 60min"
    import utils._
    val t = s.toTupl
    assert(t._1 == 60)
  }
  @Test def strToTalk(){
    val s = "Ruby vs. Clojure for Back-End Development 30min"
    import utils._
    val talk = s.toTalk
    assert(talk.isInstanceOf[Talk])
    assert(talk.time == 30)
  }
}
