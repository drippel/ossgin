package dr.cards.gin.straight

import dr.cards.model.Game
import dr.cards.model.Deck
import dr.cards.model.Player

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

}