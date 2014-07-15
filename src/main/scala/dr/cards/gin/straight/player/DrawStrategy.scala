package dr.cards.gin.straight.player

import dr.cards.model.Player
import dr.cards.gin.straight.GinState
import java.util.Random
import dr.cards.gin.straight.StraightGin
import dr.cards.gin.straight.AnalyzeHand._

trait DrawStrategy {

  val random = new Random()

  def draw( game : StraightGin, player : Player ) : Int

}

object RandomDrawStrategy extends DrawStrategy {

  def draw( game : StraightGin, player : Player ) : Int = { random.nextInt(2) }

}

object SetsDrawStrategy extends DrawStrategy {

  def draw( game : StraightGin, player : Player ) : Int = {

    val hand = game.currentHand(player)

    val card = game.currentDiscard

    // does the discard make or complete a set?
    if( makesSet( hand.cards, card ) || completesSet( hand.cards, card ) ){ 1 }
    else { 0 }
  }
}

object SetsThenRunsDrawStrategy extends DrawStrategy {

  def draw( game : StraightGin, player : Player ) : Int = {

    val hand = game.currentHand(player)
    val card = game.currentDiscard

    if( makesSet( hand.cards, card ) || completesSet( hand.cards, card ) ){ 1 }
    else if( makesRun( hand.cards, card ) || improvesRun( hand.cards, card ) ){ 1 }
    else { 0 }
  }
}

object NinjaDrawStrategy extends DrawStrategy {

  def draw( game : StraightGin, player : Player ) : Int = {


    // the last play was by the opponent
    val hand = game.currentHand(player).clone

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

    if( detectGin( hand ) ) { 1 }
    else {
      if( deadwoodRunsFirst( hand.cards ).size <= 3 ){ 1 }
      else { 0 }
    }

  }
}