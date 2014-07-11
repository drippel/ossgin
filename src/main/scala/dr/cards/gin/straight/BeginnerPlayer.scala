package dr.cards.gin.straight

import AnalyzeHand._
import dr.cards.model.Player

class BeginnerPlayer( game : StraightGin, player : Player ) extends ComputerPlayer( game, player ) {

  override def name = "Nuuwb"
  override def description = "If you can't beat this guy..."

  override def choose() = {

    val hand = game.currentHand(player)

    val card = game.currentDiscard

    // does the discard make or complete a set?
    if( makesSet( hand.cards, card ) || completesSet( hand.cards, card ) ){
       // should a beginner miss a play periodically?
      take()
    }
    else {
      stock()
    }
  }


  override def discard() = {

    val hand =  game.currentHand( player )
    val cards = hand.cards.clone

    //
    val sets = Set.find(cards)
    val left = remainder( cards, sets.toList )

    // gin should have already been detected but...
    if( !left.isEmpty ) {

      // dont discard a pair
      val pairs = Pair.find(left)
      val afterPairs = remainder( left, pairs.toList )
      val pos = if( !afterPairs.isEmpty ){
        val i = random.nextInt(afterPairs.size)
        hand.cards.indexOf(afterPairs(i))
      }
      else {
        val i = random.nextInt(left.size)
        hand.cards.indexOf(left(i))
      }
      discard( pos )
    }
    else {
      // gin?

    }

  }
}