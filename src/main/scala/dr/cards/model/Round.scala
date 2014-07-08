package dr.cards.model

import scala.collection.mutable.ListBuffer

class Round {

  var beginState : State = null
  var dealer : Player = null

  def currentState() = {
    var cs = beginState

    var found = false
    while( !found ){
      if( cs.next == null ){
        found = true
      }
      else {
        cs = cs.next
      }
    }

    cs
  }

}