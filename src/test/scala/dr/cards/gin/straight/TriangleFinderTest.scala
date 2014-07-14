package dr.cards.gin.straight

import org.junit.Test
import org.junit.Assert._
import dr.cards.model.Card.toCard
import scala.collection.mutable.ListBuffer
import dr.cards.model.Card

class TriangleFinderTest {

  @Test
  def find_triangle_test() = {

    val cards = List[Card]( "D4", "D5", "S4", "H7", "SJ", "CJ", "CT", "DK", "HA", "DA", "SA" )

    val runs = TriangleFinder.find(cards)

    assertTrue( !runs.isEmpty )
    assertTrue( runs.size == 2 )
  }

  @Test
  def find_triangle_test_2() = {

    val cards = List[Card]( "D4", "D5", "S5", "H7", "S8", "CJ", "CQ", "DK", "HA", "DA", "SA" )

    val runs = TriangleFinder.find(cards)

    assertTrue( !runs.isEmpty )
    assertTrue( runs.size == 1 )
  }
}