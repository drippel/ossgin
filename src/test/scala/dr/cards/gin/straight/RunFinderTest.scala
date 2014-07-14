package dr.cards.gin.straight

import org.junit.Test
import org.junit.Assert._
import scala.collection.mutable.ListBuffer
import dr.cards.model.Card

class RunFinderTest {

  @Test
  def test_find_single_run() = {

    val cards = new ListBuffer[Card]()
    cards += Card.stringToCard( "D4" )
    cards += Card.stringToCard( "D5" )
    cards += Card.stringToCard( "D6" )

    cards += Card.stringToCard( "H7" )
    cards += Card.stringToCard( "S3" )
    cards += Card.stringToCard( "CJ" )

    cards += Card.stringToCard( "CT" )
    cards += Card.stringToCard( "DK" )
    cards += Card.stringToCard( "HA" )

    cards += Card.stringToCard( "DA" )
    cards += Card.stringToCard( "S2" )

    val runs = RunFinder.find(cards.toList)

    assertTrue( !runs.isEmpty )
    assertTrue( runs.size == 1 )

  }

  @Test
  def test_find_multi_runs() = {

    val cards = new ListBuffer[Card]()
    cards += Card.stringToCard( "D4" )
    cards += Card.stringToCard( "D5" )
    cards += Card.stringToCard( "D6" )

    cards += Card.stringToCard( "H7" )
    cards += Card.stringToCard( "S3" )

    cards += Card.stringToCard( "CJ" )
    cards += Card.stringToCard( "CT" )
    cards += Card.stringToCard( "CQ" )

    cards += Card.stringToCard( "HA" )

    cards += Card.stringToCard( "DA" )
    cards += Card.stringToCard( "S2" )

    val runs = RunFinder.find(cards.toList)

    assertTrue( !runs.isEmpty )
    assertTrue( runs.size == 2 )

  }

  @Test
  def test_find_no_runs() = {

    val cards = new ListBuffer[Card]()
    cards += Card.stringToCard( "DA" )
    cards += Card.stringToCard( "D2" )

    cards += Card.stringToCard( "D5" )
    cards += Card.stringToCard( "D6" )

    cards += Card.stringToCard( "H7" )
    cards += Card.stringToCard( "S3" )

    cards += Card.stringToCard( "CJ" )
    cards += Card.stringToCard( "CT" )
    cards += Card.stringToCard( "CK" )

    cards += Card.stringToCard( "HA" )

    cards += Card.stringToCard( "S2" )

    val runs = RunFinder.find(cards.toList)

    assertTrue( runs.isEmpty )

  }
}