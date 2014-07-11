package dr.cards.gin.straight

import scala.collection.mutable.ListBuffer
import dr.cards.model.Card
import dr.cards.model.Player
import dr.cards.model.Play
import dr.cards.model.State
import scala.collection.mutable.HashMap
import dr.cards.model.Hand
import scala.collection.mutable.Stack

class GinState extends State {

  var player : Player = null
  val hands = HashMap[Player,Hand]()
  var discards = Stack[Card]()
  val stock = ListBuffer[Card]()
  var card : Card = null

  override def clone() = {

    val newState = new GinState()

    newState.player = player
    for( ph <- hands ){
      newState.hands += (ph._1 -> ph._2.clone )
    }

    newState.discards = discards.clone
    newState.stock ++= stock
    newState
  }

}