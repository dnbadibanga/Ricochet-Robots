//package com.group2.gui;

public class GameSettings {
    private String boardType;
    private String difficulty;
    private String player1PlayerType;
    private String player2PlayerType;
    private String player3PlayerType;
    private String player4PlayerType;
    private Player playerOne, playerTwo, playerThree, playerFour;

    public GameSettings(){
        //These are the default settings.
        this.boardType = "SIMPLE";
        this.difficulty = "EASY";
        this.player1PlayerType = "HUMAN";
        this.player2PlayerType = "HUMAN";
        this.player3PlayerType = "HUMAN";
        this.player4PlayerType = "HUMAN";
    }

    /*public Player createAndReturnPlayer1(){
        playerOne = new Player();

        if (player1PlayerType.equals("HUMAN")){
            playerOne.setPlayerTypeToHuman();
        }
        else {
            playerOne.setPlayerTypeToComputer();
        }


    }

    public Player createAndReturnPlayer2(){

    }

    public Player createAndReturnPlayer3(){

    }

    public Player createAndReturnPlayer4(){

    }
*/
    public void setSimpleBoardType(){
        this.boardType = "SIMPLE";
    }

    public void setComplexBoardType(){
        this.boardType = "COMPLEX";
    }


    public void setEasyDifficulty(){
        this.difficulty = "EASY";
    }

    public void setHardDifficulty(){
        this.difficulty = "HARD";
    }

    public void setPlayer2PlayerType(String newPlayerType){
        this.player2PlayerType = newPlayerType;
    }

    public void setPlayer3PlayerType(String newPlayerType){
        this.player3PlayerType = newPlayerType;
    }

    public void setPlayer4PlayerType(String newPlayerType){
        this.player4PlayerType = newPlayerType;
    }

    /*

    //Getters //TODO Change integers to strings reading what board types and difficulties are.

    public boolean recordedBoardTypeIsSimple(){
        if (this.boardType == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean recordedAllComputerDifficultyIsEasy(){
        if (this.difficulty == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean player1PlayerTypeisHuman(){
        if (this.player1PlayerType == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean player2PlayerTypeisHuman(){
        if (this.player2PlayerType == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean player3PlayerTypeisHuman(){
        if (this.player3PlayerType == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean player4PlayerTypeisHuman(){
        if (this.player4PlayerType == 0){
            return true;
        }
        else{
            return false;
        }
    } */

}
