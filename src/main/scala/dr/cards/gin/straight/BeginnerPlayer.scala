package dr.cards.gin.straight

import AnalyzeHand._

class BeginnerPlayer extends ComputerPlayer {

  override def name = "Nuuwb"
  override def description = "If you can't beat this guy..."

  override def choose( game : StraightGin ) = {

    val hand = GinPlay.otherHand(game).clone

    // stock or take?

    // lets look at the discard pile
    val state = game.gameState.asInstanceOf[GinState]

    val card = state.discards.top

    // does this card make or complete a set?
    if( makesSet( hand.cards, card ) || completesSet( hand.cards, card ) ){
       // should a beginner miss a play periodically?
      val t =  new Take(game)
      t.execute
    }
    else {
      val s = new Stock( game )
      s.execute()
    }
  }


  override def discard(game : StraightGin ) = {

    val hand = GinPlay.currentHand(game)
    val cards = hand.cards.clone

    //
    val sets = Set.find(cards)
    val left = remainder( cards, sets.toList )

    // gin should have already been detected but...
    if( !left.isEmpty ) {

      // dont discard a pair
      val pairs = Pair.find(left)
      val afterPairs = remainder( left, pairs.toList )
      val pos = if( !afterPairs.isEmpty ){
        val i = random.nextInt(afterPairs.size)
        hand.cards.indexOf(afterPairs(i))
      }
      else {
        val i = random.nextInt(left.size)
        hand.cards.indexOf(left(i))
      }
      val d = new Discard(game,pos)
      d.execute
    }
    else {
      // gin?

    }

  }
}