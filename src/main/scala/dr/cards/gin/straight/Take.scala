package dr.cards.gin.straight

import dr.cards.model.Play
import dr.cards.model.Player
import dr.cards.gin.straight.GinPlay._

class Take( game : StraightGin ) extends GinPlay( game ) {

  override def execute() = {

    val nextState = setNextState()

    switchPlayer( nextState )

    val player = nextState.player

    val c = nextState.discards.pop

    nextState.hands(player).cards += c

  }
}