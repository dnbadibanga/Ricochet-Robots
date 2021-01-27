//package com.group2.physicalgameobjects;

import javax.swing.*;

/**
 * This class creates one of the 17 target chips. It has a color and shape, and the coordinates of the square that
 * will share the same icon.
 */
public class TargetChip {

    private String color;
    private String shape;
    private int relatedGridSquaresRowCoord;
    private int relatedGridSquaresColumnCoord;
    private ImageIcon imageIcon;

    public TargetChip(String newColor, String newShape, ImageIcon newImageIcon, int newRelatedGridSquaresRowCoord, int newRelatedGridSquaresColumnCoord){ //TODO I updated signature compared to class diagram.
        this.color = newColor;
        this.shape = newShape;
        //this.location = new Location(-1, -1);
        this.imageIcon = newImageIcon;
        this.relatedGridSquaresRowCoord = newRelatedGridSquaresRowCoord;
        this.relatedGridSquaresColumnCoord = newRelatedGridSquaresColumnCoord;
    }

    public ImageIcon getImageIcon(){
        return this.imageIcon;
    }

    public void setImageIcon(ImageIcon newImageIcon){
        this.imageIcon = newImageIcon;
    }

    public String getColor(){
        return this.color;
    }

    public void setColor(String newColor){
        this.color = newColor;
    }

    public String getShape(){
        return this.shape;
    }

    public void setShape(String newShape){
        this.shape = newShape;
    }

    public int getRelatedGridSquaresRowCoord(){
        return this.relatedGridSquaresRowCoord;
    }

    public int getRelatedGridSquaresColumnCoord(){
        return this.relatedGridSquaresColumnCoord;
    }
/*
    public String getImageFileLocation(){

    }

    public setImageFileLocation(String imageFileLocation){

    }

*/

}
