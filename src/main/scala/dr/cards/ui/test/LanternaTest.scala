package dr.cards.ui.test

import com.googlecode.lanterna.TerminalFacade
import java.nio.charset.Charset
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.terminal.Terminal
import com.googlecode.lanterna.terminal.swing.SwingTerminal
import com.googlecode.lanterna.terminal.text.CygwinTerminal
import com.googlecode.lanterna.gui.Window
import com.googlecode.lanterna.gui.GUIScreen

class LanternaTest {

}

object LanternaTest {

  def main( args : Array[String] ) = {
    val terminal = TerminalFacade.createTerminal( System.in, System.out, Charset.forName( "UTF8" ) );
    // val screen = new Screen(terminal);
    // val screen = TerminalFacade.createScreen(new SwingTerminal());
    val screen = TerminalFacade.createScreen( TerminalFacade.createCygwinTerminal() );

    val gui = TerminalFacade.createGUIScreen( screen );
    if ( gui != null ) {
      gui.getScreen().startScreen();
      gui.setTitle( "GUI Test" );
      val myWindow = new MyWindow();
      gui.showWindow( myWindow, GUIScreen.Position.CENTER );

      Thread.sleep( 5000 )

      //Do GUI logic here

      myWindow.close()

      gui.getScreen().clear()
      gui.getScreen().stopScreen();

      terminal.clearScreen()
    }

  }

   class MyWindow extends Window( "custom window" ) {
   }

}