package dr.cards.gin.straight.play

import dr.cards.model.Card
import dr.cards.model.Player
import dr.cards.gin.straight.StraightGin

class Sort( game : StraightGin, player : Player, order : Ordering[Card] ) extends GinPlay( game, player ) {

 override def execute() = {

    val nextState = setNextState()

    val cards = nextState.hands(player).cards
    val sorted = cards.sorted(order)
    cards.clear
    cards.appendAll( sorted )

  }
}