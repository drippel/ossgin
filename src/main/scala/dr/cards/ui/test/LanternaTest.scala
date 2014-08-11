package dr.cards.ui.test

import java.nio.charset.Charset
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.terminal.Terminal
import com.googlecode.lanterna.terminal.swing.SwingTerminal
import com.googlecode.lanterna.gui.Window
import com.googlecode.lanterna.gui.GUIScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.screen.DefaultScreen
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame

class LanternaTest {

}

object LanternaTest {

  def main( args : Array[String] ) = {

    val factory = new DefaultTerminalFactory();
    val terminal = factory.createTerminal().asInstanceOf[SwingTerminalFrame]
    val screen = new DefaultScreen( terminal )
    val guiScreen = new GUIScreen(screen)

      screen.startScreen();

      val myWindow = new MyWindow();

      guiScreen.showWindow( myWindow, GUIScreen.Position.CENTER );

      try{
      Thread.sleep( 5000 )
      }
      catch {
        case _ : Throwable => {}
      }

      //Do GUI logic here

      myWindow.close()

      guiScreen.getScreen().clear()
      guiScreen.getScreen().stopScreen();

      terminal.clearScreen()

  }

   class MyWindow extends Window( "custom window" ) {
   }

}