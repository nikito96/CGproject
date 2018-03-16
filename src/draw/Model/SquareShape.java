/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Nikito
 */
public class SquareShape extends draw.Model.Shape {
    
    public SquareShape(Rectangle rect) {
        super(rect);
    }

    public SquareShape(draw.Model.RectangleShape square) {
    }

    @Override
    public boolean Contains(int x, int y) {
        if ( super.Contains(x, y) ) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void DrawSelf(Graphics grfx) {
        super.DrawSelf(grfx);
        Rectangle r = getRectangle();
        grfx.setColor(getFillColor());
        grfx.fillRect(r.x, r.y, r.width, r.height);
        grfx.setColor(Color.BLACK);
        grfx.drawRect(r.x, r.y, r.width, r.height);
    }
}
