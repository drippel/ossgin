package dr.cards.model

import scala.collection.mutable.ListBuffer

class Round {

  var beginState : State = null
  var dealer : Player = null

  def currentState() = {
    var cs = beginState
    while( cs.next != null ){
      cs = cs.next
    }

    cs
  }

  def states() : List[State] = {

    val buf = ListBuffer[State]()

    var end = false
    var cs = beginState
    while( cs.next != null ){
      buf += cs
      cs = cs.next
    }

    buf.toList

  }

}