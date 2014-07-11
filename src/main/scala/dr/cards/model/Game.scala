package dr.cards.model

import scala.collection.mutable.ListBuffer

class Game {

  val players = ListBuffer[Player]()

  val matches = ListBuffer[Match]()

  def addPlayer( p : Player ) = {
    players += p
  }

  def currentState() = {
    matches.last.rounds.last.currentState
  }

  def currentRound() = {
    matches.last.rounds.last
  }

  def inProgress() : Boolean = {
    !matches.isEmpty && !matches.last.rounds.isEmpty && matches.last.rounds.last.beginState != null
  }
}