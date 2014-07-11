package dr.cards.gin.straight

class Discard( game : StraightGin, index : Int ) extends GinPlay( game ) {

  override def execute() = {

    val nextState = setNextState()

    // the previous play should have been a stock or take,

    val player = nextState.player
    val hand   = nextState.hands(player)

    nextState.card = hand.cards.remove(index)

    nextState.discards.push(nextState.card)

  }

}