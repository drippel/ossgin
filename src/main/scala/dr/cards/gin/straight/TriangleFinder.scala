package dr.cards.gin.straight

import dr.cards.model.Card
import scala.collection.mutable.ListBuffer

object TriangleFinder extends MeldFinder[Triangle] {


  def stop( cards : List[Card] ) : Boolean = { cards.isEmpty || cards.size <= 2 }

  def sort( cards : List[Card] ) : List[Card] = { cards.sorted( new Card.SuitRankOrdering() ) }

  def findSingle( cards : List[Card] ) : Option[Triangle] = {

    val card = cards.head

    val ranks = cards.filter( (c) => { c.rank == card.rank && c.suit != card.suit } )

    if( !ranks.isEmpty ){
      // carry on
      val next = neighbors( card, cards )
      if( !next.isEmpty ){
        val lb = ListBuffer[Card]( card, ranks.head, next.head )
        Some( new Triangle( lb ) )
      }
      else {
        None
      }
    }
    else {
      // try neighbors - then the rank of the neighbor
      val next = neighbors( card, cards )
      if( !next.isEmpty ){

        val nextHead = next.head
        val nextRanks = cards.filter( (c) => { c.rank == nextHead.rank && c.suit != nextHead.suit } )
        if( !nextRanks.isEmpty ){
            val lb = ListBuffer[Card]( card, nextHead, nextRanks.head )
            Some( new Triangle( lb ) )
        }
        else {
          None
        }
      }
      else {
          None
      }
    }
  }


  def neighbors( card : Card, cards : List[Card] ) : List[Card] = {
      cards.filter( (c) => { c.suit == card.suit && ( ( c.rank == card.rank - 1 ) || ( c.rank == card.rank + 1 ) ) } )
  }
}