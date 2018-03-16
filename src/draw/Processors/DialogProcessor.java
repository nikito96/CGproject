/**
 * DialogProcessor.java
 */

package draw.Processors;

import draw.Model.GroupShape;
import java.awt.*;
import java.util.Vector;


/**
 * Класът, който ще бъде използван при управляване на диалога.
 */
public class DialogProcessor extends DisplayProcessor {
    /**
     * Избран елемент.
     */
    private Vector<draw.Model.Shape> selection = new Vector<draw.Model.Shape>();
    
    // Не е необходим в реализацията на Java
    ///**
    // * Дали в момента диалога е в състояние на "влачене" на избрания елемент.
    // */
    //private boolean isDragging;
    
    /**
     * Последна позиция на мишката при "влачене".
     * Използва се за определяне на вектора на транслация.
     */
    private Point lastLocation;
    public Color c = java.awt.Color.WHITE;

    public DialogProcessor() {
    }

    /**
     * Добавя примитив - правоъгълник на произволно място върху клиентската област.
     */
    public void AddRandomRectangle() {
        int x = 100 + (int)Math.round(Math.random()*900);
        int y = 100 + (int)Math.round(Math.random()*500);
        draw.Model.RectangleShape rect = new draw.Model.RectangleShape(new Rectangle(x,y,100,200));
        rect.setFillColor(c);
        shapeList.add(rect);
    }
    
    public void AddRandomSquare(){
        int x = 100 + (int)Math.round(Math.random()*900);
        int y = 100 + (int)Math.round(Math.random()*500);
        draw.Model.RectangleShape sq = new draw.Model.RectangleShape(new Rectangle(x,y,100,100));
        sq.setFillColor(c);
        shapeList.add(sq);
    }
    
        public void AddRandomEllipse(){
        int cx = 100 + (int)Math.round(Math.random()*900);
        int cy = 100 + (int)Math.round(Math.random()*500);
        draw.Model.EllipseShape ellip = new draw.Model.EllipseShape(new Rectangle(cx, cy, 200, 100));
        ellip.setFillColor(c);
        shapeList.add(ellip);
    }
        
    public void deleteShape(){
        for(draw.Model.Shape item : selection){
            shapeList.remove(item);
        }
    }
    
    /**
     * Проверява дали дадена точка е в елемента.
     * Обхожда в ред обратен на визуализацията с цел намиране на
     * "най-горния" елемент т.е. този който виждаме под мишката.
     * @param point - Указана точка.
     * @return Елемента на изображението, на който принадлежи дадената точка.
     */
    public draw.Model.Shape ContainsPoint(int x, int y) {
        for (int i = shapeList.size() - 1; i >= 0; i--) {
            if (shapeList.get(i).Contains(x, y)) {
                return shapeList.get(i);
            }
        }
        return null;
    }
    
    /**
     * Транслация на избраният елемент на вектор определен от <paramref name="p>p< paramref>
     * @param p - Вектор на транслация.
     */
    public void TranslateTo(Point p) {
        if (selection != null) {
            for(draw.Model.Shape item : selection){
                item.setLocation(new Point(item.getLocation().x + p.x - lastLocation.x, item.getLocation().y + p.y - lastLocation.y));
            }
            lastLocation = p;
        }
    }
    
    //
    
    public Vector<draw.Model.Shape> getSelection() {
        return selection;
    }

    public void setSelection(Vector<draw.Model.Shape> value) {
        selection = value;
    }

    //public boolean getIsDragging() {
    //    return isDragging;
    //}

    //public void setIsDragging(boolean value) {
    //    isDragging = value;
    //}
    
    public Point getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(int x, int y) {
        lastLocation = new Point(x, y);
    }
    
    public void setFillColor(Color color){
        for (draw.Model.Shape item: selection){
            item.setFillColor(color);
        }
    }
    
    public void GroupSelected(){
        if (selection.capacity() <2){
            return;
        }else{
            float minX = Float.POSITIVE_INFINITY, minY = Float.POSITIVE_INFINITY;
            float maxX = Float.NEGATIVE_INFINITY, maxY = Float.NEGATIVE_INFINITY;
            
            for (draw.Model.Shape item : selection){
                if (minX > item.getLocation().x){minX = item.getLocation().x;}
                if (minY < item.getLocation().y){minY = item.getLocation().y;}
                if (minX < item.getLocation().x){minX = item.getLocation().x + item.getWidth();}
                if (minY < item.getLocation().y){minY = item.getLocation().y + item.getHeight();}
            }
            GroupShape group = new GroupShape(new Rectangle((int) minX, (int) minY, (int) (maxX-minX),(int) (maxY-minY)));
            
            group.setSubItem(selection);
            
            for(draw.Model.Shape item : selection){
                shapeList.remove(item);
            }
            
            selection = new Vector<draw.Model.Shape>();
            selection.add(group);
            shapeList.add(group);
        }
    }
}
