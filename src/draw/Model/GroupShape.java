/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draw.Model;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

/**
 *
 * @author Nikito
 */
public class GroupShape extends draw.Model.Shape {
    
    public GroupShape(Rectangle rect){
        super(rect);
    }
    
    public GroupShape(draw.Model.RectangleShape rectangle){
        
    }
    
    private Vector<draw.Model.Shape> subItem = new Vector<draw.Model.Shape>();
    
    public Vector<draw.Model.Shape> getSubItem(){
        return subItem;
    }
    
    public void setSubItem(Vector<draw.Model.Shape> value){
        subItem = value;
    }
    
    @Override
    public boolean Contains(int x, int y){
        if(super.Contains(x, y)){
            for(draw.Model.Shape item : subItem){
                if(item.Contains(x, y)){
                    return true;
                }else{
                    return false;
                }
            }
            return true;
        }else return false;
    }
    
    @Override
    public void DrawSelf(Graphics grfx){
        super.DrawSelf(grfx);
        for(draw.Model.Shape item : subItem){
            item.DrawSelf(grfx);
        }
    }
    
    @Override
    public void setLocation(Point value){
        super.setLocation(value);
        for(draw.Model.Shape item : subItem){
            item.setLocation(value);
        }
    }
    
    @Override
    public void setFillColor(java.awt.Color value){
        for(draw.Model.Shape item : subItem){
            item.setFillColor(value);
        }
    }
}
