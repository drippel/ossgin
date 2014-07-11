package dr.cards.gin.straight

import dr.cards.model.Play
import scala.collection.mutable.HashMap
import dr.cards.model.Player
import dr.cards.model.Hand

class GinPlay( val game : StraightGin, val player : Player ) extends Play {

  def setNextState() = {
     // get the current state
    val currentState = game.gameState()
    val nextState = currentState.clone

    currentState.next = nextState
    nextState.prev = currentState

    nextState.play = this

    nextState.player = player

    nextState
  }

}