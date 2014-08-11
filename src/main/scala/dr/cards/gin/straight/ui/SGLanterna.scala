package dr.cards.gin.straight.ui

import com.googlecode.lanterna.gui.Window
import com.googlecode.lanterna.gui.Border
import com.googlecode.lanterna.gui.component.EmptySpace
import com.googlecode.lanterna.gui.component.Label
import com.googlecode.lanterna.gui.component.Panel
import com.googlecode.lanterna.gui.component.Button
import com.googlecode.lanterna.gui.Action
import com.googlecode.lanterna.gui.GUIScreen
import com.googlecode.lanterna.terminal.Terminal
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.gui.Interactable
import dr.cards.model.Player
import dr.cards.gin.straight.StraightGin
import dr.cards.gin.straight.player.ComputerPlayer
import com.googlecode.lanterna.terminal.swing.SwingTerminal
import scala.collection.mutable.ListBuffer
import com.googlecode.lanterna.gui.component.TextArea
import com.googlecode.lanterna.gui.component.EditArea
import dr.cards.gin.straight.play.Setup
import dr.cards.gin.straight.play.Deal
import dr.cards.model.Card
import dr.cards.gin.straight.play.Sort
import dr.cards.gin.straight.play.Take
import dr.cards.gin.straight.play.Discard
import dr.cards.gin.straight.play.Stock
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame
import com.googlecode.lanterna.screen.DefaultScreen
import com.googlecode.lanterna.TerminalSize
import javax.swing.JFrame



class SGLanterna {

}

object SGLanterna {

  var terminal : SwingTerminalFrame = null
  var screen : Screen = null
  var guiScreen : GUIScreen = null
  var theWindow : Window = null

  var game : StraightGin = new StraightGin()
  var me = new Player( "me" )
  var playerStrategy : ComputerPlayer = ComputerPlayer.computerPlayer( 0, game, me ).get
  var you = new Player( "you" )
  var cheatsOn = false
  var hintsOn = false

  var playersTurn = true
  var playersDiscard = false

  def main( args : Array[String] ) = {

    val factory = new DefaultTerminalFactory();
    terminal = factory.createTerminal().asInstanceOf[SwingTerminalFrame]
    terminal.setVisible(true);
    terminal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    screen = new DefaultScreen( terminal )
    guiScreen = new GUIScreen(screen)

    screen.startScreen();

    theWindow = createWindow()

    guiScreen.showWindow( theWindow, GUIScreen.Position.FULL_SCREEN );

    // screen.stopScreen();
  }

  def createWindow() : Window = {

    theWindow = new Window( "Open Source Straight Gin" );
    theWindow.setBorder( new Border.Standard() )

    theWindow.addComponent( new EmptySpace( 0, 1 ) );
    theWindow.addComponent( new Label( "Welcome to OSS Gin 0.1 \u2663 \u2666 \u2665 \u2660" ) )

    theWindow.addComponent( createScorePanel() )

    theWindow.addComponent( createGamePanel() )

    initialButtonPanel()
    theWindow.addComponent( buttonPanel )

    theWindow.addComponent( createMessagePanel() )
    theWindow.addComponent( new EmptySpace( 0, 1 ) );
    theWindow.addComponent( createHintPanel() )

    theWindow
  }

  val myCards = ListBuffer[Label]( new Label( "__" ), new Label( "__" ), new Label( "__" ), new Label( "__" ), new Label( "__" ), new Label( "__" ), new Label( "__" ), new Label( "__" ), new Label( "__" ), new Label( "__" ), new Label( "__" ) )

  def createComputerPlayerPanel() : Panel = {
    val p1 = new Panel( Panel.Orientation.HORIZONTAL )
    p1.addComponent( new Label( "My Hand:" ) )
    for ( l <- myCards ) {
      p1.addComponent( l )
    }
    p1
  }

  def setPlayersDiscard() = {
    playersDiscard = true
    playersTurn = false
  }

  def setPlayersTurn() = {
    playersDiscard = false
    playersTurn = true
  }

  val discardButton = new Button( "__", new Action() {
    def doAction() : Unit = {
      if ( playersTurn ) {
        if ( !game.gameState.discards.isEmpty ) {
          setPlayersDiscard()
          val play = new Take( game, you )
          play.execute()

          // detect gin
          if ( game.hasGin( you ) ) {
          } else {
          }

          refreshScreen
        }
      }
    }
  } )

  val stockButton = new Button( "__", new Action() {
    def doAction() : Unit = {
      if ( playersTurn ) {
        if ( !game.gameState.stock.isEmpty ) {
          setPlayersDiscard()
          val play = new Stock( game, you )
          play.execute()

          // detect gin
          if ( game.hasGin( you ) ) {
          } else {
          }

          refreshScreen
        }
      }
    }
  } )

  def createStockPanel() : Panel = {
    val p2 = new Panel( Panel.Orientation.HORIZONTAL )
    p2.addComponent( new Label( "Discard:" ) )
    p2.addComponent( discardButton )
    p2.addComponent( new EmptySpace( 10, 0 ) )
    p2.addComponent( new Label( "Stock:" ) )
    p2.addComponent( stockButton )
    p2
  }

  def createPlayerPanel() : Panel = {
    val p3 = new Panel( Panel.Orientation.HORIZONTAL )
    p3.addComponent( new Label( "Your Hand:" ) )
    for ( i <- 0 to 10 ) {
      var btn = new CardButton( "__", None, new CardButtonAction() )
      playerCards += btn
    }
    for ( b <- playerCards ) {
      p3.addComponent( b )
    }

    val buttons = new Panel( Panel.Orientation.HORIZONTAL )
    buttons.addComponent( byRank )
    buttons.addComponent( bySuit )

    val playerPanel = new Panel( Panel.Orientation.VERTICAL )
    playerPanel.addComponent( p3 )
    playerPanel.addComponent( buttons )
    playerPanel
  }

  def setMessage( msg : String ) = {
    val combined = msg +"\n" + messages.getData()
    messages.setData(combined)
  }

  class CardButton( val txt : String, var card : Option[Card], val action : CardButtonAction ) extends Button( txt, action ) {
    action.button = this
  }

  class CardButtonAction() extends Action {
    var button : CardButton = null
    def doAction() : Unit = {
      if ( playersDiscard ) {

        Console.println( "clicky:" + button.getText() )
        // computer player here
        val i = playerCards.indexOf( button )
        val d = new Discard( game, you, i )
        d.execute

        // detect gin or game over
        if ( game.isRoundOver ) {
          setMessage( "Round is over - no winner today!" )
        } else {

          // computer player
          playerStrategy.choose()

          // message for what the player took

          // detect gin
          if ( game.hasGin( me ) ) {
            setMessage( "I win loooosaah!" )
          } else {

            playerStrategy.discard()

            // detect gin or game over
            if ( game.isRoundOver ) {
              setMessage( "Round is over - no winner today!" )
            } else {

              //
              setMessage( "Your turn!" )
            }
          }
          setPlayersTurn()
        }
        refreshScreen()
      }
    }
  }

  val byRank = new Button( "By Rank", new Action() {
    def doAction() : Unit = {
      val s = new Sort( game, you, new Card.RankOrdering() )
      s.execute
      refreshScreen
    }
  } )

  val bySuit = new Button( "By Suit", new Action() {
    def doAction() : Unit = {
      val s = new Sort( game, you, new Card.SuitRankOrdering() )
      s.execute
      refreshScreen
    }
  } )

  def inProgress() : Boolean = {
    game != null && game.inProgress()
  }

  var playerCards = new ListBuffer[Button]()

  def createGamePanel() : Panel = {

    val panel = new Panel( new Border.Standard(), Panel.Orientation.VERTICAL )
    panel.addComponent( createPlayPanel() )
    panel.addComponent( createComputerPlayerPanel() )
    panel.addComponent( createStockPanel() )
    panel.addComponent( createPlayerPanel() )
    panel
  }

  val playMessage = new Label( "Start a new game." )

  def createPlayPanel() : Panel = {
    val panel = new Panel( Panel.Orientation.HORIZONTAL )
    panel.addComponent( playMessage )
    panel
  }

  val myScore = new Label( "000" )
  val yourScore = new Label( "000" )
  val myHands = new Label( "0" )
  val yourHands = new Label( "0" )

  def createScorePanel() : Panel = {
    val panel1 = new Panel( Panel.Orientation.HORIZONTAL )
    panel1.addComponent( new Label( "Points Me:" ) )
    panel1.addComponent( myScore )
    panel1.addComponent( new Label( "      You:" ) )
    panel1.addComponent( yourScore )

    val panel2 = new Panel( Panel.Orientation.HORIZONTAL )
    panel2.addComponent( new Label( "Hands Me:" ) )
    panel2.addComponent( myHands )
    panel2.addComponent( new Label( "      You:" ) )
    panel2.addComponent( yourHands )

    val panel = new Panel( new Border.Standard(), Panel.Orientation.VERTICAL )
    panel.addComponent( panel1 )
    panel.addComponent( panel2 )
    panel
  }

  var messages : EditArea = null

  def createMessagePanel() : Panel = {
    val panel = new Panel( Panel.Orientation.HORIZONTAL )
    messages = new EditArea( new TerminalSize( 30, 5 ) )
    panel.addComponent( messages )
    panel
  }

  var hints : EditArea = null

  def createHintPanel() : Panel = {
    val panel = new Panel( Panel.Orientation.HORIZONTAL )
    hints = new EditArea( new TerminalSize( 50, 10 ) )
    panel.addComponent( hints )
    panel
  }

  val buttonPanel = new Panel( new Border.Standard(), Panel.Orientation.HORIZONTAL )

  val choosePlayer = new Button( "Player", new Action() {
    def doAction() : Unit = {
    }
  } )

  def refreshScreen() : Unit = {

    // update computer hand

    // update stock/discard
    if ( !game.gameState.discards.isEmpty ) {
      discardButton.setText( renderCard( game.gameState.discards.top ) )
    } else {
      discardButton.setText( "__" )
    }

    // update player hand
    refreshPlayerHand()

    // update hints/cheats
    refreshCheats

    // render
    screen.refresh()
  }

  def refreshCheats() : Unit = {
    hints.setData( chart() )
  }

  def chart() : String = {

    var out = ""

      if ( hintsOn ) {

        out += " A  2  3  4  5  6  7  8  9  T  J  Q  K \n"
        val suits = List( "C", "D", "H", "S" )
        val ranks = List( "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K" )
        val lines = for ( s <- suits ) yield {
          var line = char2card( s ) + " "
          line += ranks.map( ( r ) => { findCard( s, r ) } ).mkString( "  " )
          line
        }
        out += lines.mkString( "\n" )
      }

    out
  }

  def char2card( c : String ) : String = {

    c match {
      case "C" => { "\u2663" }
      case "D" => { "\u2666" }
      case "H" => { "\u2665" }
      case "S" => { "\u2660" }
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
  def gameState() = { game.gameState() }

  def resetPlayerCards() = {
    for ( b <- playerCards ) {
      // terminal.applyForegroundColor( Terminal.Color.BLACK)
      b.setText( "__" )
    }
  }

  def refreshPlayerHand() = {
    if ( inProgress() ) {
      resetPlayerCards()
      val state = game.gameState
      val mapped = state.hands( you ).cards
      val zipped = mapped.zip( playerCards )
      for ( p <- zipped ) {
        if ( isRed( p._1.suit ) ) {
          // Console.println("red")
          // terminal.applyForegroundColor( Terminal.Color.RED )
          // terminal.applyBackgroundColor( Terminal.Color.RED )
        }
        p._2.setText( renderCard( p._1 ) )
        // terminal.applyForegroundColor( Terminal.Color.BLACK )
      }
    }
  }

  val newGame = new Button( "New", new Action() {
    def doAction() : Unit = {

      // detect if game in progress
      if ( !inProgress ) {
        val setup = new Setup( game, me, you )
        setup.execute
        val deal = new Deal( game, me )
        deal.execute()

        refreshScreen()

        setPlayersTurn()
      }
    }
  } );

  val options = new Button( "Options", new Action() {
    def doAction() : Unit = {
    }
  } );

  val help = new Button( "Help", new Action() {
    def doAction() : Unit = {
    }
  } );

  val hint = new Button( "Hint", new Action() {
    def doAction() : Unit = {
      hintsOn = !hintsOn
      refreshScreen()
    }
  } );

  val cheat = new Button( "Cheat", new Action() {
    def doAction() : Unit = {
      cheatsOn = !cheatsOn
      refreshScreen()
    }
  } );

  val exitButton = new Button( "Exit", new Action() {
    def doAction() : Unit = {
      theWindow.close();
      screen.stopScreen();
    }
  } );

  def initialButtonPanel() : Unit = {

    buttonPanel.addComponent( choosePlayer );
    buttonPanel.addComponent( newGame );
    buttonPanel.addComponent( options );
    buttonPanel.addComponent( help );
    buttonPanel.addComponent( hint );
    buttonPanel.addComponent( cheat );
    buttonPanel.addComponent( exitButton );

  }

  def renderCard( c : Card ) = { renderSuit( c.suit ) + renderRank( c.rank ) }

  def renderSuit( s : Int ) : String = {

    s match {
      case 0 => { "\u2663" }
      case 1 => { "\u2666" }
      case 2 => { "\u2665" }
      case 3 => { "\u2660" }
      case 4 => { "J" }
    }

  }

  def isRed( s : Int ) : Boolean = {
    s match {
      case 0 => { false }
      case 1 => { true }
      case 2 => { true }
      case 3 => { false }
      case 4 => { false }
    }
  }

  def renderRank( r : Int ) : String = {

    r match {
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

  }
}

