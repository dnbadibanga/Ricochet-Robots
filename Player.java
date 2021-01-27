//package com.group2.physicalgameobjects;

//import com.group2.javaCode.Robot;

import java.util.ArrayList;

/**
 * This class represents the physical Player of the game. This player can be either Human or Computer. They
 * have a name, a robot, and an ArrayList of token chips that they will collect if they win.
 */

public class Player {
//INSTANCE VARIABLES

    private String playerType;
    private String playerName;


    private int playerID;
    private int tokenCount;
    private RobotPieces playersRobot;
    private int bidNumber;
    private int bidOrder;
    private int numberOfChipsWon;
    private ArrayList<TargetChip> playersCollectedTargetChips;



    //ArrayList<> //TODO Set up list of movements later

    /**
     * Default settings when made is the following. (Nothing, the Player needs to be explicitly made using the methods).
     */

//CONSTRUCTOR
public Player(){
    this.playerName = "";
    this.playerType = "";
    this.tokenCount = 0;
    this.playersRobot = null;
    this.playerID = -1;
    this.playersCollectedTargetChips = new ArrayList<TargetChip>();
    //this.location = new Location(-1, -1);
    this.bidNumber = 100000;
    this.bidOrder = 100000;
    this.numberOfChipsWon = 0;
}
//METHODS

    /**
     * This will returns the player's type as a String ("HUMAN" or "COMPUTER").
     * @return
     */
    public String getPlayerType(){
        return this.playerType;
    }

    /*public void setPlayerType(String newPlayerType){
        this.playerType = newPlayerType;
    }*/

    /**
     * This sets the player's type to human "HUMAN".
     */

    public void setPlayerTypeToHuman(){
        this.playerType = "HUMAN";
    }
    /**
     * This sets the player's type to computer "COMPUTER".
     */

    public void setPlayerTypeToComputer(){
        this.playerType = "COMPUTER";
    }

    /**
     * This returns the player's name as a string.
     * @return
     */

    public String getPlayerName(){
        return this.playerName;
    }

    /**
     * This sets the player's name to the string variable provided to method.
     * @param newPlayerName
     */

    public void setPlayerName(String newPlayerName){
        this.playerName = newPlayerName;
    }

    /**
     * Not finished.
     */
    /*

    public takeTurn(){

    }
*/


    /**
     * This method gives the player their particular robot.
     * @param playersNewRobot
     */
    public void setPlayersRobot(RobotPieces playersNewRobot){
        this.playersRobot = playersNewRobot;
    }

    /**
     * This method will return the player's robot.
     * @return
     */

    public RobotPieces getPlayersRobot(){
        return this.playersRobot;
    }

    /**
     * This method will give the player a target chip.
     * @param wonTargetChip
     */

    public void addTargetChipToPlayersCollection(TargetChip wonTargetChip){
        this.playersCollectedTargetChips.add(wonTargetChip);
        this.numberOfChipsWon ++;
    }

    /**
     * This method will return as an integer the number of tokens the player has won.
     * @return
     */

    public int getNumberOfTargetChipsPlayerHasWon(){
        return this.numberOfChipsWon;
    }

/*
    public String getImageFileLocation(){

    }
    */
/*
    public setImageFileLocation(String imageFileLocation){

    }
*/

    public int getBidNumber(){
        return this.bidNumber;
    }

    public void setBidNumber(int newBidNumber){
        this.bidNumber = newBidNumber;
    }

    public void setBidOrder(int newBidOrder){
        this.bidOrder = newBidOrder;
    }

    public int getBidOrder(){
        return this.bidOrder;
    }









/* //Leaving out for now
    public Robot getRobot(){
        return this.playersRobot;
    }

    public void changeRobot(Robot newRobot){
        this.playersRobot = newRobot;
    }



    public void changePlayerID( int newID){
        this.playerID = newID;
    }


    public void addOneTokenToCount(){
        this.tokenCount += 1;
    }
*/
}