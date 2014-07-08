package dr.cards.model

import scala.collection.mutable.ListBuffer

class Hand extends CardContainer {

 override def clone() = {
    val h = new Hand()
    h.cards ++= cards
    h
  }
}