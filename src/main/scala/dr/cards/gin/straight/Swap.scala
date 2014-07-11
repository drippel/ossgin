package dr.cards.gin.straight

import dr.cards.model.Player

class Swap( game : StraightGin, player : Player, a : Int, b : Int ) extends GinPlay( game, player ) {


  override def execute() = {

    val nextState = setNextState()

    val cards = nextState.hands(player).cards

    val ca = cards(a)
    val cb = cards(b)
    cards(a) = cb
    cards(b) = ca
  }

}