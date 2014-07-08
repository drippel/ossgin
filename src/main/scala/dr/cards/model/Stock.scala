package dr.cards.model

class Stock extends CardContainer {
 override def clone() = {
    val d = new Stock()
    d.cards ++= cards
    d
  }
}