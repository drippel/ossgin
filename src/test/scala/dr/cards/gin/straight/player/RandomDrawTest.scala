package dr.cards.gin.straight.player

import org.junit.Test
import org.junit.Assert._
import dr.cards.gin.straight.StraightGin
import dr.cards.model.Player
import dr.cards.gin.straight.play.Setup
import dr.cards.gin.straight.play.Deal

class RandomDrawTest {

  @Test
  def test_random_draw() = {

    val me = new Player("me")
    val you = new Player("you")

    val game = new StraightGin()

    val setup = new Setup( game, me, you )
    setup.execute

    val deal = new Deal( game, me )
    deal.execute()

    for( i <- 0 to 50 ){
      val draw = RandomDrawStrategy.draw(game, you )
      assertTrue( draw == 0 || draw == 1 )
    }

  }

}