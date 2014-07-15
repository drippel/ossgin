package dr.cards.gin.straight.player

import dr.cards.model.Player
import dr.cards.gin.straight.StraightGin
import scala.util.Random
import dr.cards.gin.straight.SetFinder
import dr.cards.gin.straight.AnalyzeHand._
import dr.cards.gin.straight.PairFinder
import dr.cards.gin.straight.RunFinder
import dr.cards.gin.straight.SeqFinder
import dr.cards.model.Card
import scala.collection.mutable.HashMap
import dr.cards.gin.straight.TriangleFinder

trait DiscardStrategy {

  val random = new Random()

  def discard( game : StraightGin, player : Player ) : Int

  def getRandom( cards : List[Card] ) : Card = {
    val i = random.nextInt(cards.size)
    cards(i)
  }
}

object RandomDiscardStrategy extends DiscardStrategy {

  def discard( game : StraightGin, player : Player ) : Int = { random.nextInt(11) }

}

object KeepPairsDiscardStrategy extends DiscardStrategy {

  def discard( game : StraightGin, player : Player ) : Int = {

    val hand =  game.currentHand( player )
    val cards = hand.cards.clone

    //
    val sets = SetFinder.find(cards.toList)
    val left = remainder( cards.toList, sets.toList )

      // dont discard a pair
      val pairs = PairFinder.find(left)
      val afterPairs = remainder( left, pairs.toList )
      if( !afterPairs.isEmpty ){
        val i = random.nextInt(afterPairs.size)
        hand.cards.indexOf(afterPairs(i))
      }
      else {
        val i = random.nextInt(left.size)
        hand.cards.indexOf(left(i))
      }
  }
}

object KeepPairsAndSeqsDiscardStrategy extends DiscardStrategy {

  def discard( game : StraightGin, player : Player ) : Int = {

    val hand = game.currentHand(player)
    val cards = hand.cards.clone

    // find all the sets
    val sets = SetFinder.find(cards.toList)
    val runs = RunFinder.find(cards.toList)
    val left = remainder( cards.toList, ( runs.toList ++ sets.toList ) )


      // try not to discard a pair and a seq
      val pairs = PairFinder.find(left)
      val afterPairs = remainder( left, pairs.toList )
      val seqs = SeqFinder.find( afterPairs )
      val afterSeq = remainder( afterPairs, ( pairs.toList ++ seqs ) )

      val pos = if( !afterSeq.isEmpty ){
        // we have cards left that is not in a pair and seq
        hand.cards.indexOf( getRandom( afterSeq.toList ) )
      }
      else {
        // all the remaining cards are in seqs or pairs
        if( !seqs.isEmpty ){

          // discard something from seqs first
          val flat = seqs.flatten( (s) => { s.cards })
          val i = random.nextInt(flat.size)
          hand.cards.indexOf(flat(i))

        }
        else {
          val flat = pairs.flatten( (s) => { s.cards })
          val i = random.nextInt(flat.size)
          hand.cards.indexOf(flat(i))
        }
      }

      pos

  }

}

object KeepPairsAndSeqsAndCompareDiscardStrategy extends DiscardStrategy {

  def discard( game : StraightGin, player : Player ) : Int = {

    val hand = game.currentHand(player)
    val cards = hand.cards.clone

    // find all the sets
    val sets = SetFinder.find(cards.toList)
    remainder( cards.toList, sets.toList )

    val runs = RunFinder.find(cards.toList)
    val left = remainder( cards.toList, ( runs.toList ++ sets.toList ) )

    // gin should have already been detected but...

      // try not to discard a pair and a seq
      val pairs = PairFinder.find(left.toList)
      val afterPairs = remainder( left, pairs )
      val seqs = SeqFinder.find( afterPairs )
      val afterSeq = remainder( afterPairs, ( pairs.toList ++ seqs ) )

      val pos = if( !afterSeq.isEmpty ){

        // dont discard something that is close to the opponents takes
        val afterCompare = compareToTakes( game, player, afterSeq )
        if( afterCompare.isEmpty ) {
          hand.cards.indexOf( getRandom( afterCompare.toList ) )
        }
        else {
          // we have cards left that is not in a pair and seq
          hand.cards.indexOf( getRandom( afterSeq.toList ) )
        }
      }
      else {
        // all the remaining cards are in seqs or pairs
        if( !seqs.isEmpty ){

          // discard something from seqs first
          val flat = seqs.flatten( (s) => { s.cards })
          val i = random.nextInt(flat.size)
          hand.cards.indexOf(flat(i))

        }
        else {
          val flat = pairs.flatten( (s) => { s.cards })
          val i = random.nextInt(flat.size)
          hand.cards.indexOf(flat(i))
        }
      }

      pos
  }


  def compareToTakes( game : StraightGin, player : Player, cards : List[Card] ) : List[Card] = {

    val opponentTakes = game.opponentTakes(player)
    val rememberedTakes = opponentTakes.take(2)

    // if any cards are of the same rank as a card in rememberedTakes
    // remove from the list

    for( c <- cards ;
         if( !rememberedTakes.exists( (r) => { c.rank == r.rank } ) )
       ) yield c

  }

}

object NinjaDiscardStrategy extends DiscardStrategy {

  def discard( game : StraightGin, player : Player ) : Int = {

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

      game.currentHand(player).cards.indexOf( card )

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