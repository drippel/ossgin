package dr.cards.gin.straight

import dr.cards.model.Play
import scala.collection.mutable.HashMap
import dr.cards.model.Player
import dr.cards.model.Hand

class GinPlay( val game : StraightGin ) extends Play {

  def setNextState() = {
     // get the current state
    val currentState = game.gameState()
    val nextState = currentState.clone

    currentState.next = nextState
    nextState.prev = currentState

    nextState.play = this

    nextState
  }

}

object GinPlay {

  def otherPlayer( hands : HashMap[Player,Hand], player : Player ) : Player = {
    hands.keySet.filter( ( p : Player ) => { !player.name.equalsIgnoreCase(p.name)} ).head
  }

  def switchPlayer( state : GinState ) = {
    state.player = otherPlayer( state.hands, state.player )
  }

  def otherPlayer( game : StraightGin ) : Player = {
    val state = game.gameState()
    otherPlayer( state.hands, state.player )
  }

  def otherHand( game : StraightGin ) : Hand = {
    val s = game.gameState()
    s.hands(otherPlayer(game))
  }

  def currentHand( game : StraightGin ) : Hand = {
    val s = game.gameState()
    s.hands(s.player)
  }
}