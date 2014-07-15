package dr.cards.gin.straight.player

import java.util.Random
import dr.cards.model.Card
import dr.cards.gin.straight.play.Discard
import dr.cards.gin.straight.GameStage
import dr.cards.gin.straight.play.Stock
import dr.cards.gin.straight.StraightGin
import dr.cards.gin.straight.play.Take
import dr.cards.model.Player
import dr.cards.gin.straight.Early
import dr.cards.gin.straight.Middle
import dr.cards.gin.straight.Late

/*
 * Should players make mistakes or miss plays at random?
 *
 * Levels of player
 * Random
 *   0  picks from stock/discard at random, discards at random
 * Beginner
 *   1  beginner tries for sets, no memory
 *   2  prefer sets, may make a run
 *   3  remembers last X takes, discard to not match rank
 * Intermediate
 *   4  holds cards to block opponent, uses triangles
 *   5  prefer runs to sets, improve discard criteria
 *   6  prefer take from stock vs take from discard
 *   7  middle cards are valuable for runs, 6,7,8
 *      end cards less so
 * Advanced
 *   8  memory improvements, break sets to make runs, etc., only take from discard to make meld
 *   9  predict opponents hand, odds of cards in deck is extend/make a meld, memory limit improved
 * Expert
 *   10 perfect memory on takes/discards, no mistakes
 */
abstract class ComputerPlayer( val game : StraightGin, val player : Player ) {

  def name() : String = ""
  def description() : String = ""

  // random will come into play eventually for all skill levels
  val random = new Random()

  def play() = {
    choose()
    discard()
  }

  var chooseStrategy :  ( StraightGin, Player ) => Int
  var discardStrategy :  ( StraightGin, Player ) => Int

  def choose() : Unit = {

    // stock or take?
    chooseStrategy( game, player ) match {
      case 0 => { stock() }
      case 1 => { take() }
    }

  }

  def discard() : Unit = { discard( discardStrategy( game, player ) ) }

  def getRandom( cards : List[Card] ) : Card = {
    val i = random.nextInt(cards.size)
    cards(i)
  }

  def gameStage() : GameStage = {

    val left = game.gameState().stock.size

    if( left > 21 ){ Early() }
    else if( left > 11 ) { Middle() }
    else{ Late() }

  }

  def take() = {
      val t = new Take( game, player )
      t.execute
  }

  def stock() = {
      val t = new Stock( game, player )
      t.execute
  }

  def discard( i : Int ) = {
    val d = new Discard(game,player,i)
    d.execute
  }
}


object ComputerPlayer {

  def computerPlayer( level : Int, game : StraightGin, player : Player ) : Option[ComputerPlayer] = {

    level match {
      case 0 => { Some( new RandomPlayer( game, player )) }
      case 1 => { Some( new BeginnerPlayer( game, player )) }
      case 2 => { Some( new B2Player( game, player )) }
      case 3 => { Some( new B3Player( game, player )) }
      case 10 => { Some( new Ninja( game, player )) }
      case _ => {None}
    }

  }

}

