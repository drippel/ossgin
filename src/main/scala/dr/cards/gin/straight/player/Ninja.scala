package dr.cards.gin.straight.player

import dr.cards.gin.straight.AnalyzeHand._
import dr.cards.model.Player
import dr.cards.gin.straight.StraightGin
import dr.cards.gin.straight.Run
import dr.cards.gin.straight.Set
import dr.cards.gin.straight.RunFinder
import dr.cards.gin.straight.SetFinder
import dr.cards.model.Card
import scala.collection.mutable.HashMap
import dr.cards.gin.straight.PairFinder
import dr.cards.gin.straight.SeqFinder
import dr.cards.gin.straight.TriangleFinder

class Ninja( game : StraightGin, player : Player ) extends ComputerPlayer( game, player ) {

  override def name = "Ninja"
  override def description = "Ninja"

  override def choose() = {

    // the last play was by the opponent
    val hand = game.currentHand(player).clone

    val card = game.currentDiscard()

    // lets figure some stuff out
    // the ninja will almost never take from the discards
    // however, if it leads to gin - no brainer - take the card and win
    // or almost gin - in certain stages of the game - he might take the discard
    // say taking the discard leads to dead wood < 2-3 cards
    // maybe some more calculation here
    // if the card does make a set/run - calc what are the chances of extending it again?
    // if we extends a pair to a set but the 4th card is already in discard, should he take?
    // calc deadwood prev to take and compare to deadwood after take?
    // taking and and completing a set might be a no brainer as well
    hand.cards += card

    if( detectGin( hand ) ) {
      take()
    }
    else {
      if( deadwoodRunsFirst( hand.cards ).size <= 3 ){
        take()
      }
      else {
        stock()
      }
    }
  }

  override def discard() = {

    // step 1 collect information

    val takes = game.opponentTakes(player)
    val discards = game.opponentDiscards(player)

    // cards in the opponents hand
    val inHand = takes.diff(discards)

    // all discards
    val allDiscards = game.gameState.discards.clone

    //
    val cards = game.currentHand(player).cards.clone

    // TODO: this will not break off cards from a long run to make a set
    val runs = RunFinder.find( cards.toList )
    val sets = SetFinder.find( remainder( cards.toList, runs.toList ) )

    val deadwood = remainder( cards.toList, ( runs.toList ++ sets.toList ) )

    //
    if( !deadwood.isEmpty ){

      // lets create a map of cards and weights
      val weights = HashMap[Card,Int]()
      for( c <- deadwood ){
        weights.put( c, 0 )
      }

      // if the card is in a pair, seq, or triangle
      val pairs = PairFinder.find(deadwood).flatten( (p) => { p.cards } )
      adjustWeights( weights, pairs, 10 )

      val seq = SeqFinder.find(deadwood).flatten( (p) => { p.cards } )
      adjustWeights( weights, pairs, 15 )

      val tri = TriangleFinder.find(deadwood).flatten( (p) => { p.cards } )
      adjustWeights( weights, pairs, 10 )

      // if the card is near one of the cards in the players hand
      for( d <- deadwood ){
        if( inHand.exists( (s) => { isNear( d, s ) } )){
            val cur = weights(d)
            weights.put(d, ( cur + 20 ) )
        }
      }

      //
      val s = weights.values.toList.sorted.head
      val possibles = weights.filter( (p) => { p._2 == 2 } ).keySet.toList

      val card = getRandom( possibles )

      discard( game.currentHand(player).cards.indexOf( card ) )

    }
    else {
      // gin?
    }

  }

  def adjustWeights( weights : HashMap[Card,Int], cards : List[Card], adj : Int ) = {
    for( c <- cards ){
      val cur = weights(c)
      weights.put(c, ( cur + adj ) )
    }
  }

  def isNear( src : Card, trgt : Card ) : Boolean = {
    if( src.rank == trgt.rank ){
      true
    }
    else if( src.suit == trgt.suit ) {
      val diff = src.rank - trgt.rank
      diff.abs <= 2
    }
    else {
      false
    }
  }


}