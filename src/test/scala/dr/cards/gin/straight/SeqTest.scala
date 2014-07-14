package dr.cards.gin.straight

import org.junit.Test
import org.junit.Assert._
import dr.cards.model.Hand
import dr.cards.model.Card

class SeqTest {

  @Test
  def test_find_seq() = {

    val h = new Hand();
    h.cards += Card.stringToCard( "S2" )
    h.cards += Card.stringToCard( "D2" )
    h.cards += Card.stringToCard( "C2" )
    h.cards += Card.stringToCard( "H2" )

    h.cards += Card.stringToCard( "S3" )
    h.cards += Card.stringToCard( "D3" )
    h.cards += Card.stringToCard( "C3" )

    h.cards += Card.stringToCard( "HA" )

    h.cards += Card.stringToCard( "S8" )
    h.cards += Card.stringToCard( "S9" )

    val card = Card.stringToCard( "ST" )

    val deadwood = AnalyzeHand.deadwoodRunsFirst(h.cards)

    assertFalse( deadwood.isEmpty )

    val seqs = SeqFinder.find( deadwood )

    assertFalse( seqs.isEmpty )

  }

}