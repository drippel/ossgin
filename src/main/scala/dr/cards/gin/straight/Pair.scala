package dr.cards.gin.straight

import scala.collection.mutable.ListBuffer
import dr.cards.model.Card
import dr.cards.model.Deck

class Pair( cs : ListBuffer[Card] ) extends Meld( cs ) { }