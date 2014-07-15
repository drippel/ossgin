package dr.cards.gin.straight.player

import dr.cards.model.Player
import dr.cards.gin.straight.StraightGin
import dr.cards.gin.straight.GinState

class RandomPlayer( game : StraightGin, player : Player ) extends ComputerPlayer( game, player ) {

  override def name = "Random"
  override def description = "Well.. random"

  var chooseStrategy :  ( StraightGin, Player ) => Int = RandomDrawStrategy.draw
  var discardStrategy :  ( StraightGin, Player ) => Int = RandomDiscardStrategy.discard

}