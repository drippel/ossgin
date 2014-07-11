package dr.cards.gin.straight

import java.util.Random
import dr.cards.model.Card

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
class ComputerPlayer {

  def name() : String = ""
  def description() : String = ""

  // random will come into play eventually for all skill levels
  val random = new Random()

  def evaluate() = {}

  def play( game : StraightGin ) = {
    choose(game)
    discard(game)
  }
  def choose( game : StraightGin ) = {}
  def discard( game : StraightGin ) = {}

  def getRandom( cards : List[Card] ) : Card = {
    val i = random.nextInt(cards.size)
    cards(i)
  }
}


object ComputerPlayer {

  def computerPlayer( level : Int ) : Option[ComputerPlayer] = {

    level match {
      case 0 => { Some(randomPlayer()) }
      case 1 => { Some(beginnerPlayer()) }
      case 2 => { Some(b2Player()) }
      case 3 => { Some(b3Player()) }
      case _ => {None}
    }

  }

  def randomPlayer() = new RandomPlayer()
  def beginnerPlayer() = new BeginnerPlayer()
  def b2Player() = new B2Player()
  def b3Player() = new B3Player()
}

