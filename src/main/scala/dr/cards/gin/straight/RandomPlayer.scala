package dr.cards.gin.straight

import java.util.Random

class RandomPlayer extends ComputerPlayer {

  override def name = "Random"
  override def description = "Well.. random"

  override def choose( game : StraightGin ) = {

    // stock or take?
    val p = random.nextInt(2)
    p match {
      case 0 => {
        // stock
        val d = new Stock(game)
        d.execute
      }
      case 1 => {
        // take
        val t = new Take(game)
        t.execute
      }
      case _ => {
        // wtf
      }
    }

  }


  override def discard(game : StraightGin ) = {

    val i = random.nextInt(11)
    val d = new Discard(game,i)
    d.execute
  }

}