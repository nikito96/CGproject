/**
 * RectangleShape.java
 */

package draw.Model;

import java.awt.*;

/**
 * Класът правоъгълник е основен примитив, който е наследник на базовия Shape.
 */
public class RectangleShape extends draw.Model.Shape {
    
    public RectangleShape(Rectangle rect) {
        super(rect);
    }

    public RectangleShape(draw.Model.RectangleShape rectangle) {
    }

    /**
     * Проверка за принадлежност на точка point към правоъгълника.
     * В случая на правоъгълник този метод може да не бъде пренаписван, защото
     * Реализацията съвпада с тази на абстрактния клас Shape, който проверява
     * дали точката е в обхващащия правоъгълник на елемента (а той съвпада с
     * елемента в този случай).
     */
    @Override
    public boolean Contains(int x, int y) {
        if ( super.Contains(x, y)) {
            //  Проверка дали е в обекта само, ако точката е в обхващащия правоъгълник.
            //  В случая на правоъгълник - директно връщаме true
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Частта, визуализираща конкретния примитив.
     */
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
