package dr.cards.gin.straight

import scala.collection.mutable.ListBuffer
import dr.cards.model.Card
import dr.cards.model.Hand
import dr.cards.model.Deck

object SeqFinder extends MeldFinder[Seq] {


  def findSingle( cards : List[Card] ) : Option[Seq] = {

    val possible = new ListBuffer[Card]
    possible += cards.head

    var rest = cards.tail

    if( !rest.isEmpty && isNext( possible.last, rest.head ) ){
      possible += rest.head
      Some( new Seq( possible.clone ) )
    }
    else {
      None
    }

  }

  def isNext( c1 : Card, c2 : Card ) : Boolean = {
    c1.suit == c2.suit && c1.rank == c2.rank -1
  }

  def sort( cards : List[Card] ) : List[Card] = { cards.sorted( new Card.SuitRankOrdering() ) }

  def stop( cards : List[Card] ) : Boolean = { cards.isEmpty || cards.size <= 1 }

}