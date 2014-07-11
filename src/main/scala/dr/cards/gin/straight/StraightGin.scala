package dr.cards.gin.straight

import dr.cards.model.Game
import dr.cards.model.Deck
import dr.cards.model.Player
import dr.cards.model.Card

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


}