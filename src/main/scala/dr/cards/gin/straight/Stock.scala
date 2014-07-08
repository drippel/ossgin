package dr.cards.gin.straight

import dr.cards.model.Play
import dr.cards.model.Player
import dr.cards.gin.straight.GinPlay._

class Stock( game : StraightGin ) extends GinPlay( game ) {

  override def execute() = {

    val nextState = setNextState()

    switchPlayer( nextState )

    val player = nextState.player

    val c = nextState.stock.remove(0)

    nextState.hands(player).cards += c

  }
}