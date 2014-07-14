package dr.cards.gin.straight

import scala.collection.mutable.ListBuffer
import dr.cards.model.Card
import dr.cards.model.Hand
import dr.cards.model.Deck

class Seq( cs : ListBuffer[Card] ) extends Meld( cs ) {

  override def improves( card : Card ) : Boolean = {

    val sorted = cards.sorted( Card.suitRankOrdering )

    if( card.suit != cards.head.suit ){
      false
    }
    else {
      if( card.rank == cards.head.rank - 1 ){
        true
      }
      else if( card.rank == cards.last.rank + 1 ){
        true
      }
      else {
        false
      }
    }

  }
}