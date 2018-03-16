/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;


/**
 *
 * @author Nikito
 */
public class EllipseShape extends draw.Model.Shape{
    
    private Ellipse2D.Double ellipse;
    
    public EllipseShape(Rectangle rect){
        super(rect);
    }

    public EllipseShape(draw.Model.EllipseShape ellip){
        
    }
    
    @Override
    public boolean Contains(int x, int y) {
        if (ellipse.contains((double) x, (double) y)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    @Override
    public void DrawSelf(Graphics grfx){
        super.DrawSelf(grfx);
        Rectangle r = getRectangle();
        grfx.setColor(getFillColor());
        grfx.fillOval(r.x,r.y, r.width, r.height);
        grfx.setColor(Color.BLACK);
        grfx.drawOval(r.x,r.y, r.width, r.height);
        ellipse = new Ellipse2D.Double((double) r.x, (double) r.y, (double) r.width, (double) r.height);
    }
}
