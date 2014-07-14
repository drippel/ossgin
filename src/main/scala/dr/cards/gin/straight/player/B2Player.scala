package dr.cards.gin.straight.player

import dr.cards.gin.straight.AnalyzeHand._
import dr.cards.model.Player
import dr.cards.gin.straight.Run
import dr.cards.gin.straight.Pair
import dr.cards.gin.straight.Seq
import dr.cards.gin.straight.Set
import dr.cards.gin.straight.StraightGin
import dr.cards.gin.straight.SetFinder
import dr.cards.gin.straight.RunFinder
import dr.cards.gin.straight.PairFinder
import dr.cards.gin.straight.SeqFinder

class B2Player( game : StraightGin, player : Player ) extends ComputerPlayer( game, player ) {

  override def name = "Beginner 2"
  override def description = "If you can't beat this guy..."

  override def choose() = {

    val hand = game.currentHand(player)

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
    val sets = SetFinder.find(cards.toList)
    val runs = RunFinder.find(cards.toList)
    val left = remainder( cards.toList, ( runs.toList ++ sets.toList ) )

    // gin should have already been detected but...
    if( !left.isEmpty ) {

      // try not to discard a pair and a seq
      val pairs = PairFinder.find(left)
      val afterPairs = remainder( left, pairs.toList )
      val seqs = SeqFinder.find( afterPairs )
      val afterSeq = remainder( afterPairs, ( pairs.toList ++ seqs ) )

      val pos = if( !afterSeq.isEmpty ){
        // we have cards left that is not in a pair and seq
        hand.cards.indexOf( getRandom( afterSeq.toList ) )
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

}