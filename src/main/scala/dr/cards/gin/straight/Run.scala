package dr.cards.gin.straight

import scala.collection.mutable.ListBuffer
import dr.cards.model.Card
import dr.cards.model.Hand
import dr.cards.model.Deck

class Run( cs : ListBuffer[Card] ) extends Meld( cs ) {

  override def improves( card : Card ) : Boolean = {

    // make sure the cards are sorted
    val sorted = cards.clone.sorted( Card.rankOrdering )

    if( card.suit != sorted.head.suit ){
      false
    }
    else {
      if( card.rank == sorted.head.rank - 1 ){
        true
      }
      else if( card.rank == sorted.last.rank + 1 ){
        true
      }
      else {
        false
      }
    }

  }
}