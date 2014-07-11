package dr.cards.gin.straight

import dr.cards.model.Player

class Discard( game : StraightGin, player : Player, index : Int ) extends GinPlay( game, player ) {

  override def execute() = {

    val nextState = setNextState()

    // the previous play should have been a stock or take,

    val player = nextState.player
    val hand   = nextState.hands(player)

    nextState.card = hand.cards.remove(index)

    nextState.discards.push(nextState.card)

  }

}