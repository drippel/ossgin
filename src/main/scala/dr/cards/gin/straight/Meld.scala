package dr.cards.gin.straight

import dr.cards.model.CardContainer
import scala.collection.mutable.ListBuffer
import dr.cards.model.Card

class Meld( cs : ListBuffer[Card] ) extends CardContainer {

  cards ++= cs

}