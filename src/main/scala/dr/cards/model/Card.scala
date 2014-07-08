package dr.cards.model

import java.util.Random

class Card( val suit : Card.Suit, val rank : Card.Rank ) {

  override def toString() : String = {
    "[" + Card.printSuit( suit ) + "," + Card.printRank( rank ) + "]"
  }

  override def equals( o : Any ) : Boolean = {

    if ( !o.isInstanceOf[Card] ) {
      return false;
    }

    val c = o.asInstanceOf[Card]

    return c.suit == suit && c.rank == rank
  }

}

object Card {

  type Suit = Int
  type Rank = Int

  val rand = new Random()

  def main( args : Array[String] ) = {
    Console.print("\033[2J");
    Console.println( "cards..." )
    Console.println( randomCard() )
    Console.println( randomCard() )
    Console.println( "\u2660" )
    // val utf = new java.lang.String( "\u2660".getBytes(), "UTF-8" )
    val utf = new java.lang.String( "\u2660" )
    Console.println( utf )
    Console.println( " S \u2660 \u2661 \u2662 C \u2663 \u2664 H \u2665 D \u2666 \u2667\n" )
    Console.println( " \u2551 \n" )
    Console.println( "\u001B[34mblue\u001B[0m" )
    Console.println( "\u001B[44mblue\u001B[0m" )
    Console.println( "\u001B[34m \u2551 \u001B[0m" )
    val c : Char = 200
    Console.println( Character.toString(c))

    val code = "\u2660"
    Console.println( code )
  }

  def printSuit( s : Suit ) : String = {

    s match {
      case 0 => { "C" }
      case 1 => { "D" }
      case 2 => { "H" }
      case 3 => { "S" }
      case 4 => { "J" }
    }

  }

  def printRank( r : Rank ) : String = {

    r match {
      case 0 => { "A" }
      case 1 => { "2" }
      case 2 => { "3" }
      case 3 => { "4" }
      case 4 => { "5" }
      case 5 => { "6" }
      case 6 => { "7" }
      case 7 => { "8" }
      case 8 => { "9" }
      case 9 => { "T" }
      case 10 => { "J" }
      case 11 => { "Q" }
      case 12 => { "K" }
      case 13 => { "R" }
    }
  }

  def randomSuit() : Suit = { rand.nextInt( 4 ) }
  def randomRank() : Rank = { rand.nextInt( 13 ) }
  def randomCard() : Card = { new Card( randomSuit(), randomRank() ) }

  def stringToCard( s : String ) : Card = {
    new Card( charToSuit( s.charAt( 0 ) ), charToRank( s.charAt( 1 ) ) )
  }

  def charToSuit( c : Char ) : Suit = {

    c match {
      case 'C' => { 0 }
      case 'D' => { 1 }
      case 'H' => { 2 }
      case 'S' => { 3 }
      case 'J' => { 4 }
    }
  }

  def charToRank( c : Char ) : Rank = {

    c match {
      case 'A' => { 0 }
      case '2' => { 1 }
      case '3' => { 2 }
      case '4' => { 3 }
      case '5' => { 4 }
      case '6' => { 5 }
      case '7' => { 6 }
      case '8' => { 7 }
      case '9' => { 8 }
      case 'T' => { 9 }
      case 'J' => { 10 }
      case 'Q' => { 11 }
      case 'K' => { 12 }
      case 'R' => { 13 }
    }
  }

  class RankOrdering extends Ordering[Card] {
    def compare( a : Card, b : Card ) = {
      var s = a.rank compare b.rank
      if ( s == 0 ) {
        s = a.suit compare b.suit
      }

      s
    }
  }

  class SuitRankOrdering extends Ordering[Card] {
    def compare( a : Card, b : Card ) = {
      var s = a.suit.compare( b.suit )
      if ( s == 0 ) {
        s = a.rank.compare( b.rank )
      }

      s
    }
  }
}