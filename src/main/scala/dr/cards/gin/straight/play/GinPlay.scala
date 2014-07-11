package dr.cards.gin.straight.play

import dr.cards.model.Play
import dr.cards.model.Player
import dr.cards.gin.straight.StraightGin

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