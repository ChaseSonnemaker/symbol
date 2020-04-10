
package org.kickstats.symbol;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Draws an SCP symbol and a loading screen symbol and animates them
 * based on a timer.
 * 
 * Contains methods used to draw and animate both symbols. This class is used 
 * by the LoadingScreen class. A large portion of the instance variables 
 * found in this class were calculated using mathematical reasoning, other 
 * graphing software, and trial and error.
 * 
 * @author Chase Sonnemaker using code from Leon Tabak
 * @version 7 April 2020
 */
public class SymbolPanel extends JPanel implements ActionListener {
    
    private final double ARROW_ANGLE = (2 * Math.PI) / 3;
    
    //Speed Information
    private int speed = 1;
    
    //Main Circle Information
    double centerX = 0;
    double centerY = -0.4;
    double radius = 0.4;
    
    //Arrow Lines Information
    double origX = 0;
    double origY = -0.875;
    double origX2 = 0;
    double origY2 = -0.6;
    
    //Arrow Points Information
    double arpX = 0;
    double arpY = -0.45;
    double arX = -0.075;
    double arY = -0.6;
    double arX2 = 0.075;
    double arY2 = -0.6;
    
    //Outer Circle Hats Information
    double outerX1 = 0.12;
    double outerY1 = -0.9;
    double outerX2 = 0.1;
    double outerY2 = -0.98;
    double outerX3 = -0.1;
    double outerY3 = -0.98;
    double outerX4 = -0.12;
    double outerY4 = -0.9;
    
    //Outer Circle Arcs Information
    double centerX2 = 0;
    double centerY2 = -0.4;
    double radius2 = 0.514;
    double startAngle = 197;
    double endAngle = -93.5;
    
    //Loading Lines Information
    double loadStartX = 0;
    double loadStartY = 0.425;
    double loadEndX = 0;
    double loadEndY = 0.475;
    double loadCenterX = 0;
    double loadCenterY = 0.525;
    int loadLines = 8;
    
    //Rotation Information
    double rotateSymbol = 360;
    double rotateLoad = 60;
    double moveSymbol = (2 * Math.PI) / this.rotateSymbol;
    double moveLoad = (2 * Math.PI) / this.rotateLoad;
    double angleSymbol = 0;
    double angleLoad = 0;
    
    //Color Information
    private Color color = Color.white;
    
    //Movement Information
    double xMove = 0;
    double yMove = 0;
    Random rdm1 = new Random();
    Random rdm2 = new Random();
 
    
    /**
     * Creates and instance of the Symbol class which sets a timer moving to 
     * induce animation.
     */
    public SymbolPanel() {
       Timer timer = new Timer(50, this);
       timer.start();
    } // SwingPanel()
    
    
    /**
     * Sets the speed instance variable to a new integer value.
     * 
     * @param i A factor by which to increase the random movement speed and 
     * rotation of shapes.
     */
    public void setSpeed(int i) {
        this.speed = i;
    }// setSpeed
    
    
    /**
     * Creates an AffineTransform object designed to scale a new shape to fit 
     * the window, and rotate the shape to a starting position.
     * 
     * Used for the pieces of the animation that are repeatedly made and do not
     * move aside from a rotation to an initial angle.
     * 
     * @param initialAngle The starting angle (radians) of the shape.
     * @return An AffineTransform object that will scale and rotate a shape.
     */
    public AffineTransform rotateToPlace(double initialAngle) {
        int w = this.getWidth();
        int h = this.getHeight();
        
        AffineTransform transform = new AffineTransform();
        AffineTransform scaling = new AffineTransform();
        scaling.setToScale(w / 2, h / 2);
        AffineTransform translation = new AffineTransform();
        translation.setToTranslation(1.0, 1.0);
        AffineTransform rotate = new AffineTransform();
        rotate.setToRotation(initialAngle, centerX, centerY);
        
        transform.concatenate(scaling);
        transform.concatenate(translation);
        transform.concatenate(rotate);
        
        return transform;
    }// rotateToPlace(double)
    
    
    /**
     * Creates an AffineTransform object designed to move, scale, and rotate 
     * an arrow to a starting position, as well as rotate the arrow to a 
     * new position based on spin movement.
     * 
     * Designed to be used with the arrow shapes that make up the main
     * SCP symbol which need to be moved to an initial angle and have a 
     * rotation animation. 
     * 
     * @param initialAngle The starting angle (radians) of the arrow.
     * @param newAngle The new angle (radians) of the arrow given rotation
     * @return An AffineTransform object which can be used to transform an
     * arrow to the correctly rotated position.
     */
    public AffineTransform spinArrows(double initialAngle, double newAngle) {

        AffineTransform transform = rotateToPlace(initialAngle);
        
        AffineTransform rotate = new AffineTransform();
        rotate.setToRotation(newAngle, centerX, centerY);
        transform.concatenate(rotate);
        
        return transform;
    }// spinArrows(double, double)
    
    /**
     * Creates an AffineTransform object designed to move, scale, and rotate 
     * a loading symbol line to its original position, as create the rotation
     * movement and random movement that occurs during the animation. 
     * 
     * Designed to facilitate the rotation and random movement of the 
     * loading screen symbol.
     * 
     * @param initialAngle The starting angle (radians) of the shape.
     * @param newAngle The angle (radians) by which the shape will rotate 
     * during animation.
     * @param xMovement The distance the shape moves on the x-axis from its 
     * starting position.
     * @param yMovement The distance the shape moves on the y-axis from its 
     * starting position.
     * @return An AffineTransform object which can be used to move a loading 
     * symbol line to a proper position given starting angle, rotation, and
     * movement.
     */
    public AffineTransform spinLoading(double initialAngle, double newAngle, 
                                        double xMovement, double yMovement) {
            int w = this.getWidth();
            int h = this.getHeight();
            
            AffineTransform transform = new AffineTransform();
            AffineTransform scaling = new AffineTransform();
            scaling.setToScale(w / 2, h / 2);
            AffineTransform translation = new AffineTransform();
            translation.setToTranslation(1.0, 1.0);
            AffineTransform move = new AffineTransform();
            move.translate(xMovement, yMovement);
            AffineTransform rotate = new AffineTransform();
            rotate.setToRotation(initialAngle + newAngle, 
                                    loadCenterX, loadCenterY);
            
            transform.concatenate(scaling);
            transform.concatenate(translation);
            transform.concatenate(move);
            transform.concatenate(rotate);
            
            return transform;
    }// spinLoading(double, double)
    
    
    /**
     * Creates a straight line shape object using instance variables defined
     * in this class.
     * 
     * Part of the rotating arrows which make up the main SCP symbol.
     * 
     * @param initialAngle The starting angle (radians) of the line.
     * @param newAngle The angle change (radians) of the line due to a rotation.
     * @return A straight line shape with parameters defined in this class, 
     * scaled to the window, and rotated based on an initial and rotated angle.
     */
    public Shape createArrow(double initialAngle, double newAngle) {
        AffineTransform transform = spinArrows(initialAngle, newAngle);
        
        Line2D.Double line = new Line2D.Double(origX, origY, origX2, origY2);
        Shape arrow = transform.createTransformedShape(line);
        
        return arrow;
    }// createArrow(double, double)
    
    
    /**
     * Creates a triangular arrowhead shape using instance variables defined 
     * in this class.
     * 
     * Part of the rotating arrows which make up the main SCP symbol.
     * 
     * @param initialAngle The starting angle (radians) of the arrowhead.
     * @param newAngle The angle change (radians) of the arrowhead due to 
     * a rotation.
     * @return A triangular arrowhead shape with parameters defined in this 
     * class, scaled to the window, and rotated based on an initial 
     * and rotated angle.
     */
    public Shape createArrowHead(double initialAngle, double newAngle) {
        AffineTransform transform = spinArrows(initialAngle, newAngle);
        
        Path2D.Double arrowhead = new Path2D.Double();
        arrowhead.moveTo(arpX, arpY);
        arrowhead.lineTo(arX, arY);
        arrowhead.lineTo(arX2, arY2);
        Shape arrowFin = transform.createTransformedShape(arrowhead);
        
        return arrowFin;
    }// createArrowHead(double, double)
    
    
    /**
     * Creates an arc using instance variables defined in this class. 
     * 
     * Part of the outer circle of the symbol.
     * 
     * @param angle The angle (radians) of the arc.
     * @return An arc shape with class defined parameters, scaled to the 
     * window, and set to a defined angle. 
     */
    public Shape createArc(double angle) {
        AffineTransform transform = rotateToPlace(angle);
        
        double d1 = 2 * this.radius2;
        double ulx1 = this.centerX2 - this.radius2;
        double uly1 = this.centerY2 - this.radius2;
        
        Arc2D.Double arc = new Arc2D.Double(ulx1, uly1, d1, d1, startAngle, 
                                            endAngle, Arc2D.OPEN);
        Shape arcFin = transform.createTransformedShape(arc);
        
        return arcFin;
    }// createArc(double)
    
    
    /**
     * Creates a hat shape object using instance variables 
     * defined in this class.
     * 
     * Part of the outer circle of the symbol and connects the outer arcs. 
     * The arrows are initially placed below these hats until they begin 
     * rotating.
     * 
     * @param angle The angle (radians) of the hat.
     * @return A hat shape object with class defined parameters, scaled to 
     * the window, and at a specified angle.
     */
    public Shape createHat(double angle) {
        AffineTransform transform = rotateToPlace(angle);
        
        Path2D.Double outerHat = new Path2D.Double();
        outerHat.moveTo(outerX1, outerY1);
        outerHat.lineTo(outerX2, outerY2);
        outerHat.lineTo(outerX3, outerY3);
        outerHat.lineTo(outerX4, outerY4);
        Shape outer = transform.createTransformedShape(outerHat);
        
        return outer;
    }// createHat(angle)
    
    
    /**
     * Paints the symbol and loading screen symbol shapes onto the window.
     * 
     * The painted components are white with various stroke sizes.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        
  
        //Color and stroke sizes used
        g2D.setColor(this.color);
        BasicStroke stroke = new BasicStroke(15f);
        g2D.setStroke(stroke);
        
        
        //Drawing the first circle
        AffineTransform transform = rotateToPlace(0);
        double d = 2 * this.radius;
        double ulx = this.centerX - this.radius;
        double uly = this.centerY - this.radius;
        Ellipse2D.Double circle = new Ellipse2D.Double(ulx, uly, d, d);
        Shape circle1 = transform.createTransformedShape(circle);
        g2D.draw(circle1);
        
        
        //Adjusting stroke size
        BasicStroke stroke2 = new BasicStroke(8f);
        g2D.setStroke(stroke2);
        
        
        //Drawing the arrows
        g2D.draw(createArrow(0, angleSymbol));
        g2D.draw(createArrow(ARROW_ANGLE, angleSymbol));
        g2D.draw(createArrow(-ARROW_ANGLE, angleSymbol));
        
        g2D.fill(createArrowHead(0, angleSymbol));
        g2D.fill(createArrowHead(ARROW_ANGLE, angleSymbol));
        g2D.fill(createArrowHead(-ARROW_ANGLE, angleSymbol));
        
        
        //Adjusting stroke size
        BasicStroke stroke3 = new BasicStroke(5f);
        g2D.setStroke(stroke3);
        
        
        //Drawing the outer circle
        g2D.draw(createArc(0));
        g2D.draw(createArc(ARROW_ANGLE));
        g2D.draw(createArc(-ARROW_ANGLE));
        
        g2D.draw(createHat(0));
        g2D.draw(createHat(ARROW_ANGLE));
        g2D.draw(createHat(-ARROW_ANGLE));
        
        
        //Loading sign and movement 
        for(int i = 0; i < loadLines; i++) {
            double startingAngle = i * ((2 * Math.PI) / loadLines);
            
            Line2D.Double newLine = new Line2D.Double(loadStartX, loadStartY, 
                                        loadEndX, loadEndY);
            
            AffineTransform transformLoad = spinLoading(startingAngle, angleLoad, 
                                            xMove, yMove);
            
            Shape newLineFin = transformLoad.createTransformedShape(newLine);
            
            g2D.draw(newLineFin);
        }// for
    
    } // paintComponent( Graphics )

    
    /**
     * Creates changes in the position and angle of the shapes to create 
     * animation based on a timer and redraws the panel. 
     * 
     * Uses instance variables to increase the angles of rotation for the 
     * arrows and the loading screen symbol. Also checks to see if the center 
     * of the loading screen symbol is on the panel, then either moves it back
     * to being on the panel or randomly moves it to a new set of positions. 
     * All of the calculated angles are multiplied by a speed factor which can
     * be changed from the menu located in the LoadingScreen class. 
     * 
     * @param event An event trigger caused by a timer.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        //Symbol Rotate
        this.angleSymbol = this.angleSymbol + this.speed * this.moveSymbol;
        if(this.angleSymbol > (2 * Math.PI)) {
            this.angleSymbol = this.angleSymbol - 2 * Math.PI;
        }// if
        
        //Loading Rotate
        this.angleLoad = this.angleLoad + this.speed * this.moveLoad;
        if(this.angleLoad > (2 * Math.PI)) {
            this.angleLoad = this.angleLoad - 2 * Math.PI;
        }// if
        
        //Loading random x-axis movement
        if(this.xMove > 1) {
            this.xMove = this.xMove + this.speed * 
                (( - 0.05));
        }// if
        else if((this.xMove < -1)) {
            this.xMove = this.xMove + this.speed *
                    ((0.05));
        }// else if
        else {
            this.xMove = this.xMove + this.speed * 
                (rdm1.nextDouble() * 0.1 - 0.05);
        }// else
        
        //Loading random y-axis movement        
        if(this.yMove > 1) {
            this.yMove = this.yMove + this.speed * 
                (( - 0.05));
        }// if
        else if((this.yMove < -1)) {
            this.yMove = this.yMove + this.speed *
                    ((0.05));
        }// else if
        else {
            this.yMove = this.yMove + this.speed * 
                (rdm2.nextDouble() * 0.1 - 0.05);
        }// else

        //Redraw panel
        this.repaint();
    } // actionPerformed( ActionEvent )

    
}// SymbolPanel
