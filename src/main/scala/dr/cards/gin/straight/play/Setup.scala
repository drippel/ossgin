package dr.cards.gin.straight.play

import dr.cards.model.Play
import dr.cards.model.Player
import dr.cards.model.Match
import dr.cards.model.Round
import dr.cards.model.Hand
import dr.cards.gin.straight.GinState
import dr.cards.gin.straight.StraightGin

class Setup( game : StraightGin, p1 : Player, p2 : Player ) extends Play {

  override def execute() = {

    game.addPlayer( p1 )
    game.addPlayer( p2 )

    val match1 = new Match()
    game.matches += match1

    val round = new Round()
    match1.rounds += round

    round.dealer = p1

    val state = new GinState()
    state.play = this
    state.player = p1
    state.hands += ( p1 -> new Hand() )
    state.hands += ( p2 -> new Hand() )

    round.beginState = state
  }

}