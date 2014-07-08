package dr.cards.model

import scala.collection.mutable.MutableList
import java.util.Random
import scala.collection.mutable.ListBuffer

class Deck() extends CardContainer {

  override def clone() = {
    val d = new Deck()
    d.cards ++= cards
    d
  }

}



object Deck {

  def standardDeck() = {
    val deck = new Deck()
    for( i <- 0 to 3 ){
      for( j <- 0 to 12 ){
        deck.cards += new Card(i,j)
      }
    }
    deck
  }

  def main( args : Array[String] ) = {
    Console.println( standardDeck() )
    Console.println( shuffle( standardDeck() ) )
    Console.println( shuffle( standardDeck(), 1000 ) )
  }

  def shuffle( deck : Deck ) : Deck  = {

    val rand = new Random()

    val newDeck = new Deck()

    val srcList = deck.cards.clone

    while( !srcList.isEmpty ){
      newDeck.cards += srcList.remove( rand.nextInt( srcList.size ) )
    }

    newDeck
  }

  def shuffle( deck : Deck, times : Int ) : Deck = {

    var d = deck

    for( i <- 0 to times ){
      d = shuffle(d)
    }

    d
  }

}