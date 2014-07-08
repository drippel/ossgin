package dr.cards.gin.straight

import scala.collection.mutable.ListBuffer
import dr.cards.model.Card
import dr.cards.model.Deck

class Pair( cs : ListBuffer[Card] ) extends Meld( cs ) {

}

object Pair {

  def find( cards : ListBuffer[Card] ) : ListBuffer[Meld] = {
    findPairsLoop( cards.clone, ListBuffer[Meld]() )
  }

  def findPairsLoop( cards : ListBuffer[Card], accum : ListBuffer[Meld] ) : ListBuffer[Meld] = {

    if( cards.isEmpty || cards.size <= 1 ){
      accum
    }
    else {

      val card = cards.head

      findPair( card, cards ) match {

        case Some(s) =>{
          accum += s
          val newCards = cards.filter( (c)=> { c.rank != card.rank } )
          findPairsLoop( newCards, accum )
        }
        case None => {
          findPairsLoop( cards.tail, accum )
        }
      }

    }
  }

  def findPair( card : Card, cards : ListBuffer[Card] ) : Option[Set] = {

    val set = cards.filter( ( c ) => { c.rank == card.rank } )

    if( set.size >= 2 ){
      Some(new Set(set))
    }
    else {
      None
    }

  }


  def main( args : Array[String] ) = {

    val cards = Deck.shuffle( Deck.standardDeck, 100 )
    val deal = cards.cards.take(40)
    Console.println( deal )
    for( set <- find( deal ) ){
      Console.println( set )
    }
  }
}