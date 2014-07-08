package dr.cards.gin.straight

import dr.cards.model.Hand
import scala.collection.mutable.ListBuffer
import dr.cards.model.Card
import dr.cards.model.CardContainer

object AnalyzeHand {

  // to make this easy lets first group by rank

  def detectGin( hand : Hand ) : Boolean = {

    runsThenSets(hand) || setsThenRuns(hand)

  }

  def runsThenSets( hand : Hand ) : Boolean = {
    detectGin( hand, Run.find, Set.find )
  }

  def setsThenRuns( hand : Hand ) : Boolean = {
    detectGin( hand, Set.find, Run.find )
  }

  def detectGin[T <: Meld]( hand : Hand, f1 : (ListBuffer[Card]) => ListBuffer[T] , f2 :  (ListBuffer[Card]) => ListBuffer[T] ) : Boolean = {

    val sets = f1( hand.cards.clone)

    val runs = f2( remainder( hand.cards.clone, sets.toList ) )
    val combined = runs.toList ++ sets.toList
    val runsRemainder = remainder( hand.cards.clone, combined )

    runsRemainder.isEmpty || runsRemainder.size == 1

  }

  def remainder( hand : ListBuffer[Card], melds : List[Meld] ) : ListBuffer[Card] = {
    val flat = CardContainer.flatten(melds)
    hand.diff( flat )
  }

  def makesSet( hand : ListBuffer[Card], card : Card ) : Boolean = {
    improvesSet( hand, card, 2 )
  }

  def completesSet( hand : ListBuffer[Card], card : Card ) : Boolean = {
    improvesSet( hand, card, 3 )
  }

  def improvesSet( hand : ListBuffer[Card], card : Card, target : Int ) : Boolean = {
    val set = hand.filter( (c) => { c.rank == card.rank } )
    set.size == target
  }
}