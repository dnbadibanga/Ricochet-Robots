//package com.group2.physicalgameobjects;

//import com.group2.physicalgameobjects.Color.*;
//import com.group2.physicalgameobjects.Symbols.*;

/**
 * This class creates a target tile found on either a simple or complex board. It has a color and a symbol.
 * The colors are "RED", "GREEN", "BLUE", "YELLOW", and the symbols are "GEAR", "SATURN", "CRESCENTMOON", "STAR", 
 * and "VORTEX".
 */

public class TargetTile {
    private String symbol;
    private String color;

    public TargetTile(String newColor, String newSymbol){
        this.color = newColor;
        this.symbol = newSymbol;
    }


    public String getColor(){
        return this.color;
    }

    public String getSymbol(){
        return this.symbol;
    }

//TODO May not need below
    public void addGearSymbol(){
        this.symbol = "GEAR";
        //this.symbol = new GearSymbol();
    }

    public void addSaturnSymbol(){
        this.symbol = "SATURN";
        //this.symbol = new SaturnSymbol();
    }

    public void addCrescentMoonSymbol(){
        this.symbol = "CRESCENTMOON";
    }

    public void addStarSymbol(){
        this.symbol = "STAR";
    }

    public void addRedColor(){
        this.color = "RED";
    }

    public void addGreenColor(){
        this.color = "GREEN";
    }

    public void addBlueColor(){
        this.color = "BLUE";
    }

    public void addYellowColor(){
        this.color = "YELLOW";
    }

    public void addVortexSymbol(){
        this.symbol = "VORTEX";
    }
/*
    public String getImageFileLocation(){

    }
    */
/*
    public setImageFileLocation(String imageFileLocation){

    }
*/



}
