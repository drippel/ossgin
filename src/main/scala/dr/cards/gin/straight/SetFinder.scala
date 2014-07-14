package dr.cards.gin.straight

import scala.collection.mutable.ListBuffer
import dr.cards.model.Card

object SetFinder extends MeldFinder[Set] {

  def findSingle( cards : List[Card] ) : Option[Set] = {

    val card = cards.head

    val set = cards.filter( ( c ) => { c.rank == card.rank } )

    if( set.size >= 3 ){
      val lb = ListBuffer[Card]()
      lb ++= set
      Some(new Set(lb))
    }
    else {
      None
    }

  }

  def stop( cards : List[Card] ) : Boolean = { cards.isEmpty || cards.size <= 2 }

  def sort( cards : List[Card] ) : List[Card] = { cards.sorted( new Card.RankOrdering() ) }


}