package dr.cards.gin.straight.ui

import com.googlecode.lanterna.TerminalFacade
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
import com.googlecode.lanterna.terminal.TerminalSize
import com.googlecode.lanterna.gui.component.EditArea

class SGLanterna {

}

object SGLanterna {

  var terminal : SwingTerminal = null
  var screen : Screen = null
  var guiScreen : GUIScreen = null
  var theWindow : Window = null

  var game : StraightGin = new StraightGin()
  var me = new Player( "me" )
  var playerStrategy : ComputerPlayer = ComputerPlayer.computerPlayer( 0, game, me ).get
  var you = new Player( "you" )
  var cheatsOn = false
  var hintsOn = false

  def main( args : Array[String] ) = {

    // these do not draw the screen properly
    // terminal = TerminalFacade.createTextTerminal();
    // terminal = TerminalFacade.createCygwinTerminal()
    // terminal = TerminalFacade.createUnixTerminal()
    // terminal = TerminalFacade.createTerminal();
    terminal = TerminalFacade.createSwingTerminal()
    // terminal.applyBackgroundColor( Terminal.Color.BLUE )
    // terminal.clearScreen()
    // terminal.getJFrame().setResizable(false)

    screen = new Screen( terminal, 80, 30 )
    guiScreen = TerminalFacade.createGUIScreen( screen );

    screen.startScreen();

    theWindow = createWindow()

    guiScreen.showWindow( theWindow, GUIScreen.Position.FULL_SCREEN );

    // screen.stopScreen();
  }

  def createWindow() : Window = {

    theWindow = new Window( "Open Source Straight Gin" );
    theWindow.setBorder( new Border.Standard() )

    // x,y
    theWindow.addComponent( new EmptySpace( 0, 1 ) );
    theWindow.addComponent( new Label( "Welcome to OSS Gin 0.1 \u2663 \u2666 \u2665 \u2660" ) )
    theWindow.addComponent( new EmptySpace( 0, 1 ) );
    // y=4

    theWindow.addComponent( createScorePanel() )

    theWindow.addComponent( createGamePanel() )

    initialButtonPanel()
    theWindow.addComponent( buttonPanel )

    theWindow.addComponent( createMessagePanel() )

    theWindow
  }

  def createGamePanel() : Panel = {

    val p1 = new Panel( Panel.Orientation.HORISONTAL )
    p1.addComponent( new Label( "My Hand:" ) )
    p1.addComponent( new Label( "__ __ __ __ __ __ __ __ __ __ __" ) )

    val p2 = new Panel( Panel.Orientation.HORISONTAL )
    p2.addComponent( new Label( "Discard:" ) )
    p2.addComponent( new Label( "__" ) )
    p2.addComponent( new EmptySpace( 0, 5 ) )
    p2.addComponent( new Label( "Stock:" ) )
    p2.addComponent( new Label( "__" ) )

    val p3 = new Panel( Panel.Orientation.HORISONTAL )
    p3.addComponent( new Label( "Your Hand:" ) )
    p3.addComponent( new Label( "__ __ __ __ __ __ __ __ __ __ __ __" ) )

    val panel = new Panel( new Border.Standard(), Panel.Orientation.VERTICAL )
    panel.addComponent(p1)
    panel.addComponent(p2)
    panel.addComponent(p3)
    panel
  }

  val myScore = new Label("000")
  val yourScore = new Label("000")
  val myHands = new Label("0")
  val yourHands = new Label("0")

  def createScorePanel() : Panel = {
    val panel1 = new Panel( new Border.Standard(), Panel.Orientation.HORISONTAL )
    panel1.addComponent( new Label( "Points Me:" ) )
    panel1.addComponent( myScore )
    panel1.addComponent( new Label( "      You:" ) )
    panel1.addComponent( yourScore )

    val panel2 = new Panel( new Border.Standard(), Panel.Orientation.HORISONTAL )
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
    val panel = new Panel( new Border.Standard(), Panel.Orientation.HORISONTAL )
    messages = new EditArea( new TerminalSize( 30, 5 ) )
    panel.addComponent(messages)
    panel
  }

  val buttonPanel = new Panel( new Border.Standard(), Panel.Orientation.HORISONTAL )

  val choosePlayer = new Button( "Player", new Action() {
    def doAction() : Unit = {
    }
  } )

  val newGame = new Button( "New", new Action() {
    def doAction() : Unit = {
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
    }
  } );

  val cheat = new Button( "Cheat", new Action() {
    def doAction() : Unit = {
    }
  } );

  val exitButton = new Button( "Exit", new Action() {
    def doAction() : Unit = {
      theWindow.close();
      screen.stopScreen();
    }
  } );

  val discard = new Button( "XX", new Action() {
    def doAction() : Unit = {
    }
  } )

  val stock = new Button( "XX", new Action() {
    def doAction() : Unit = {
    }
  } );

  val playerHand = new ListBuffer[Button]()

  def initialButtonPanel() : Unit = {

    buttonPanel.addComponent( choosePlayer );
    buttonPanel.addComponent( newGame );
    buttonPanel.addComponent( options );
    buttonPanel.addComponent( help );
    buttonPanel.addComponent( hint );
    buttonPanel.addComponent( cheat );
    buttonPanel.addComponent( exitButton );

  }
}