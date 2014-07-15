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

  var chooseStrategy :  ( StraightGin, Player ) => Int = SetsThenRunsDrawStrategy.draw
  var discardStrategy :  ( StraightGin, Player ) => Int = KeepPairsAndSeqsDiscardStrategy.discard

}