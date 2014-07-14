package dr.cards.gin.straight.player

import dr.cards.gin.straight.AnalyzeHand._
import dr.cards.gin.straight.Pair
import dr.cards.gin.straight.Set
import dr.cards.gin.straight.StraightGin
import dr.cards.model.Player
import dr.cards.gin.straight.SetFinder
import dr.cards.gin.straight.PairFinder

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
    val sets = SetFinder.find(cards.toList)
    val left = remainder( cards.toList, sets.toList )

    // gin should have already been detected but...
    if( !left.isEmpty ) {

      // dont discard a pair
      val pairs = PairFinder.find(left)
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