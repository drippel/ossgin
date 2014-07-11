package dr.cards.gin.straight

import AnalyzeHand._
import dr.cards.model.Card
import scala.collection.mutable.ListBuffer

class Ninja extends ComputerPlayer {

  override def name = "Ninja"
  override def description = "Ninja"

  override def choose( game : StraightGin ) = {

    // the last play was by the opponent
    val hand = GinPlay.otherHand( game ).clone

    val card = game.currentDiscard()

    // lets figure some stuff out
    // the ninja will almost never take from the discards
    // however, if it leads to gin - no brainer - take the card and win
    // or almost gin - in certain stages of the game - he might take the discard
    // say taking the discard leads to dead wood < 2-3 cards
    // maybe some more calculation here
    // if the card does make a set/run - calc what are the chances of extending it again?
    // if we extends a pair to a set but the 4th card is already in discard, should he take?
    // calc deadwood prev to take and compare to deadwood after take?
    // taking and and completing a set might be a no brainer as well
    hand.cards += card

    if( detectGin( hand ) ) {
      take( game )
    }
    else {
      if( deadwoodRunsFirst( hand.cards ).size <= 3 ){
        take( game )
      }
      else {
        stock( game )
      }
    }
  }

  override def discard( game : StraightGin ) = {

    val hand = GinPlay.currentHand( game )
    val cards = hand.cards.clone

    // find all the sets
    val sets = Set.find( cards )
    remainder( cards, sets.toList )

    val runs = Run.find( cards )
    val left = remainder( cards, ( runs.toList ++ sets.toList ) )

    // gin should have already been detected but...
    if ( !left.isEmpty ) {

      // try not to discard a pair and a seq
      val pairs = Pair.find( left )
      val afterPairs = remainder( left, pairs.toList )
      val seqs = Seq.find( afterPairs )
      val afterSeq = remainder( afterPairs, ( pairs.toList ++ seqs ) )

      val pos = if ( !afterSeq.isEmpty ) {

        // dont discard something that is close to the opponents takes
        val afterCompare = compareToTakes( game, afterSeq )
        if ( afterCompare.isEmpty ) {
          hand.cards.indexOf( getRandom( afterCompare.toList ) )
        } else {
          // we have cards left that is not in a pair and seq
          hand.cards.indexOf( getRandom( afterSeq.toList ) )
        }
      } else {
        // all the remaining cards are in seqs or pairs
        if ( !seqs.isEmpty ) {

          // discard something from seqs first
          val flat = seqs.flatten( ( s ) => { s.cards } )
          val i = random.nextInt( flat.size )
          hand.cards.indexOf( flat( i ) )

        } else {
          val flat = pairs.flatten( ( s ) => { s.cards } )
          val i = random.nextInt( flat.size )
          hand.cards.indexOf( flat( i ) )
        }
      }
      val d = new Discard( game, pos )
      d.execute
    } else {
      // gin?

    }

  }

  def compareToTakes( game : StraightGin, cards : ListBuffer[Card] ) : ListBuffer[Card] = {

    val state = game.gameState()
    val opponentTakes = game.opponentTakes( state.player )
    val rememberedTakes = opponentTakes.take( 2 )

    // if any cards are of the same rank as a card in rememberedTakes
    // remove from the list

    for (
      c <- cards;
      if ( !rememberedTakes.exists( ( r ) => { c.rank == r.rank } ) )
    ) yield c

  }

}