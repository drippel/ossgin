package dr.cards.gin.straight

import scala.collection.mutable.ListBuffer
import dr.cards.model.Card
import dr.cards.model.Deck

class Set( cs : ListBuffer[Card] ) extends Meld(cs) { }

object Set {

  def find( cards : ListBuffer[Card] ) : ListBuffer[Meld] = {
    findSetsLoop( cards.clone, ListBuffer[Meld]() )
  }

  def findSetsLoop( cards : ListBuffer[Card], accum : ListBuffer[Meld] ) : ListBuffer[Meld] = {

    if( cards.isEmpty || cards.size <= 2 ){
      accum
    }
    else {

      val card = cards.head

      findSet( card, cards ) match {

        case Some(s) =>{
          accum += s
          val newCards = cards.filter( (c)=> { c.rank != card.rank } )
          findSetsLoop( newCards, accum )
        }
        case None => {
          findSetsLoop( cards.tail, accum )
        }
      }

    }
  }

  def findSet( card : Card, cards : ListBuffer[Card] ) : Option[Set] = {

    val set = cards.filter( ( c ) => { c.rank == card.rank } )

    if( set.size >= 3 ){
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