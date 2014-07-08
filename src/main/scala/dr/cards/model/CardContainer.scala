package dr.cards.model

import scala.collection.mutable.ListBuffer

class CardContainer {

  val cards = ListBuffer[Card]();


  override def toString() = {
    var s = "[ {"

    for( c <- cards ){
      s += c + ","
    }

    s += " } ]"

    s
  }

  def copy() = {
    cards.clone
  }

}

object CardContainer {

  def flatten[T <: CardContainer]( containers :  List[T]) : List[Card] = {
    containers.map( (s) => { s.cards } ).flatten
  }



}