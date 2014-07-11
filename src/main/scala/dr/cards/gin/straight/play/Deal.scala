package dr.cards.gin.straight.play
import dr.cards.model.Player
import dr.cards.gin.straight.StraightGin

class Deal( game : StraightGin, player : Player ) extends GinPlay( game, player ) {

  override def execute() = {

    val nextState = setNextState()

    val deck = game.shuffle( game.newDeck )

    val dealer = nextState.player
    val other = game.opponent( dealer )

    for( i <- 0 to 9 ){
      val c1 = deck.cards.remove(0)
      nextState.hands(other).cards += c1
      val c2 = deck.cards.remove(0)
      nextState.hands(dealer).cards += c2
    }

    nextState.discards.push( deck.cards.remove(0) )

    nextState.stock ++= deck.cards

  }


}