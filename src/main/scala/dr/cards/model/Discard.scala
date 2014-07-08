package dr.cards.model


class Discard extends CardContainer {
 override def clone() = {
    val d = new Discard()
    d.cards ++= cards
    d
  }
}