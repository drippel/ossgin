package dr.cards.gin.straight

import dr.cards.model.Play
import dr.cards.model.Player

class Take( game : StraightGin, player : Player ) extends GinPlay( game, player ) {

  override def execute() = {

    val nextState = setNextState()

    val player = nextState.player

    nextState.card = nextState.discards.pop

    nextState.hands(player).cards += nextState.card

  }
}