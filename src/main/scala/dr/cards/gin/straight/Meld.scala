package dr.cards.gin.straight

import dr.cards.model.CardContainer
import scala.collection.mutable.ListBuffer
import dr.cards.model.Card
import java.util.Random

class Meld( cs : ListBuffer[Card] ) extends CardContainer {

  val random = new Random()

  cards ++= cs

  def improves( card : Card ) : Boolean = false

  def getRandom() : Card = {
    val i = random.nextInt(cards.size)
    cards(i)
  }

}