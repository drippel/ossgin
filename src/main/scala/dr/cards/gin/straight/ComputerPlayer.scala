package dr.cards.gin.straight

import java.util.Random
import dr.cards.model.Card
import dr.cards.model.Player

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
class ComputerPlayer( val game : StraightGin, val player : Player ) {

  def name() : String = ""
  def description() : String = ""

  // random will come into play eventually for all skill levels
  val random = new Random()

  def evaluate() = {}

  def play() = {
    choose()
    discard()
  }
  def choose() = {}
  def discard() = {}

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
      val t = new Take( game )
      t.execute
  }

  def stock() = {
      val t = new Stock( game )
      t.execute
  }

  def discard( i : Int ) = {
    val d = new Discard(game,i)
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

