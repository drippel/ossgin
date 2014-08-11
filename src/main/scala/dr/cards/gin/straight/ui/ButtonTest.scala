package dr.cards.gin.straight.ui

import com.googlecode.lanterna.terminal.Terminal
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.gui.GUIScreen
import com.googlecode.lanterna.gui.DefaultBackgroundRenderer
import com.googlecode.lanterna.gui.Window
import com.googlecode.lanterna.gui.component.AbstractComponent
import com.googlecode.lanterna.gui.TextGraphics
import com.googlecode.lanterna.gui.Theme.Category
import com.googlecode.lanterna.gui.component.Panel
import com.googlecode.lanterna.gui.component.Button
import com.googlecode.lanterna.gui.Action
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame
import com.googlecode.lanterna.screen.DefaultScreen
import com.googlecode.lanterna.TerminalSize
import javax.swing.JFrame


object ButtonTest {

    def main( args : Array[String] ) : Unit =
    {
    val factory = new DefaultTerminalFactory();
    val terminal = factory.createTerminal().asInstanceOf[SwingTerminalFrame]
    terminal.setVisible(true);
    terminal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    val screen = new DefaultScreen( terminal )
    val guiScreen = new GUIScreen(screen)

        guiScreen.getScreen().startScreen();
        guiScreen.setBackgroundRenderer(new DefaultBackgroundRenderer("GUI Test"));

        val mainWindow = new Window("Window with panels");
        mainWindow.addComponent(new AbstractComponent() {
            def repaint( graphics : TextGraphics ) =
            {
                graphics.applyTheme(graphics.getTheme().getDefinition(Category.SHADOW));
                for( y <- 0 to graphics.getHeight() )
                    for( x <- 0 to graphics.getWidth() )
                        graphics.drawString(x, y, "X");
            }

            def calculatePreferredSize() : TerminalSize = {
                return new TerminalSize(20, 6);
            }
        });

        var buttonPanel = new Panel(Panel.Orientation.HORIZONTAL);
        var button1 = new Button("Button1", new Action() {
            def doAction() =
            {
                mainWindow.close();
            }
        });


        var button2 = new Button("Button2");
        var button3 = new Button("Button3");
        buttonPanel.addComponent(button1);
        buttonPanel.addComponent(button2);
        buttonPanel.addComponent(button3);
        mainWindow.addComponent(buttonPanel);

        guiScreen.showWindow(mainWindow, GUIScreen.Position.CENTER);
        guiScreen.getScreen().stopScreen();
    }


}

