/**
 * Shape.java
 */

package draw.Model;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 *   Базовия клас на примитивите, който съдържа общите характеристики на примитивите.
 */
public abstract class Shape {
    /**
     *   Обхващащ правоъгълник на елемента.
     */
    private Rectangle rectangle;
    
    /**
     *   Цвят на елемента.
     */
    private Color fillColor;

    public Shape() {
    }

    public Shape(Rectangle rect) {
        rectangle = rect;
    }
    
    public Shape(draw.Model.Shape shape) {
        this.setHeight(shape.getHeight());
        this.setLocation(shape.getLocation());
        this.rectangle = shape.rectangle;
        this.setWidth(shape.getWidth());
        this.setFillColor(shape.getFillColor());
    }

    /**
     * Проверка дали точка point принадлежи на елемента.
     * @param point - Точка.
     * @return Връща true, ако точката принадлежи на елемента и false, ако не пренадлежи.
     */
    public boolean Contains(int x, int y) {
            return getRectangle().contains(x, y);
    }
    
    /**
     * Визуализира елемента.
     * @param grfx - Къде да бъде визуализиран елемента.
     */
    public void DrawSelf(Graphics grfx) {
        //  shape.Rectangle.Inflate(shape.BorderWidth, shape.BorderWidth)
    }
    
    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle value) {
        rectangle = value;
    }
    
    public int getWidth() {
        return this.getRectangle().width;
    }

    public void setWidth(int value) {
        rectangle.width = value;
    }
    
    public int getHeight() {
        return this.getRectangle().height;
    }

    public void setHeight(int value) {
        rectangle.height = value;
    }
    
    public Point getLocation() {
        return this.getRectangle().getLocation();
    }

    public void setLocation(Point value) {
        rectangle.setLocation(value);
    }

    public java.awt.Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(java.awt.Color value) {
        fillColor = value;
    }
}
