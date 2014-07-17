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

class SGLanterna {

}


object SGLanterna {

  var terminal : Terminal = null
  var screen   : Screen = null
  var guiScreen : GUIScreen = null
  var theWindow : Window = null

  def main( args : Array[String] ) = {

        terminal = TerminalFacade.createTextTerminal();
        // terminal.applyBackgroundColor( Terminal.Color.BLUE )
        terminal.clearScreen()

        screen = new Screen( terminal, 80, 30 )
        guiScreen = TerminalFacade.createGUIScreen( screen );

        screen.startScreen();

        theWindow = createWindow()

        guiScreen.showWindow( theWindow, GUIScreen.Position.FULL_SCREEN);

        screen.stopScreen();
  }

  def createWindow() : Window = {

        theWindow = new Window("Open Source Straight Gin");
        theWindow.setBorder(new Border.Standard() )
        theWindow.addComponent(new EmptySpace(1, 1));
        theWindow.addComponent(new Label("Welcome to OSS Gin 0.1" ) )
        theWindow.addComponent(new EmptySpace(5, 1));
        theWindow.addComponent(new EmptySpace(1, 10));

        theWindow.addComponent( createGamePanel() )

        theWindow.addComponent( createButtonPanel() )

        theWindow
  }

  def createGamePanel() : Panel = {
    val panel = new Panel(new Border.Standard(), Panel.Orientation.HORISONTAL )
    panel
  }

  def createButtonPanel() : Panel = {

        val buttonPanel = new Panel(new Border.Standard(), Panel.Orientation.HORISONTAL )
        val exitButton = new Button("Exit", new Action() {
          def doAction() : Unit = {
            theWindow.close();
          }
        });

        buttonPanel.addComponent(new EmptySpace(50, 1));
        buttonPanel.addComponent(exitButton);
        buttonPanel
  }
}