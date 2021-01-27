//package com.group2.gameplayrules;


//import com.group2.javaCode.Robot;
//import com.group2.physicalgameobjects.*;

/**
 * This class deals with the bidding process. It is tasked with determining if players make a legal and valid moves in both simple and complex boards.
 */
public class VerifyBidProcessor {

    int bidNumber;
    GridSquare[] listOfSquaresMovedInOrder;
    RobotPieces robotToMove;
    GridSquare squareWithDesiredTargetTile;
    GridSquare[][] gameboardWithGridSquares;

    public VerifyBidProcessor(int newBidNumber, GridSquare[] newListOfSquaresMovedInOrder, RobotPieces newRobotToMove, GridSquare newSquareWithDesiredTargetTile, GridSquare[][] newGameboardWithGridSquares) {
        this.bidNumber = newBidNumber;
        this.listOfSquaresMovedInOrder = newListOfSquaresMovedInOrder;
        this.robotToMove = newRobotToMove;
        this.squareWithDesiredTargetTile = newSquareWithDesiredTargetTile;
        this.gameboardWithGridSquares = newGameboardWithGridSquares;


    }

    public boolean wasThisInBetweenMovesLegal(){
        //Did they click on a robot first?
        if (doesPlayerClickARobotFirst() == false) {
            return false;
        }

        //Going through the list for every pair of adjacent gridsquares:
        GridSquare currentSquare = this.listOfSquaresMovedInOrder[0];
        int currentSquareIndex = 0;
        GridSquare nextSquare = this.listOfSquaresMovedInOrder[0 + 1];
        int nextSquareIndex = 0 + 1;
        String currentMovingDirection = returnCurrentDirection(currentSquare, nextSquare);

        //String futureDirection = returnCurrentDirection(this.listOfSquaresMovedInOrder[0 + 1], this.listOfSquaresMovedInOrder[0 + 1 + 1]);//TODO ADDED

        for (int i = 0; i < (this.listOfSquaresMovedInOrder.length - 1); i++) {

            currentSquare = this.listOfSquaresMovedInOrder[i];
            currentSquareIndex = i;
            nextSquare = this.listOfSquaresMovedInOrder[i + 1];
            nextSquareIndex = i + 1;
            currentMovingDirection = returnCurrentDirection(currentSquare, nextSquare);

            //futureDirection = returnCurrentDirection(this.listOfSquaresMovedInOrder[i + 1], this.listOfSquaresMovedInOrder[i + 1 + 1]);//TODO ADDED

            if (doesPlayerMoveThroughABlackBarrier(currentSquare, nextSquare) == true) {
                return false;
            } else if (isThereARobotOnNextSquare(nextSquare) == true) {
                return false;
            } else if (isTheNextSquareTheLastSquareClicked(nextSquare) == true) {//Reworded
                if (isTheNextSquareTheLastSquareClicked(nextSquare) == true) {
                    if (doesNextSquareHaveBarrierToStopCurrentDirection(currentMovingDirection, nextSquare) == false) {
                        if (isThereARobotToBlockIntendedDirectionAfterNextSquare(currentMovingDirection, nextSquare ) == false) { //TODO REVERT BACK //Fix this.
                            return false;
                        } else {
                            return true;
                        }
                    }
                    else{
                        return true;
                    }
                }
            } else if (doesNextSquareHaveBarrierToStopCurrentDirection(currentMovingDirection, nextSquare) == true) {
                if (willPlayerLaterIntendToMovePerpendicularAfterReachingNextSquare(currentSquareIndex, nextSquareIndex) == false) {
                    return false;
                }
            } else if (isThereARobotToBlockIntendedDirectionAfterNextSquare(currentMovingDirection, nextSquare) == true) { //TODO REVERT BACK
                if (willPlayerLaterIntendToMovePerpendicularAfterReachingNextSquare(currentSquareIndex, nextSquareIndex) == false) {
                    return false;
                }
            }
        }
        return false; //TODO CHeck this

    }




    /**
     * This method returns true if the order of squares the player clicked were valid (legal), and that they actually reached and stopped
     * at the target square (accurate).
     */

    public boolean wereMovesLegalAndAccurate() {

        if (doesPlayerClickARobotFirst() == false) {
            return false;
        } else if (doesPlayerClickTheSquareWithTargetTileLast() == false) {
            return false;
        }

        //Going through the list for every pair of adjacent gridsquares:
        GridSquare currentSquare = this.listOfSquaresMovedInOrder[0];
        int currentSquareIndex = 0;
        GridSquare nextSquare = this.listOfSquaresMovedInOrder[0 + 1];
        int nextSquareIndex = 0 + 1;
        String currentMovingDirection = returnCurrentDirection(currentSquare, nextSquare);
        for (int i = 0; i < (this.listOfSquaresMovedInOrder.length - 1); i++) {

            currentSquare = this.listOfSquaresMovedInOrder[i];
            currentSquareIndex = i;
            nextSquare = this.listOfSquaresMovedInOrder[i + 1];
            nextSquareIndex = i + 1;
            currentMovingDirection = returnCurrentDirection(currentSquare, nextSquare);

            if (doesPlayerMoveThroughABlackBarrier(currentSquare, nextSquare) == true) {
                return false;
            } else if (isThereARobotOnNextSquare(nextSquare) == true) {
                return false;
            } else if (doesTheNextSquareHaveTheTargetTileToReach(nextSquare) == true) {
                if (isTheNextSquareTheLastSquareClicked(nextSquare) == true) {
                    if (doesNextSquareHaveBarrierToStopCurrentDirection(currentMovingDirection, nextSquare) == false) {
                        if (isThereARobotToBlockIntendedDirectionAfterNextSquare(currentMovingDirection, nextSquare) == false) {//Fix this. //TODO CHANGED HERE
                            return false;
                        } else {
                            int numberOfMoves = countNumberOfMovesOnSimpleBoard();
                            if (numberOfMoves <= 1) {
                                return false;
                            } else {
                                if (numberOfMoves == bidNumber) {
                                    return true;
                                }
                                else{
                                    return false;
                                }
                            }
                        }
                    }
                    else{
                        int numberOfMoves = countNumberOfMovesOnSimpleBoard();
                        if (numberOfMoves <= 1) {
                            return false;
                        } else {
                            if (numberOfMoves == bidNumber) {
                                return true;
                            }
                            else{
                                return false;
                            }
                        }
                    }
                }
            } else if (doesNextSquareHaveBarrierToStopCurrentDirection(currentMovingDirection, nextSquare) == true) {
                if (willPlayerLaterIntendToMovePerpendicularAfterReachingNextSquare(currentSquareIndex, nextSquareIndex) == false) {
                    return false;
                }
            } else if (isThereARobotToBlockIntendedDirectionAfterNextSquare(currentMovingDirection, nextSquare) == true) { //changed here
                if (willPlayerLaterIntendToMovePerpendicularAfterReachingNextSquare(currentSquareIndex, nextSquareIndex) == false) {
                    return false;
                }
            }
        }
    return false;

    }



    public boolean wereMovesLegalAndAccurateForComplexBoard() {

        if (doesPlayerClickARobotFirst() == false) {
            return false;
        } else if (doesPlayerClickTheSquareWithTargetTileLast() == false) {
            return false;
        }

        //Going through the list for every pair of adjacent gridsquares:
        GridSquare currentSquare = this.listOfSquaresMovedInOrder[0];
        int currentSquareIndex = 0;
        GridSquare nextSquare = this.listOfSquaresMovedInOrder[0 + 1];
        int nextSquareIndex = 0 + 1;
        String currentMovingDirection = returnCurrentDirection(currentSquare, nextSquare);

        //String futureDirection = returnCurrentDirection(this.listOfSquaresMovedInOrder[0 + 1], this.listOfSquaresMovedInOrder[0 + 1 + 1]);//TODO ADDED

        for (int i = 0; i < (this.listOfSquaresMovedInOrder.length - 1); i++) {

            currentSquare = this.listOfSquaresMovedInOrder[i];
            currentSquareIndex = i;
            nextSquare = this.listOfSquaresMovedInOrder[i + 1];
            nextSquareIndex = i + 1;
            currentMovingDirection = returnCurrentDirection(currentSquare, nextSquare);

            //futureDirection = returnCurrentDirection(this.listOfSquaresMovedInOrder[i + 1], this.listOfSquaresMovedInOrder[i + 1 + 1]);//TODO ADDED

            if (doesPlayerMoveThroughABlackBarrier(currentSquare, nextSquare) == true) {
                return false;
            } else if (isThereARobotOnNextSquare(nextSquare) == true) {
                return false;
            } else if (doesTheNextSquareHaveTheTargetTileToReach(nextSquare) == true) {
                if (isTheNextSquareTheLastSquareClicked(nextSquare) == true) {
                    if (doesNextSquareHaveBarrierToStopCurrentDirection(currentMovingDirection, nextSquare) == false) {
                        if (isThereARobotToBlockIntendedDirectionAfterNextSquare(currentMovingDirection, nextSquare) == false) {//Fix this.
                            return false;
                        } else {
                            int numberOfMoves = countNumberOfMovesOnComplexBoard();
                            if (numberOfMoves <= 1) {
                                return false;
                            } else {
                                if (numberOfMoves == bidNumber) {
                                    return true;
                                }
                                else{
                                    return false;
                                }
                            }
                        }
                    }
                    else{
                        int numberOfMoves = countNumberOfMovesOnComplexBoard();
                        if (numberOfMoves <= 1) {
                            return false;
                        } else {
                            if (numberOfMoves == bidNumber) {
                                return true;
                            }
                            else{
                                return false;
                            }
                        }
                    }
                }
            }


            else if (isThereADiagonalBarrierOnNextSquare(nextSquare) == true){
                if (didPlayerGoInRightDirectionWithDiagonalBarrier(currentSquareIndex, nextSquareIndex, this.listOfSquaresMovedInOrder[0].getRobotOnSquare(), nextSquare.getDiagonalBarrierOnSquare() ) == false){
                    return false;
                }
            }



            else if (doesNextSquareHaveBarrierToStopCurrentDirection(currentMovingDirection, nextSquare) == true) {
                if (willPlayerLaterIntendToMovePerpendicularAfterReachingNextSquare(currentSquareIndex, nextSquareIndex) == false) {
                    return false;
                }
            } else if (isThereARobotToBlockIntendedDirectionAfterNextSquare(currentMovingDirection, nextSquare) == true) { //CHanged here
                if (willPlayerLaterIntendToMovePerpendicularAfterReachingNextSquare(currentSquareIndex, nextSquareIndex) == false) {
                    return false;
                }
            }
        }
        return false;

    }

    private boolean isThereADiagonalBarrierOnNextSquare(GridSquare nextSquare){

        if (nextSquare.getDiagonalBarrierOnSquare() != null){
            return true;
        }
        else{
            return false;
        }

    }

    private boolean didPlayerGoInRightDirectionWithDiagonalBarrier(int currentSquareIndex, int nextSquareIndex, RobotPieces robot, DiagonalBarrier currentDiagonalBarrier){
        GridSquare currentSquare = listOfSquaresMovedInOrder[currentSquareIndex];
        GridSquare nextSquare = listOfSquaresMovedInOrder[nextSquareIndex];

        GridSquare futureCurrentSquare = listOfSquaresMovedInOrder[currentSquareIndex + 1];
        GridSquare futureNextSquare = listOfSquaresMovedInOrder[nextSquareIndex + 1];

        String currentDirection = returnCurrentDirection(currentSquare, nextSquare);
        String directionMovedAfterBarrier = returnCurrentDirection(futureCurrentSquare, futureNextSquare);

        String barrierOrientation = currentDiagonalBarrier.getDiagonalBarriersOrientation();

        String colorOfBarrier = currentDiagonalBarrier.getDiagonalBarriersColor();

        String colorOfMovingRobot = robot.getColor();

        String correctDirection = "";

        //First get the correct direction the robot SHOULD go in

        if (colorOfMovingRobot.equals(colorOfBarrier)){
            correctDirection = "STRAIGHT";
        }
        else{
            correctDirection = "PERP";
        }

        String playerGoStraightOrPerp = "";

        //Now get direction player ACTUALLY went in
        if ((currentDirection.equals("NORTH") || currentDirection.equals("SOUTH")) && (directionMovedAfterBarrier.equals("EAST") || directionMovedAfterBarrier.equals("WEST"))) {
            playerGoStraightOrPerp = "PERP";
        }
        if ((currentDirection.equals("EAST") || currentDirection.equals("WEST")) && (directionMovedAfterBarrier.equals("NORTH") || directionMovedAfterBarrier.equals("SOUTH"))) {
            playerGoStraightOrPerp = "PERP";
        }
        if ( currentDirection.equals(directionMovedAfterBarrier) ){
            playerGoStraightOrPerp = "STRAIGHT";
        }

        //First check if player actually went in right general direction based on color of robot and barrier

        if ( colorOfMovingRobot.equals(colorOfBarrier) && playerGoStraightOrPerp.equals("PERP")){
            return false;
        }
        //If true, keep going.


        //Now check the case if colors are different and robot needs to bounce, did it bounce in right specific direction?
        if (colorOfMovingRobot.equals(colorOfBarrier) == false){
            if ( (currentDirection.equals("NORTH")) && (barrierOrientation.equals("TOPRIGHTBOTTOMLEFT"))  && (directionMovedAfterBarrier.equals("WEST") == false) ){
                return false;
            }

            if ( (currentDirection.equals("SOUTH")) && (barrierOrientation.equals("TOPRIGHTBOTTOMLEFT"))  && (directionMovedAfterBarrier.equals("EAST") == false) ){
                return false;
            }

            if ( (currentDirection.equals("NORTH")) && (barrierOrientation.equals("BOTTOMLEFTTOPRIGHT"))  && (directionMovedAfterBarrier.equals("EAST") == false) ){
                return false;
            }

            if ( (currentDirection.equals("SOUTH")) && (barrierOrientation.equals("BOTTOMLEFTTOPRIGHT"))  && (directionMovedAfterBarrier.equals("WEST") == false) ){
                return false;
            }

            if ( (currentDirection.equals("EAST")) && (barrierOrientation.equals("TOPRIGHTBOTTOMLEFT"))  && (directionMovedAfterBarrier.equals("NORTH") == false) ){
                return false;
            }

            if ( (currentDirection.equals("WEST")) && (barrierOrientation.equals("TOPRIGHTBOTTOMLEFT"))  && (directionMovedAfterBarrier.equals("SOUTH") == false) ){
                return false;
            }

            if ( (currentDirection.equals("EAST")) && (barrierOrientation.equals("BOTTOMLEFTTOPRIGHT"))  && (directionMovedAfterBarrier.equals("SOUTH") == false) ){
                return true;
            }

            if ( (currentDirection.equals("WEST")) && (barrierOrientation.equals("BOTTOMLEFTTOPRIGHT"))  && (directionMovedAfterBarrier.equals("NORTH") == false) ){
                return true;
            }
            else{
                return true; //This ok here?
            }
        }



        //If program made it this far, then move was true.

        return true;
    }

    private boolean doesPlayerClickARobotFirst() {
        if (this.robotToMove.getRobotRowCoord() == this.listOfSquaresMovedInOrder[0].getSquaresRowCoordinate()) {
            if (this.robotToMove.getRobotColumnCoord() == this.listOfSquaresMovedInOrder[0].getSquaresColumnCoordinate()) {
                return true;
            }
        }
        return false;
    }

    private boolean doesPlayerClickTheSquareWithTargetTileLast() {
        if (this.squareWithDesiredTargetTile.getSquaresRowCoordinate() == this.listOfSquaresMovedInOrder[this.listOfSquaresMovedInOrder.length - 1].getSquaresRowCoordinate()) {
            if (this.squareWithDesiredTargetTile.getSquaresColumnCoordinate() == this.listOfSquaresMovedInOrder[this.listOfSquaresMovedInOrder.length - 1].getSquaresColumnCoordinate()) {
                return true;
            }
        }
        return false;
    }

    private boolean doesPlayerMoveThroughABlackBarrier(GridSquare currentSquareInArray, GridSquare nextSquareInArray) {
        if (isThereABarrierBlockingIntendedDirection(currentSquareInArray, nextSquareInArray) == true) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isThereARobotOnNextSquare(GridSquare nextSquare) {
        if (nextSquare.isRobotOnSquare() == true) {
            return true;
        } else {
            return false;
        }
    }

    private boolean doesTheNextSquareHaveTheTargetTileToReach(GridSquare nextSquare) {
        if (nextSquare.doesSquareHaveATargetTile() == false) {
            return false;
        } else if (nextSquare.equals(squareWithDesiredTargetTile) == true) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isTheNextSquareTheLastSquareClicked(GridSquare nextSquare) {
        if (nextSquare.getSquaresRowCoordinate() == this.listOfSquaresMovedInOrder[this.listOfSquaresMovedInOrder.length - 1].getSquaresRowCoordinate()) {
            if (nextSquare.getSquaresColumnCoordinate() == this.listOfSquaresMovedInOrder[this.listOfSquaresMovedInOrder.length - 1].getSquaresColumnCoordinate()) {
                return true;
            }
        }
        return false;
    }



    /**
     * This is a private worker method gets a list of squares moved in order by the player on a SIMPLE BOARD. It is assumed
     * that every is legal and accurate about the player's movement, except to check if the number
     * of moves matches what the player bid. This method returns the actual number of movements the
     * player's path does as a positive integer.
     *
     * @param
     * @return
     */

    private int countNumberOfMovesOnSimpleBoard() {
        int numberOfMoves = 0;



        for (int i = 0; i < (this.listOfSquaresMovedInOrder.length - 1); i++) {
            GridSquare currentSquare = this.listOfSquaresMovedInOrder[i];
            int currentSquaresIndex = i;
            GridSquare nextSquare = this.listOfSquaresMovedInOrder[i + 1];
            int nextSquaresIndex = i + 1;
            String currentDirection = returnCurrentDirection(currentSquare, nextSquare);

            if ( (isThereABlackBarrierOnFarEdgeOfNextSquare(nextSquare, currentDirection) == true) || (isThereARobotToBlockIntendedDirectionAfterNextSquare(currentDirection, nextSquare) == true)) {
                numberOfMoves += 1;
            }
        }
        return numberOfMoves;
    }

    private int countNumberOfMovesOnComplexBoard(){
        int numberOfMoves = 0;

        for (int i = 0; i < (this.listOfSquaresMovedInOrder.length - 1); i++) {
            GridSquare currentSquare = this.listOfSquaresMovedInOrder[i];
            int currentSquaresIndex = i;
            GridSquare nextSquare = this.listOfSquaresMovedInOrder[i + 1];
            int nextSquaresIndex = i + 1;
            String currentDirection = returnCurrentDirection(currentSquare, nextSquare);

            if ( (isThereABlackBarrierOnFarEdgeOfNextSquare(nextSquare, currentDirection) == true) || (isThereARobotToBlockIntendedDirectionAfterNextSquare(currentDirection, nextSquare) == true) || (isThereADiagonalBarrierOnNextSquare(nextSquare) == true) ) {
                numberOfMoves += 1;
            }
        }
        return numberOfMoves;
    }

    /**
     * This method assumes that the last square clicked stops at an obstacle.
     * @param clickedSquares
     * @return
     */
    public int countNumberOfMovesSimpleBoard(GridSquare [] clickedSquares){
        int numberOfMoves = 0;

        for (int i = 0; i < (clickedSquares.length - 1); i++) {
            GridSquare currentSquare = clickedSquares[i];
            int currentSquaresIndex = i;
            GridSquare nextSquare = clickedSquares[i + 1];
            int nextSquaresIndex = i + 1;
            String currentDirection = returnCurrentDirection(currentSquare, nextSquare);

            if ( (isThereABlackBarrierOnFarEdgeOfNextSquare(nextSquare, currentDirection) == true) || (isThereARobotToBlockIntendedDirectionAfterNextSquare(currentDirection, nextSquare) == true)) {
                numberOfMoves += 1;
            }
        }
        return numberOfMoves;
    }

    public boolean doesNumberOfMovesAndBidNumberMatch(int numberOfMoves, int bidNumber){
        if (numberOfMoves == bidNumber){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean wasLastMoveLegalAndAccurateForMidMoveBid() {

        if (doesPlayerClickARobotFirst() == false) {
            return false;
        } else if (doesPlayerClickTheSquareWithTargetTileLast() == false) {
            return false;
        }

        //Going through the list for every pair of adjacent gridsquares:
        GridSquare currentSquare = this.listOfSquaresMovedInOrder[0];
        int currentSquareIndex = 0;
        GridSquare nextSquare = this.listOfSquaresMovedInOrder[0 + 1];
        int nextSquareIndex = 0 + 1;
        String currentMovingDirection = returnCurrentDirection(currentSquare, nextSquare);
        for (int i = 0; i < (this.listOfSquaresMovedInOrder.length - 1); i++) {

            currentSquare = this.listOfSquaresMovedInOrder[i];
            currentSquareIndex = i;
            nextSquare = this.listOfSquaresMovedInOrder[i + 1];
            nextSquareIndex = i + 1;
            currentMovingDirection = returnCurrentDirection(currentSquare, nextSquare);

            if (doesPlayerMoveThroughABlackBarrier(currentSquare, nextSquare) == true) {
                return false;
            } else if (isThereARobotOnNextSquare(nextSquare) == true) {
                return false;
            } else if (doesTheNextSquareHaveTheTargetTileToReach(nextSquare) == true) {
                if (isTheNextSquareTheLastSquareClicked(nextSquare) == true) {
                    if (doesNextSquareHaveBarrierToStopCurrentDirection(currentMovingDirection, nextSquare) == false) {
                        if (isThereARobotToBlockIntendedDirectionAfterNextSquare(currentMovingDirection, nextSquare) == false) {//Fix this.
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
            } else if (doesNextSquareHaveBarrierToStopCurrentDirection(currentMovingDirection, nextSquare) == true) {
                if (willPlayerLaterIntendToMovePerpendicularAfterReachingNextSquare(currentSquareIndex, nextSquareIndex) == false) {
                    return false;
                }
            } else if (isThereARobotToBlockIntendedDirectionAfterNextSquare(currentMovingDirection, nextSquare) == true) {
                if (willPlayerLaterIntendToMovePerpendicularAfterReachingNextSquare(currentSquareIndex, nextSquareIndex) == false) {
                    return false;
                }
            }
        }
        return false;

    }

    /**
     * This worker method returns tru if given square has a barrier blocking the intended direction, and false if not.
     * (This is some code duplication with "doesFinalSquareWithTarget" method which will need to be fixed later.)
     *
     * @param squareToCheck
     * @param currentDirection
     * @return
     */
    private boolean isThereABlackBarrierOnFarEdgeOfNextSquare(GridSquare squareToCheck, String currentDirection) {
        if (currentDirection.equals("NORTH") && (squareToCheck.doesSquareHaveNorthEdgeBarrier() == true)) {
            return true;
        } else if (currentDirection.equals("SOUTH") && (squareToCheck.doesSquareHaveSouthEdgeBarrier() == true)) {
            return true;
        } else if (currentDirection.equals("EAST") && (squareToCheck.doesSquareHaveEastEdgeBarrier() == true)) {
            return true;
        } else if (currentDirection.equals("WEST") && (squareToCheck.doesSquareHaveWestEdgeBarrier() == true)) {
            return true;
        } else {
            return false;
        }
    }



    /**
     * This method returns true if there is a barrier on the target square that can stop the robot from moving
     * in it's intended direction, false if not.
     *
     * @param intendedDirection    A string "NORTH", "SOUTH", "EAST", or "WEST" for robot's intended direction.
     * @param nextSquare A square that has the target tile where the robot hopes to stop on.
     * @return true if there is a barrier on the target square that can stop the robot from moving in it's intended
     * direction, false if not.
     */
    private boolean doesNextSquareHaveBarrierToStopCurrentDirection(String intendedDirection, GridSquare nextSquare) {
        if (intendedDirection.equals("NORTH") && (nextSquare.doesSquareHaveNorthEdgeBarrier() == true)) {
            return true;
        } else if (intendedDirection.equals("SOUTH") && (nextSquare.doesSquareHaveSouthEdgeBarrier() == true)) {
            return true;
        } else if (intendedDirection.equals("EAST") && (nextSquare.doesSquareHaveEastEdgeBarrier() == true)) {
            return true;
        } else if (intendedDirection.equals("WEST") && (nextSquare.doesSquareHaveWestEdgeBarrier() == true)) {
            return true;
        } else {
            return false;
        }

    }



    /**
     * This method tests if the FinalSquareWithTargetTileHaveAnotherRobotByItToStopIntendedDirection
     *
     * @param intendedDirection
     * @param nextSquare
     * @param
     * @return
     */

    private boolean isThereARobotToBlockIntendedDirectionAfterNextSquare(String intendedDirection, GridSquare nextSquare) {
        //First we need to get the square from the gameboard that is adjacent to the target tile square and in the
        //same direction as the intended direction of the moving robot.

        
        GridSquare squareWithPotentialBlockingRobotLocation = new GridSquare(-1, -1); //Default row/column coordinate does not exist. We will change here.
        
        

        if (intendedDirection.equals("NORTH")) {
            squareWithPotentialBlockingRobotLocation.setSquaresRowCoordinate(nextSquare.getSquaresRowCoordinate() - 1); //TODO CHanged
            squareWithPotentialBlockingRobotLocation.setSquaresColumnCoordinate(nextSquare.getSquaresColumnCoordinate());
        } else if (intendedDirection.equals("SOUTH")) {
            squareWithPotentialBlockingRobotLocation.setSquaresRowCoordinate(nextSquare.getSquaresRowCoordinate() + 1); //TODO CHanged
            squareWithPotentialBlockingRobotLocation.setSquaresColumnCoordinate(nextSquare.getSquaresColumnCoordinate());
        } else if (intendedDirection.equals("EAST")) {
            squareWithPotentialBlockingRobotLocation.setSquaresRowCoordinate(nextSquare.getSquaresRowCoordinate());
            squareWithPotentialBlockingRobotLocation.setSquaresColumnCoordinate(nextSquare.getSquaresColumnCoordinate() + 1);
        } else if (intendedDirection.equals("WEST")) {
            squareWithPotentialBlockingRobotLocation.setSquaresRowCoordinate(nextSquare.getSquaresRowCoordinate());
            squareWithPotentialBlockingRobotLocation.setSquaresColumnCoordinate(nextSquare.getSquaresColumnCoordinate() - 1);
        }

        //Next, does this square exist to be walkable?
        if (doesThisWalkableSquareCoordinateExist(squareWithPotentialBlockingRobotLocation) == false) {
            return false; //TODO Check if returning this is correct.
        }
        //Else, keep going.

        //Finally, is there a robot on this square
        if (this.gameboardWithGridSquares[ squareWithPotentialBlockingRobotLocation.getSquaresRowCoordinate() ][ squareWithPotentialBlockingRobotLocation.getSquaresColumnCoordinate() ].getRobotOnSquare() == null){
            return false; //TODO Could decouple this later
        }
        else{
            return true;
        }

    }

    /**
     * Note, square MUST have different coordinates that are positive integers, or it won't work.
     *
     * @param startingSquare
     * @param endingSquare
     * @return
     */


    private String returnCurrentDirection(GridSquare startingSquare, GridSquare endingSquare) {
        int rowCoordinateStartSquare = startingSquare.getSquaresRowCoordinate();
        int columnCoordinateStartSquare = startingSquare.getSquaresColumnCoordinate();

        int rowCoordinateEndingSquare = endingSquare.getSquaresRowCoordinate();
        int columnCoordinateEndingSquare = endingSquare.getSquaresColumnCoordinate();

        int endRowCoordMinusStartRowCoord = rowCoordinateEndingSquare - rowCoordinateStartSquare;
        int endColCoordMinusStartColCoord = columnCoordinateEndingSquare - columnCoordinateStartSquare;

        if ((endColCoordMinusStartColCoord == 0) && (endRowCoordMinusStartRowCoord < 0)) {
            return "NORTH";
        } else if ((endColCoordMinusStartColCoord == 0) && (endRowCoordMinusStartRowCoord > 0)) {
            return "SOUTH";
        } else if ((endColCoordMinusStartColCoord > 0) && (endRowCoordMinusStartRowCoord == 0)) {
            return "EAST";
        } //else if ((EndColCoordMinusStartColCoord < 0) && (EndRowCoordMinusStartRowCoord == 0)) {
            //return "WEST";
        //}
          else{
            return "WEST";
        }
    }

    /**
     * This worker method checks if there is a barrier blocking the intended direction.
     *
     * @param startingSquare
     * @param endingSquare
     * @return
     */

    private boolean isThereABarrierBlockingIntendedDirection(GridSquare startingSquare, GridSquare endingSquare) {
        String direction = returnCurrentDirection(startingSquare, endingSquare);

        if (direction.equals("NORTH") && (startingSquare.doesSquareHaveNorthEdgeBarrier() == true)) {
            return true;
        } else if (direction.equals("SOUTH") && (startingSquare.doesSquareHaveSouthEdgeBarrier() == true)) {
            return true;
        } else if (direction.equals("EAST") && (startingSquare.doesSquareHaveEastEdgeBarrier() == true)) {
            return true;
        } else if (direction.equals("WEST") && (startingSquare.doesSquareHaveWestEdgeBarrier() == true)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This worker method returns true if a player intends to move perpendicular on their next square, or false if not.
     *
     * @param currentSquareIndex
     * @param nextSquareIndex
     * @param
     * @return
     */

    private boolean willPlayerLaterIntendToMovePerpendicularAfterReachingNextSquare(int currentSquareIndex, int nextSquareIndex) {
        GridSquare currentSquare = listOfSquaresMovedInOrder[currentSquareIndex];
        GridSquare nextSquare = listOfSquaresMovedInOrder[nextSquareIndex];

        int futureCurrentSquareIndex = currentSquareIndex + 1;
        int futureNextSquareIndex = nextSquareIndex + 1;

        GridSquare futureCurrentSquare = listOfSquaresMovedInOrder[futureCurrentSquareIndex];
        GridSquare futureNextSquare = listOfSquaresMovedInOrder[futureNextSquareIndex];

        String pastDirection = returnCurrentDirection(currentSquare, nextSquare);
        String futureDirection = returnCurrentDirection(futureCurrentSquare, futureNextSquare);

        if ((futureDirection.equals("NORTH") || futureDirection.equals("SOUTH")) && (pastDirection.equals("WEST") || pastDirection.equals("EAST"))) {
            return true;
        } else if ((futureDirection.equals("WEST") || futureDirection.equals("EAST")) && (pastDirection.equals("NORTH") || pastDirection.equals("SOUTH"))) {
            return true;
        } else {
            return false;
        }
    }



    private boolean doesThisWalkableSquareCoordinateExist(GridSquare squareToCheck){
        if ( (squareToCheck.getSquaresRowCoordinate() >= 0) && (squareToCheck.getSquaresColumnCoordinate() >= 0) && (squareToCheck.getSquaresRowCoordinate() <= 15) && (squareToCheck.getSquaresColumnCoordinate() <=15) ){
            return true;
        }
        else{
            return false;
        }
    }

}



