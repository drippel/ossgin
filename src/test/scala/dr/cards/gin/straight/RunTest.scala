package dr.cards.gin.straight

import org.junit.Test
import org.junit.Assert._
import scala.collection.mutable.ListBuffer
import dr.cards.model.Card

class RunTest {

  @Test
  def test_extends_run_start() {

    val cards = new ListBuffer[Card]()
    cards += Card.stringToCard( "D4" )
    cards += Card.stringToCard( "D5" )
    cards += Card.stringToCard( "D6" )
    val run = new Run(cards)

    val card = Card.stringToCard("D3")

    assertTrue( run.improves(card))
  }

  @Test
  def test_extends_run_end() {

    val cards = new ListBuffer[Card]()
    cards += Card.stringToCard( "D4" )
    cards += Card.stringToCard( "D5" )
    cards += Card.stringToCard( "D6" )
    val run = new Run(cards)

    val card = Card.stringToCard("D7")

    assertTrue( run.improves(card))
  }

  @Test
  def test_extends_run_nope() {

    val cards = new ListBuffer[Card]()
    cards += Card.stringToCard( "D4" )
    cards += Card.stringToCard( "D5" )
    cards += Card.stringToCard( "D6" )
    val run = new Run(cards)

    val card = Card.stringToCard("D8")

    assertFalse( run.improves(card))
  }

  @Test
  def test_extends_run_wrong_suit() {

    val cards = new ListBuffer[Card]()
    cards += Card.stringToCard( "D4" )
    cards += Card.stringToCard( "D5" )
    cards += Card.stringToCard( "D6" )
    val run = new Run(cards)

    val card = Card.stringToCard("C7")

    assertFalse( run.improves(card))
  }
}