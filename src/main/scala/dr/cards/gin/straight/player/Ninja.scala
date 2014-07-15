package dr.cards.gin.straight.player

import dr.cards.gin.straight.AnalyzeHand._
import dr.cards.model.Player
import dr.cards.gin.straight.StraightGin
import dr.cards.gin.straight.Run
import dr.cards.gin.straight.Set
import dr.cards.gin.straight.RunFinder
import dr.cards.gin.straight.SetFinder
import dr.cards.model.Card
import scala.collection.mutable.HashMap
import dr.cards.gin.straight.PairFinder
import dr.cards.gin.straight.SeqFinder
import dr.cards.gin.straight.TriangleFinder

class Ninja( game : StraightGin, player : Player ) extends ComputerPlayer( game, player ) {

  override def name = "Ninja"
  override def description = "Ninja"

  var chooseStrategy :  ( StraightGin, Player ) => Int = NinjaDrawStrategy.draw
  var discardStrategy :  ( StraightGin, Player ) => Int = NinjaDiscardStrategy.discard

}