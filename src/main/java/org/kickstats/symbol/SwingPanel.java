package org.kickstats.symbol;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.QuadCurve2D;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * A support class for the Swing class.
 * 
 * Draws the animation based on a timer.
 * 
 * @author Leon Tabak, edited by Chase Sonnemaker
 * @version 5 April 2020
 */
public class SwingPanel extends JPanel implements ActionListener {

    private double ctr1x1 = 0;
    private double ctr1x2 = 0;
    private double ctr1x3 = 0;
    private double ctr1x4 = 0;
    private double fake = 0;
    private double ctr1y1 = 0;
    private double ctr1y2 = 0;
    private double ctr1y3 = 0;
    private double ctr1y4 = 0;
    private double x1 = 0.4;
    private double x2 = -0.4;
    private double y1 = 0.4;
    private double y2 = -0.4;
    private double factorChange = 0.05;
    private double angle = 0;
    private double max = 0.8;
    
    private Color color = Color.red;

    public SwingPanel() {
        Timer timer = new Timer(50, this);
        timer.start();
    } // SwingPanel()

    public Color getColor() {
        return this.color;
    } // getColor()

    public void setColor(Color c) {
        this.color = c;
    } // setColor( Color )
    
    public void setMaximum(double max) {
        this.max = max;
    }// setMaximum(double)

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        int w = this.getWidth();
        int h = this.getHeight();

        AffineTransform transform = new AffineTransform();
        AffineTransform scaling = new AffineTransform();
        scaling.setToScale(w / 2, h / 2);
        AffineTransform translation = new AffineTransform();
        translation.setToTranslation(1.0, 1.0);
        AffineTransform rotate = new AffineTransform();
        rotate.setToRotation(angle, 0, 0);

        transform.concatenate(scaling);
        transform.concatenate(translation);
        transform.concatenate(rotate);

        // Replace this block of code that creates
        // an ellipse with your own code that draws
        // something else
        // Make sure that all geometry fits in a square
        // whose corners are (-1, -1) and (+1, +1)
        this.angle = this.angle + 0.1;
        ctr1y1 = ctr1y1 + factorChange;
        ctr1y3 = ctr1y3 - factorChange;
        ctr1x2 = ctr1x2 + factorChange;
        ctr1x4 = ctr1x4 - factorChange;
        QuadCurve2D.Double curve1 = new QuadCurve2D.Double(x1, y2, ctr1x1,
        ctr1y1, x2, y2);
        
        QuadCurve2D.Double curve2 = new QuadCurve2D.Double(x2, y2, ctr1x2,
        ctr1y2, x2, y1);
        
        QuadCurve2D.Double curve3 = new QuadCurve2D.Double(x2, y1, ctr1x3,
        ctr1y3, x1, y1);
        
        QuadCurve2D.Double curve4 = new QuadCurve2D.Double(x1, y1, ctr1x4,
        ctr1y4, x1, y2);

        Shape shape1 = transform.createTransformedShape(curve1);
        Shape shape2 = transform.createTransformedShape(curve2);
        Shape shape3 = transform.createTransformedShape(curve3);
        Shape shape4 = transform.createTransformedShape(curve4);
        g2D.setColor(this.getColor());
        
        BasicStroke stroke = new BasicStroke(10f);
        
        g2D.setStroke(stroke);
        g2D.draw(shape1);
        g2D.draw(shape2);
        g2D.draw(shape3);
        g2D.draw(shape4);
    } // paintComponent( Graphics )

    @Override
    public void actionPerformed(ActionEvent event) {
        // You might also like to try what happens
        // in each step of the animation
        // Move? In which direction? How much?
        // Make bigger? Or make smaller?
        // Rotate? (There's an AffineTransform for that, too.)
        // Change color?

        this.fake = this.fake - this.factorChange;
        if (this.fake > max ) {
            this.factorChange = -this.factorChange;
        } // if
        else if (this.fake < 0) {
            this.factorChange = -this.factorChange;
        } // else if

        this.repaint();
    } // actionPerformed( ActionEvent )

} // SwingPanel
