package dr.cards.gin.straight.player

import dr.cards.gin.straight.AnalyzeHand._
import dr.cards.model.Player
import dr.cards.gin.straight.StraightGin
import dr.cards.gin.straight.Run
import dr.cards.gin.straight.Set

class Ninja( game : StraightGin, player : Player ) extends ComputerPlayer( game, player ) {

  override def name = "Ninja"
  override def description = "Ninja"

  override def choose() = {

    // the last play was by the opponent
    val hand = game.currentHand(player)

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
      take()
    }
    else {
      if( deadwoodRunsFirst( hand.cards ).size <= 3 ){
        take()
      }
      else {
        stock()
      }
    }
  }

  override def discard() = {

    val takes = game.opponentTakes(player)
    val discards = game.opponentDiscards(player)

    // cards in the opponents hand
    val inHand = takes.diff(discards)

    // all discards
    val allDiscards = game.gameState.discards.clone

    //
    val cards = game.currentHand(player).cards.clone

    // TODO: this will not break off cards from a long run to make a set
    val runs = Run.find( cards )
    val sets = Set.find( remainder( cards, runs.toList ) )

    val left = remainder( cards, ( runs.toList ++ sets.toList ) )

    //
    if( !left.isEmpty ){

      //

    }
    else {
      // gin?
    }



  }


}