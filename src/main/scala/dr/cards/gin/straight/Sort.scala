package dr.cards.gin.straight

import dr.cards.model.Card
import dr.cards.model.Player

class Sort( game : StraightGin, player : Player, order : Ordering[Card] ) extends GinPlay( game ) {

 override def execute() = {

    val nextState = setNextState()

    val cards = nextState.hands(player).cards
    val sorted = cards.sorted(order)
    cards.clear
    cards.appendAll( sorted )

  }
}