package dr.cards.gin.straight

abstract class GameStage { }

case class VeryEarly extends GameStage
case class Early extends GameStage
case class Middle extends GameStage
case class Late extends GameStage
case class VeryLate extends GameStage