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

  var chooseStrategy :  ( StraightGin, Player ) => Int = SetsDrawStrategy.draw
  var discardStrategy :  ( StraightGin, Player ) => Int = KeepPairsDiscardStrategy.discard

}