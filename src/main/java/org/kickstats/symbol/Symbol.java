
package org.kickstats.symbol;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Creates a loading screen window with the SCP symbol and a randomly moving 
 * loading screen symbol animation on it.
 * 
 * Has a menu which allows for someone to change the speed of the rotation
 * and random movement. This was an experiment designed to help me work on 
 * creating more precise and defined shapes as well as work on random movement, 
 * boundaries, and refactoring. The class used by this class as a panel is 
 * called SymbolPanel.
 * 
 * @author Chase Sonnemaker using code from Leon Tabak.
 * @version 7 April 2020
 */
public class Symbol extends JFrame implements ActionListener{
    
    private final int FRAME_WIDTH = 800;
    private final int FRAME_HEIGHT = 800;
    private final String FRAME_TITLE = "Loading...";
    private final int SPEEDS = 6;
    private final String SPEED = "Speed";

    private final SymbolPanel panel;
    
    
    /**
     * Creates a window with the animations of the symbols,
     * containing a menu for changing the speed of the animation.
     * 
     * The speed menu will increase, by a labeled factor, the speed of the 
     * rotation of the symbol and loading screen sign, as well as increase the 
     * random movement of the loading screen sign.
     */
    public Symbol() {
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle(FRAME_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Container pane = this.getContentPane();
        this.panel = new SymbolPanel();
        pane.add(panel);
        this.panel.setBackground(Color.BLACK);
        
        //Speed menu creation
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu speedMenu = new JMenu(SPEED);
        menuBar.add(speedMenu);
        
        //Add speeds to the menu
        for(int i = 0; i < SPEEDS; i++) {
            String label = SPEED + " " + i;
            JMenuItem item = new JMenuItem(label);
            item.addActionListener(this);
            item.setActionCommand(label);
            speedMenu.add(item);
        }// for
        
        this.setVisible(true);
    }// Symbol()
    
    
    /**
     * When a speed is selected from the speed menu, changes the speed of the 
     * rotation and movement animation by the labeled factor.
     * 
     * @param event A user click on the speed menu. 
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        String actionCommand = event.getActionCommand();
        
        if (actionCommand.indexOf(SPEED) >= 0) {
            int i = SPEED.length();
            String suffix = actionCommand.substring(i).trim();
            int index = Integer.parseInt(suffix);
            this.panel.setSpeed(index);
        } // if
            
    } // actionPerformed( ActionEvent ) 
    
    
    /**
     * Calls the constructor of this class to create 
     * the window and begin the animation.
     */
    public static void main(String[] args) {
        Symbol ls = new Symbol();
    } // main( String [] )
    
}// Symbol()
