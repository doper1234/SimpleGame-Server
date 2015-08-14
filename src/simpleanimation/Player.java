/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpleanimation;

import java.awt.*;



public class Player {
  
    
    int x,y;
    int pWidth = 50;
    int pHeight = 50;
    Color playerColor;
    
    public Player(int x, int y, Color pC){
        this.x = x;
        this.y = y;
        playerColor = pC;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public void drawPlayer(Graphics g){
        g.setColor(playerColor);
        g.fillRect(x, y, pWidth, pHeight);
    }
    
    public void movePlayerLeft(int boundsX){
        if(x >= boundsX){
            x = x - 10;
        }
        else{
            x = boundsX;
        }
    }
    public void movePlayerRight(int boundsX){
        if(x + pWidth < boundsX){
           x = x + 10; 
        }
        else{
            //x = boundsX - pWidth;
        }
    }
    public void movePlayerUp(int boundsY){
        if(y >= boundsY){
            y = y - 10;
        }
        else{
            y = boundsY;
        }
    }
    public void movePlayerDown(int boundsY){
        if(y + pHeight < boundsY){
           y = y + 10; 
        }
        else{
            //y = boundsY - pHeight;
        }
    }

    
    public Color getPlayerColor() {
        return playerColor;
    }
    
    
    
   
}
