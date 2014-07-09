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

object Seq {


  def find( cards : ListBuffer[Card] ) : ListBuffer[Meld] = {

    // sorting will make life easier
    var copy = cards.clone.sorted( new Card.SuitRankOrdering() )
    findSeqLoop( copy, new ListBuffer[Meld]() )
  }


  def findSeqLoop( cards : ListBuffer[Card], accum : ListBuffer[Meld] ) : ListBuffer[Meld] = {

    if( cards.isEmpty || cards.size <= 1 ){
      accum
    }
    else {
      findSeq( cards ) match {

        case Some(r) => {
          accum += r
          val rest = cards.filter( (c) => { !r.cards.contains(c) } )
          findSeqLoop( rest, accum )
        }
        case None => {
          findSeqLoop( cards.tail, accum )
        }

      }
    }

  }

  def findSeq( cards : ListBuffer[Card] ) : Option[Seq] = {

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


  def main( args : Array[String] ) : Unit = {

    val cards = Deck.shuffle( Deck.standardDeck, 100 )
    val deal = cards.cards.take(40)
    Console.println( deal.sorted( new Card.SuitRankOrdering() ) )
    for( set <- find( deal ) ){
      Console.println( set )
    }

  }
}