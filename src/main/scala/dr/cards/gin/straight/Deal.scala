package dr.cards.gin.straight

import dr.cards.model.Play
import dr.cards.model.Player
import dr.cards.model.Hand
import scala.collection.mutable.HashMap

class Deal( game : StraightGin ) extends GinPlay( game ) {

  override def execute() = {

    val nextState = setNextState()

    val deck = game.shuffle( game.newDeck )

    val dealer = nextState.player
    val other = GinPlay.otherPlayer( nextState.hands, dealer )

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