package dr.cards.gin.straight

import dr.cards.model.Player
import dr.cards.model.Card

class StraightGinConsole( val game : StraightGin ) {
}

object StraightGinConsole {

  var game : StraightGin = null
  val me = new Player( "Me" )
  val you = new Player( "You" )

  val ginConsole = new StraightGinConsole( new StraightGin() )

  var cheatsOn = false
  var hintsOn = false

  var playerStrategy : ComputerPlayer = ComputerPlayer.randomPlayer()

  var msg : Option[String] = null

  def setMessage( m : String ) = {
    msg = Some( m )
  }

  def getMessage() : String = {
    val m = msg.getOrElse( "" )
    msg = None
    m
  }

  def main( args : Array[String] ) = {
    setMessage( "Select New to start a game" )
    newGameMenu
    gameLoop
  }

  def newGameMenu() = {
    actions = List[Action]( QuitAction(), NewGameAction(), CheatAction(), HintsAction(), PlayerAction() )
  }

  def gameLoop() : Unit = {
    refreshConsole();
    executeAction( Console.readLine() )
    gameLoop()
  }

  def executeAction( cmd : String ) = {

    val c = cmd.charAt( 0 )

    actions.find( ( a ) => { c == a.code } ) match {

      case Some( action ) => {
        action.cmd = cmd
        action.execute
      }
      case _ => {
      }

    }

  }

  abstract class Action( val name : String, val code : Char ) {
    var cmd : String = null
    def execute() : Unit
  }

  case class QuitAction extends Action( "(Q)uit", 'Q' ) {
    override def execute() = {
      System.exit( 0 )
    }
  }

  case class NewGameAction extends Action( "(N)ew", 'N' ) {
    override def execute() = {
      game = new StraightGin()
      val setup = new Setup( game, me, you )
      setup.execute
      val deal = new Deal( game )
      deal.execute()

      takeMenu()
    }
  }

  def takeMenu() = {
    actions = List[Action]( QuitAction(), NewGameAction(), StockAction(), PlayerAction(),
      TakeAction(), CheatAction(), SaveAction(),
      HintsAction(), SortAction(), SwapAction() )
  }

  def discardMenu() = {
    actions = List[Action]( QuitAction(), NewGameAction(), DiscardAction(), PlayerAction(),
      CheatAction(), SaveAction(),
      HintsAction(), SortAction(), SwapAction() )
  }

  case class StockAction extends Action( "(S)tock", 'S' ) {
    override def execute() = {
      val play = new Stock( game )
      play.execute()

      // detect gin
      if ( game.hasGin( you ) ) {
        setMessage( "Winnah winnah chicken dinnah!" )
        newGameMenu()
      } else {
        discardMenu()
      }

    }
  }

  case class TakeAction extends Action( "(T)ake", 'T' ) {
    override def execute() = {

      val play = new Take( game )
      play.execute()

      // detect gin
      if ( game.hasGin( you ) ) {
        setMessage( "Winnah winnah chicken dinnah!" )
        newGameMenu()
      } else {
        discardMenu()
      }

    }
  }

  def discardPrompt() = {
    if ( inProgress() ) {
      val yourHand = gameState().hands( you )
      if ( yourHand.cards.size > 10 ) {
        // print the discard message
        Console.println( vert + " Discard: D1 - D11                                " )
      }
    }
  }

  case class DiscardAction extends Action( "(D)iscard", 'D' ) {
    override def execute() = {
      try {

        Console.println("discard...")

        val i = cmd.tail.toInt
        val d = new Discard( game, i - 1 )
        d.execute

        // detect gin or game over
        if ( game.isRoundOver ) {
          setMessage( "Round is over - no winner today!" )
          newGameMenu()
        } else {

          // computer player
          playerStrategy.choose( game )

          // message for what the player took

          // detect gin
          if ( game.hasGin( me ) ) {
            setMessage( "I win loooosaah!" )
            newGameMenu()
          } else {

            playerStrategy.discard( game )

            // detect gin or game over
            if ( game.isRoundOver ) {
              setMessage( "Round is over - no winner today!" )
              newGameMenu()
            } else {

              //
              setMessage( "Your turn!" )
              actions = List[Action]( QuitAction(), NewGameAction(), StockAction(),
                TakeAction(), CheatAction(), SaveAction(),
                HintsAction(), SortAction(), SwapAction() )
            }
          }

        }

      } catch {
        case _ : Throwable => {
          setMessage( "Did you typo the discard? 1 - 11" )
        }
      }
    }
  }

  case class CheatAction extends Action( "(C)heat", 'C' ) {
    override def execute() = {
      cheatsOn = !cheatsOn
    }
  }

  case class SaveAction extends Action( "Sa(V)e", 'V' ) {
    override def execute() = {
      System.exit( 0 )
    }
  }

  case class HintsAction extends Action( "(H)ints", 'H' ) {
    override def execute() = {
      hintsOn = !hintsOn
    }
  }

  case class UndoAction extends Action( "(U)ndo", 'U' ) {
    override def execute() = {
      System.exit( 0 )
    }
  }

  case class SortAction extends Action( "S(O)rt", 'O' ) {
    override def execute() = {

      val ordering = if ( cmd.length() > 1 ) {
        cmd.charAt( 1 ) match {
          case 'S' => {
            new Card.SuitRankOrdering()
          }
          case _ => {
            new Card.RankOrdering()
          }
        }
      } else {
        new Card.RankOrdering()
      }
      val s = new Sort( game, you, ordering )
      s.execute
    }
  }

  case class SwapAction extends Action( "S(W)ap", 'W' ) {
    override def execute() = {
      val nums = cmd.tail.split( "," )
      if ( nums.size > 1 ) {
        try {
          val a = nums( 0 ).toInt
          val b = nums( 1 ).toInt
          val s = new Swap( game, you, a - 1, b - 1 )
          s.execute
        } catch {
          case _ : Throwable => {}
        }
      }
    }
  }

  case class PlayerAction extends Action( "(P)layer", 'P' ){
    override def execute() = {
      val num = cmd.tail
      if ( num.size > 0 ) {
        try {
          val i = num.toInt
          ComputerPlayer.computerPlayer(i) match {
            case Some(p) => {
              playerStrategy = p
              setMessage("Switched player to " + playerStrategy.name )
            }
            case _ => {
              // do nothing
            }
          }
        } catch {
          case _ : Throwable => {}
        }
      }
    }
  }

  case class SlideAction extends Action( "S(L)ide", 'L' ){
    override def execute() = {
    }
  }

  var actions = List[Action]( QuitAction(), NewGameAction(), StockAction(),
    TakeAction(), DiscardAction(), CheatAction(), SaveAction(),
    HintsAction(), UndoAction() )

  def refreshConsole() = {

    clearScreen()
    line()
    title()
    score()
    blank()
    player1()
    blank()
    stock()
    blank()
    player2()
    blank()
    chart()
    blank()
    message()
    line()
    menu()

  }

  def clearScreen() = {
    Console.print( "\033[2J" )
  }

  def menu() = {
    Console.println( vert + " Actions:                                       " )
    for ( group <- actions.grouped( 3 ) ) {
      Console.print( vert + "         " )
      for ( action <- group ) {
        Console.print( " " + action.name + " " )
      }
      Console.print( "\n" )
    }
    discardPrompt()
  }

  def message() = {
    Console.println( vert + " Message: " + getMessage() )
  }

  def title() = {
    Console.println( vert + " gin 0.1 (straight gin)                         " )
  }

  def score() = {
    Console.println( vert + " Points Me: XXX     You: XXX                    " )
    Console.println( vert + " Hands      XXX          XXX                    " )
  }

  def inProgress() : Boolean = {
    game != null
  }

  def player1() = {
    if ( inProgress() ) {
      if ( cheatsOn ) {
        var line = vert + " Me: "
        val state = gameState()
        val mapped = state.hands( me ).cards.map( ( c ) => { renderCard( c ) } )
        line += mapped.mkString( " " )
        line += "\n"
        Console.print( line )
      } else {
        Console.println( vert + " Me: XX XX XX XX XX XX XX XX XX XX              " )
      }
    } else {
      Console.println( vert + " Me: XX XX XX XX XX XX XX XX XX XX              " )
    }
  }

  def player2() = {
    if ( !inProgress() ) {
      Console.println( vert + " You: XX XX XX XX XX XX XX XX XX XX             " )
    } else {
      var line = vert + " You: "
      val state = gameState()
      val mapped = state.hands( you ).cards.map( ( c ) => { renderCard( c ) } )
      line += mapped.mkString( " " )
      line.padTo( 49, " " )
      line += "\n"
      Console.print( line )
    }
  }

  def gameState() = {
    game.currentState.asInstanceOf[GinState]
  }

  def stock() = {
    if ( !inProgress() ) {
      Console.println( vert + " Discard: XX    Stock: XX                       " )
    } else {
      if ( cheatsOn ) {
        var line = vert + " Discards: "
        val dg = gameState().discards.grouped( 10 )
        for ( g <- dg ) {
          line += g.map( ( c ) => { renderCard( c ) } ).mkString( " " )
          line += "\n          "
        }
        line += " \n"
        Console.print( line )

        var sline = vert + " Stock: "
        val sg = gameState().stock.grouped( 10 )
        for ( g <- sg ) {
          sline += g.map( ( c ) => { renderCard( c ) } ).mkString( " " )
          sline += "\n          "
        }
        sline += " \n"
        Console.print( sline )
      } else {
        val state = gameState()
        if ( state.discards.isEmpty ) {
          Console.println( vert + " Discard: __    Stock: XX                       " )
        } else {
          Console.print( vert + " Discard: " + renderCard( state.discards.top ) )
          Console.print( "    Stock: XX                       \n" )
        }
      }
    }
  }

  def separator() = {
    line()
    Console.println( " " )
  }

  def dash = "\u2500"
  def vert = "\u2502"

  def line() = {
    for( i <- 0 until 50 ){
      Console.print(dash)
    }
    Console.print( "\n" )
  }

  def blank() {
    Console.print( vert +"\n" )
  }

  def chart() {

    if ( !inProgress ) {

      line()
      Console.println( vert + "  A  2  3  4  5  6  7  8  9  T  J  Q  K         " )
      Console.println( vert + "C                                               " )
      Console.println( vert + "D                                               " )
      Console.println( vert + "H                                               " )
      Console.println( vert + "S                                               " )
      line()

    } else {

      if ( hintsOn ) {

        line()
        Console.println( vert + "  A  2  3  4  5  6  7  8  9  T  J  Q  K         " )
        val suits = List( "C", "D", "H", "S" )
        val ranks = List( "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K" )
        val lines = for ( s <- suits ) yield {
          var line = vert + "" + char2card( s ) + " "
          line += ranks.map( ( r ) => { findCard( s, r ) } ).mkString( "  " )
          line += "\u001B[0m"
          line
        }
        Console.println( lines.mkString( "\n" ) )
        line()
      }

    }
  }

  def char2card( c : String ) : String = {

    c match {
      case "C" => { "\u001B[36m\u2663" }
      case "D" => { "\u001B[31m\u2666" }
      case "H" => { "\u001B[31m\u2665" }
      case "S" => { "\u001B[36m\u2660" }
      case _ => { "J" }
    }
  }

  def findCard( s : String, r : String ) : String = {
    val c = Card.stringToCard( s + r )
    val state = gameState()

    if ( state.discards.contains( c ) ) {
      "D"
    } else if ( state.stock.contains( c ) ) {
      "S"
    } else if ( state.hands( me ).cards.contains( c ) ) {
      "M"
    } else if ( state.hands( you ).cards.contains( c ) ) {
      "Y"
    } else {
      "_"
    }

  }

  def renderCard( c : Card ) = { renderSuit( c.suit ) + renderRank( c.rank ) }

  def renderSuit( s : Int ) : String = {

    s match {
      case 0 => { "\u001B[36m\u2663" }
      case 1 => { "\u001B[31m\u2666" }
      case 2 => { "\u001B[31m\u2665" }
      case 3 => { "\u001B[36m\u2660" }
      case 4 => { "J" }
    }

  }

  def renderRank( r : Int ) : String = {

    var s = r match {
      case 0 => { "A" }
      case 1 => { "2" }
      case 2 => { "3" }
      case 3 => { "4" }
      case 4 => { "5" }
      case 5 => { "6" }
      case 6 => { "7" }
      case 7 => { "8" }
      case 8 => { "9" }
      case 9 => { "T" }
      case 10 => { "J" }
      case 11 => { "Q" }
      case 12 => { "K" }
      case 13 => { "J" }
    }

    s += "\u001B[0m"

    s
  }
}