package dr.cards.gin.straight

import org.junit.Test
import dr.cards.model.Hand
import dr.cards.model.Card
import org.junit.Assert._

class AnalyzeHandTest {


  @Test
  def test_gin_all_sets() = {

    val h = new Hand();
    h.cards += Card.stringToCard( "S2" )
    h.cards += Card.stringToCard( "D2" )
    h.cards += Card.stringToCard( "C2" )
    h.cards += Card.stringToCard( "H2" )

    h.cards += Card.stringToCard( "S3" )
    h.cards += Card.stringToCard( "D3" )
    h.cards += Card.stringToCard( "C3" )

    h.cards += Card.stringToCard( "S4" )
    h.cards += Card.stringToCard( "D4" )
    h.cards += Card.stringToCard( "H4" )

    h.cards += Card.stringToCard( "S9" )

    assertTrue( AnalyzeHand.detectGin( h ) )

  }

  @Test
  def test_gin_all_runs() = {

    val h = new Hand();
    h.cards += Card.stringToCard( "S2" )
    h.cards += Card.stringToCard( "S3" )
    h.cards += Card.stringToCard( "S4" )
    h.cards += Card.stringToCard( "S5" )

    h.cards += Card.stringToCard( "D6" )
    h.cards += Card.stringToCard( "D7" )
    h.cards += Card.stringToCard( "D8" )

    h.cards += Card.stringToCard( "H9" )
    h.cards += Card.stringToCard( "HT" )
    h.cards += Card.stringToCard( "HJ" )

    h.cards += Card.stringToCard( "C3" )

    assertTrue( AnalyzeHand.detectGin( h ) )

  }

  @Test
  def test_gin_combo() = {

    val h = new Hand();
    h.cards += Card.stringToCard( "S2" )
    h.cards += Card.stringToCard( "S3" )
    h.cards += Card.stringToCard( "S4" )
    h.cards += Card.stringToCard( "S5" )

    h.cards += Card.stringToCard( "D6" )
    h.cards += Card.stringToCard( "C6" )
    h.cards += Card.stringToCard( "H6" )

    h.cards += Card.stringToCard( "H9" )
    h.cards += Card.stringToCard( "HT" )
    h.cards += Card.stringToCard( "HJ" )

    h.cards += Card.stringToCard( "C3" )

    assertTrue( AnalyzeHand.detectGin( h ) )

  }

  @Test
  def test_remaining() = {

    val h = new Hand();
    h.cards += Card.stringToCard( "S2" )
    h.cards += Card.stringToCard( "D2" )
    h.cards += Card.stringToCard( "C2" )
    h.cards += Card.stringToCard( "H2" )

    h.cards += Card.stringToCard( "S3" )
    h.cards += Card.stringToCard( "D3" )
    h.cards += Card.stringToCard( "C3" )

    h.cards += Card.stringToCard( "S4" )
    h.cards += Card.stringToCard( "D4" )
    h.cards += Card.stringToCard( "H4" )

    h.cards += Card.stringToCard( "S9" )

    val sets = Set.find( h.cards.clone )

    val remainder = AnalyzeHand.remainder( h.cards.clone, sets.toList )

    assertTrue( remainder.size == 1 )
    assertTrue( remainder.contains( Card.stringToCard( "S9" ) ) )

  }

}