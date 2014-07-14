package dr.cards.gin.straight

import dr.cards.model.Card
import scala.collection.mutable.ListBuffer

object RunFinder extends MeldFinder[Run] {

  def findSingle( cards : List[Card] ) : Option[Run] = {

    val possible = new ListBuffer[Card]
    possible += cards.head

    var rest = cards.tail

    while( !rest.isEmpty && isNext( possible.last, rest.head ) ){
      possible += rest.head
      rest = rest.tail
    }

    if( possible.size >= 3 ){
      Some( new Run( possible.clone ) )
    }
    else {
      None
    }

  }

  def isNext( c1 : Card, c2 : Card ) : Boolean = { c1.suit == c2.suit && c1.rank == c2.rank -1 }

  def stop( cards : List[Card] ) : Boolean = {
    cards.isEmpty || cards.size <= 2
  }

  def sort( cards : List[Card] ) : List[Card] = { cards.sorted( new Card.SuitRankOrdering() ) }

}