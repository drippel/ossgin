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
    detectGin( hand, RunFinder.find, SetFinder.find )
  }

  def setsThenRuns( hand : Hand ) : Boolean = {
    detectGin( hand, SetFinder.find, RunFinder.find )
  }

  def detectGin[T <: Meld]( hand : Hand, f1 : (List[Card]) => List[T] , f2 :  (List[Card]) => List[T] ) : Boolean = {

    val sets = f1( hand.cards.toList )

    val runs = f2( remainder( hand.cards.toList, sets ) )
    val combined = runs.toList ++ sets.toList
    val runsRemainder = remainder( hand.cards.toList, combined )

    runsRemainder.isEmpty || runsRemainder.size == 1

  }

  def remainder( hand : List[Card], melds : List[Meld] ) : List[Card] = {
    val flat = CardContainer.flatten(melds)
    hand.diff( flat ).toList
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

  def makesRun( hand : ListBuffer[Card], card : Card ) : Boolean = {
    val remainder = deadwoodRunsFirst(hand)
    val seqs = SeqFinder.find(remainder)
    seqs.exists( (s) => { s.improves(card) } )
  }

  def improvesRun( hand : ListBuffer[Card], card : Card ) : Boolean = {
    val runs = RunFinder.find(hand.toList)
    runs.exists( (r) => { r.improves(card) })
  }

  def deadwoodRunsFirst( cards : ListBuffer[Card] ) : List[Card] = {
    deadwood( cards, RunFinder.find, SetFinder.find)
  }

  def deadwoodSetsFirst( cards : ListBuffer[Card] ) : List[Card] = {
    deadwood( cards, SetFinder.find, RunFinder.find)
  }

  def deadwood[T <: Meld]( cards : ListBuffer[Card], f1 : (List[Card]) => List[T] , f2 :  (List[Card]) => List[T] ) : List[Card] = {
    val sets = f1( cards.toList)
    val runs = f2( remainder( cards.toList, sets.toList ) )
    val combined = runs.toList ++ sets.toList
    remainder( cards.toList, combined )
  }
}