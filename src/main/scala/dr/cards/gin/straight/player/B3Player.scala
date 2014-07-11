package dr.cards.gin.straight.player

import dr.cards.model.Card
import scala.collection.mutable.ListBuffer
import dr.cards.model.Player
import dr.cards.gin.straight.Run
import dr.cards.gin.straight.Pair
import dr.cards.gin.straight.Seq
import dr.cards.gin.straight.Set
import dr.cards.gin.straight.StraightGin
import dr.cards.gin.straight.AnalyzeHand._

class B3Player( game : StraightGin, player : Player ) extends ComputerPlayer( game, player ) {

  override def name = "Beginner 3"
  override def description = "A little better, remembers somethings."

  override def choose() = {

    val hand = game.currentHand(player)

    // lets look at the discard pile
    val card = game.currentDiscard

    // does this card make or complete a set?
    if( makesSet( hand.cards, card ) || completesSet( hand.cards, card ) ){
      // should a beginner miss a play periodically?
      take()
    }
    else if( makesRun( hand.cards, card ) || improvesRun( hand.cards, card ) ){
      // should a beginner miss a play periodically?
      take()
    }
    else {
      stock()
    }
  }


  override def discard() = {

    val hand = game.currentHand(player)
    val cards = hand.cards.clone

    // find all the sets
    val sets = Set.find(cards)
    remainder( cards, sets.toList )

    val runs = Run.find(cards)
    val left = remainder( cards, ( runs.toList ++ sets.toList ) )

    // gin should have already been detected but...
    if( !left.isEmpty ) {

      // try not to discard a pair and a seq
      val pairs = Pair.find(left)
      val afterPairs = remainder( left, pairs.toList )
      val seqs = Seq.find( afterPairs )
      val afterSeq = remainder( afterPairs, ( pairs.toList ++ seqs ) )

      val pos = if( !afterSeq.isEmpty ){

        // dont discard something that is close to the opponents takes
        val afterCompare = compareToTakes( game, afterSeq )
        if( afterCompare.isEmpty ) {
          hand.cards.indexOf( getRandom( afterCompare.toList ) )
        }
        else {
          // we have cards left that is not in a pair and seq
          hand.cards.indexOf( getRandom( afterSeq.toList ) )
        }
      }
      else {
        // all the remaining cards are in seqs or pairs
        if( !seqs.isEmpty ){

          // discard something from seqs first
          val flat = seqs.flatten( (s) => { s.cards })
          val i = random.nextInt(flat.size)
          hand.cards.indexOf(flat(i))

        }
        else {
          val flat = pairs.flatten( (s) => { s.cards })
          val i = random.nextInt(flat.size)
          hand.cards.indexOf(flat(i))
        }
      }

      discard(pos)
    }
    else {
      // gin?

    }

  }

  def compareToTakes( game : StraightGin, cards : ListBuffer[Card] ) : ListBuffer[Card] = {

    val opponentTakes = game.opponentTakes(player)
    val rememberedTakes = opponentTakes.take(2)

    // if any cards are of the same rank as a card in rememberedTakes
    // remove from the list

    for( c <- cards ;
         if( !rememberedTakes.exists( (r) => { c.rank == r.rank } ) )
       ) yield c

  }




}