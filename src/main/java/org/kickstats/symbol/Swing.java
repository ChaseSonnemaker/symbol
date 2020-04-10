package org.kickstats.symbol;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Creates window that animates a twisting spinning cube made up of quadratic 
 * curves and has menus for changing background color, cube color, and cube 
 * size.
 * 
 * An experiment working with different types of shapes, menus, and 
 * movement as well as a general introduction to 2D graphic design.
 * 
 * @author Leon Tabak, edited by Chase Sonnemaker
 * @version 5 April 2020
 */
public class Swing extends JFrame implements ActionListener {

    private final int FRAME_WIDTH = 800;
    private final int FRAME_HEIGHT = 800;
    private final String FRAME_TITLE = "Swing";
    private final int NUMBER_OF_MENU_ITEMS = 8;
    private final String BG_COLOR = "Background Color";
    private final String FG_COLOR = "Foreground Color";
    private final String SIZESTRING = "Size";

    private final List<Color> bgPalette = new ArrayList<>();
    private final List<Color> fgPalette = new ArrayList<>();
    private final List<Double> sizes = new ArrayList<>();
    private final SwingPanel panel;

    public Swing() {
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle(FRAME_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = this.getContentPane();
        this.panel = new SwingPanel();
        pane.add(panel);

        //List of randomized background colors
        Random rng = new Random();
        for (int i = 0; i < NUMBER_OF_MENU_ITEMS; i++) {
            int red = 64 + rng.nextInt(128);
            int green = 64 + rng.nextInt(128);
            int blue = 64 + rng.nextInt(128);
            Color color = new Color(red, green, blue);
            bgPalette.add(color);
        } // for
        this.panel.setBackground(bgPalette.get(0));

        //List of randomized cube colors
        for (int i = 0; i < NUMBER_OF_MENU_ITEMS; i++) {
            int red = 32 + rng.nextInt(224);
            int green = 32 + rng.nextInt(224);
            int blue = 32 + rng.nextInt(224);
            Color color = new Color(red, green, blue);
            fgPalette.add(color);
        } // for
        this.panel.setColor(fgPalette.get(0));
        
        //List of cube wave sizes
        for(int i = 0; i < NUMBER_OF_MENU_ITEMS; i++) {
            sizes.add(i * 0.2);
        }
        this.panel.setMaximum(sizes.get(5));
        
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu bgColorMenu = new JMenu(BG_COLOR);
        menuBar.add(bgColorMenu);

        for (int i = 0; i < NUMBER_OF_MENU_ITEMS; i++) {
            String label = BG_COLOR + " " + i;
            JMenuItem item = new JMenuItem(label);
            item.addActionListener(this);
            item.setActionCommand(label);
            bgColorMenu.add(item);
        } // for

        JMenu fgColorMenu = new JMenu(FG_COLOR);
        menuBar.add(fgColorMenu);

        for (int i = 0; i < NUMBER_OF_MENU_ITEMS; i++) {
            String label = FG_COLOR + " " + i;
            JMenuItem item = new JMenuItem(label);
            item.addActionListener(this);
            item.setActionCommand(label);
            fgColorMenu.add(item);
        } // for
        
        JMenu size = new JMenu("Size");
        menuBar.add(size);
        
        for(int i = 0; i < NUMBER_OF_MENU_ITEMS; i++) {
            String label = SIZESTRING + " " + (i);
            JMenuItem item = new JMenuItem(label);
            item.addActionListener(this);
            item.setActionCommand(label);
            size.add(item);
        }

        this.setVisible(true);
    } // Swing()

    @Override
    public void actionPerformed(ActionEvent event) {
        String actionCommand = event.getActionCommand();

        if (actionCommand.indexOf(BG_COLOR) >= 0) {
            int i = BG_COLOR.length();
            String suffix = actionCommand.substring(i).trim();
            int index = Integer.parseInt(suffix);
            this.panel.setBackground(bgPalette.get(index));
        } // if
        else if (actionCommand.indexOf(FG_COLOR) >= 0) {
            int i = FG_COLOR.length();
            String suffix = actionCommand.substring(i).trim();
            int index = Integer.parseInt(suffix);
            this.panel.setColor(fgPalette.get(index));
        } // if
        else if(actionCommand.indexOf(SIZESTRING) >= 0) {
            int i = SIZESTRING.length();
            String suffix = actionCommand.substring(i).trim();
            int index = Integer.parseInt(suffix);
            this.panel.setMaximum(sizes.get(index));
        }// else if
            
    } // actionPerformed( ActionEvent )

    public static void main(String[] args) {
        Swing swing = new Swing();
    } // main( String [] )

} // Swing
