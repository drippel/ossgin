package dr.cards.gin.straight

import dr.cards.model.Game
import dr.cards.model.Deck
import dr.cards.model.Player
import dr.cards.model.Card
import dr.cards.model.Hand
import dr.cards.gin.straight.play.Take
import dr.cards.gin.straight.play.Discard
import dr.cards.gin.straight.play.GinPlay

class StraightGin extends Game {

  var dealer = 0

  def newDeck() = {
    Deck.standardDeck
  }

  def shuffle( deck : Deck ) = {
    Deck.shuffle( deck, 100 )
  }

  def gameState() = {
    currentState().asInstanceOf[GinState]
  }

  def isRoundOver() : Boolean = {
    val state = gameState()
    state.stock.size <= 2
  }

  def hasGin( player : Player ) : Boolean = {

    AnalyzeHand.detectGin( gameState().hands(player) )
  }

  def getLastPlay() : GinPlay = {
    val state = currentState.asInstanceOf[GinState]
    state.play.asInstanceOf[GinPlay]
  }

  def opponent( player : Player ) : Player = {
    players.filter( (p) => { !player.equals(p) } ).head
  }

  def allStates() : List[GinState] = {
    val allStates = currentRound().states
    allStates.map( (s) => { s.asInstanceOf[GinState] } )
  }

  def playerStates( player : Player ) : List[GinState] = {
    allStates.filter( (s) => { s.player.equals( player ) } )
  }

  def opponentStates( player : Player ) : List[GinState] = {
    val opp = opponent(player)
    allStates.filter( (s) => { s.player.equals( opp ) } )
  }

  def opponentTakes( player : Player ) : List[Card] = {

    val oppTakes = opponentStates(player).filter( (s) => {
      s.play.isInstanceOf[Take]
    })

    oppTakes.map( (s) => { s.card })

  }

  def opponentDiscards( player : Player ) : List[Card] = {

    val oppTakes = opponentStates(player).filter( (s) => {
      s.play.isInstanceOf[Discard]
    })

    oppTakes.map( (s) => { s.card })

  }

  def currentDiscard() : Card = {
    gameState().discards.top
  }

  def currentHand( player : Player ) : Hand = {
    gameState().hands(player)
  }


}