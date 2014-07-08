package dr.cards.gin.straight

import org.junit.Test
import scala.collection.mutable.ListBuffer
import dr.cards.model.Card
import org.junit.Assert._

class SetTest {

  @Test
  def test_complete_set_1() = {

    val hand = ListBuffer[Card]()

    hand += Card.stringToCard( "S2" )
    hand += Card.stringToCard( "D2" )

    hand += Card.stringToCard( "S4" )
    hand += Card.stringToCard( "D4" )

    hand += Card.stringToCard( "C7" )
    hand += Card.stringToCard( "C8" )
    hand += Card.stringToCard( "C9" )

    hand += Card.stringToCard( "HT" )
    hand += Card.stringToCard( "HJ" )
    hand += Card.stringToCard( "HQ" )

    val card = Card.stringToCard( "H2" )

    assertTrue( AnalyzeHand.makesSet(hand, card) )
  }

  @Test
  def test_complete_set_2() = {

    val hand = ListBuffer[Card]()

    hand += Card.stringToCard( "S2" )
    hand += Card.stringToCard( "D2" )

    hand += Card.stringToCard( "S4" )
    hand += Card.stringToCard( "D4" )

    hand += Card.stringToCard( "C7" )
    hand += Card.stringToCard( "C8" )
    hand += Card.stringToCard( "C9" )

    hand += Card.stringToCard( "HT" )
    hand += Card.stringToCard( "HJ" )
    hand += Card.stringToCard( "HQ" )

    val card = Card.stringToCard( "DK" )

    assertFalse( AnalyzeHand.makesSet(hand, card) )
  }
}