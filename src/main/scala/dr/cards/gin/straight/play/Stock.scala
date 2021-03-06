package dr.cards.gin.straight.play
import dr.cards.model.Player
import dr.cards.gin.straight.StraightGin

class Stock( game : StraightGin, player : Player ) extends GinPlay( game, player ) {

  override def execute() = {

    val nextState = setNextState()

    val player = nextState.player

    nextState.card = nextState.stock.remove(0)

    nextState.hands(player).cards += nextState.card

  }
}