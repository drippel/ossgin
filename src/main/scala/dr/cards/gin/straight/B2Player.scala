package dr.cards.gin.straight

import AnalyzeHand._
import dr.cards.model.Card
import dr.cards.model.Player

class B2Player( game : StraightGin, player : Player ) extends ComputerPlayer( game, player ) {

  override def name = "Beginner 2"
  override def description = "If you can't beat this guy..."

  override def choose() = {

    val hand = GinPlay.otherHand(game).clone

    // stock or take?

    // lets look at the discard pile
    val state = game.gameState.asInstanceOf[GinState]

    val card = state.discards.top

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

    val hand = GinPlay.currentHand(game)
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