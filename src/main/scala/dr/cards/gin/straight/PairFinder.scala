package dr.cards.gin.straight

import scala.collection.mutable.ListBuffer
import dr.cards.model.Card
import dr.cards.model.Deck

object PairFinder extends MeldFinder[Pair] {

  def findSingle( cards : List[Card] ) : Option[Pair] = {

    val card = cards.head

    val set = cards.filter( ( c ) => { c.rank == card.rank } )

    if( set.size >= 2 ){
      val lb = ListBuffer[Card]()
      lb ++= set
      Some(new Pair(lb))
    }
    else {
      None
    }

  }

  def stop( cards : List[Card] ) : Boolean = { cards.isEmpty || cards.size <= 1 }

  def sort( cards : List[Card] ) : List[Card] = { cards.sorted( new Card.RankOrdering() ) }
}