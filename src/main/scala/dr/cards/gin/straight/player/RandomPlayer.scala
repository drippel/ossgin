package dr.cards.gin.straight.player
import dr.cards.model.Player
import dr.cards.gin.straight.StraightGin

class RandomPlayer( game : StraightGin, player : Player ) extends ComputerPlayer( game, player ) {

  override def name = "Random"
  override def description = "Well.. random"

  override def choose() = {

    // stock or take?
    random.nextInt(2) match {
      case 0 => { stock() }
      case 1 => { take() }
      case _ => {
        // wtf
      }
    }

  }

  override def discard() = { discard( random.nextInt(11) ) }

}