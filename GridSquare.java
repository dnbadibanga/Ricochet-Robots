import javax.swing.*;
import java.awt.*;

/*
 *  a simple extension of JButton which allows the background colour to be set and switched back and forth with ease
 *  
 *  there are other ways of doing this, but it's a neat way to demonstrate how to create your own gui components
 *  (as well as how to use ternary operators)
 */

/**
 * This class describes a square on a game board (used for both simple and complex). It has a row and column location on the
 * board (where top left has row col coordinate as (0, 0), top right corner has (0, 15), bottom right corner has
 * (15, 15), and bottom left corner has (15, 0). The square also has a list of what items are currently "on" it
 * in a list, where certain indices are given designated slots for different types of Java objects.
 * It also records if it has an edge barrier that a robot can't pass through, or a diagonal
 * barrier that some robots can pass through (note that two square adjacent to each other that have a edge barrier in between
 * them, have a recorded edge barrier on opposite sides of each other.) Square can also tell you if there is a target
 * tile on it and what that tile is,  and if there is a robot on it, and what that robot is.
 *
 */
public class GridSquare extends JButton
{
    private int rowCoord, columnCoord;         // not used in this demo, but might be helpful in future...
    private int up, down, left, right;
    private RobotPieces squaresRobot;
    private TargetTile squaresTargetTile;
    private DiagonalBarrier squaresDiagonalBarrier;
    private String northEdgeBarrier, southEdgeBarrier, eastEdgeBarrier, westEdgeBarrier;

    // constructor takes the x and y coordinates of this square
    public GridSquare(int rowCoord, int columnCoord)
    {
        super();
        this.setSize(50,50);
        this.rowCoord = rowCoord;
        this.columnCoord = columnCoord;

        this.squaresRobot = null;
        this.squaresTargetTile = null;
        this.squaresDiagonalBarrier = null;

        this.northEdgeBarrier = "";
        this.southEdgeBarrier = "";
        this.eastEdgeBarrier = "";
        this.westEdgeBarrier = "";

        //this.setEnabled(false);//By default when first made, the buttons will not be clickable. This will change when players need to prove their move.
    }
    
    // if the decider is even, it chooses black, otherwise white (for 'column+row' will allow a chequerboard effect)
    public void setColor( int decider)
    {
        Color colour = (int) (decider/2.0) == (decider/2.0) ? Color.WHITE : Color.white;
        this.setBackground( colour);
    }
    
    // if the square s black it becomes white, and vice-versa
    public void switchColor()
    {
        Color colour =Color.white;
        this.setBackground( colour);
    }
    
    public void border()
    {
        //border to the top
        this.setBorder(BorderFactory.createMatteBorder(0,0,0,4,Color.black));
        //border to the bottom
        this.setBorder(BorderFactory.createMatteBorder(4,0,0,0,Color.black));
        //border to the left
        this.setBorder(BorderFactory.createMatteBorder(0,4,0,0,Color.black));
        //border to the right
        this.setBorder(BorderFactory.createMatteBorder(0,0,4,0,Color.black));
    }
    public void setIcon()
    {   
        //blue icons
        Icon blueCircle =new ImageIcon("bluecircle.jpg");
        Icon blueHex =new ImageIcon("blueHex.jpg");
        Icon blueSquare =new ImageIcon("blueSquare.jpg");
        Icon blueTriangle =new ImageIcon("blueTriangle.jpg");
        //green icons
        Icon greenCircle =new ImageIcon("greencircle.jpg");
        Icon greenHex =new ImageIcon("greenhex.jpg");
        Icon greenSquare =new ImageIcon("greensquare.jpg");
        Icon greenTriangle =new ImageIcon("greentriangle.jpg");
        //red icons
        Icon redCricle =new ImageIcon("redcircle.jpg");
        Icon redHex =new ImageIcon("redhex.jpg");
        Icon redSquare =new ImageIcon("redsquare.jpg");
        Icon redTriangle =new ImageIcon("redtriangle.jpg");
        //yellow icons
        Icon yellowCricle =new ImageIcon("redcircle.jpg");
        Icon yellowHex =new ImageIcon("redhex.jpg");
        Icon yellowSquare =new ImageIcon("redsquare.jpg");
        Icon yellowTriangle =new ImageIcon("redtriangle.jpg");
        
        //jButton1.setIcon(i);


    }

    /**
     * This method receives a robot to put on the square.
     * @param newRobot
     */

    public void addRobotToSquare(RobotPieces newRobot){
        this.squaresRobot = newRobot;
    }

    /**
     * This method returns true if there is a robot currently on it (the square), or false if not.
     * @return
     */

    public boolean isRobotOnSquare(){
        if (this.squaresRobot == null){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * This method returns the actual robot that is on it (the square). WARNING if there is no robot on this
     * and you call it, it will return null.
     * @return
     */

    public RobotPieces getRobotOnSquare(){
        return this.squaresRobot;
    }

    /**
     * This method removes a robot from a square.
     */

    public void removeRobotFromSquare(){
        this.squaresRobot = null;
    }

    /**
     * This method returns true if there is a diagonal barrier on the square, and false if not.
     * @return
     */

    public boolean doesSquareHaveDiagonalBarrier(){
        if (squaresDiagonalBarrier != null){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This method adds a DiagonalBarrier object to the square.
     * @param newDiagonalBarrier
     */

    public void addDiagonalBarrierToSquare(DiagonalBarrier newDiagonalBarrier){
        this.squaresDiagonalBarrier = newDiagonalBarrier;
    }

    /**
     * This method returns null if there is no diagonal barrier on square, or
     * the DiagonalBarrier object found on the square.
     * @return
     */
    public DiagonalBarrier getDiagonalBarrierOnSquare(){
        return this.squaresDiagonalBarrier;
    }



    /**
     * This method adds a target tile to a square.
     * @param newTargetTile
     */

    public void addTargetTileToSquare(TargetTile newTargetTile){
        this.squaresTargetTile = newTargetTile;
    }

    public TargetTile getTargetTileOnSquare(){
        return this.squaresTargetTile;
    }

    /**
     * This method returns true if the square has a target tile, and false if not.
     * @return
     */

    public boolean doesSquareHaveATargetTile(){
        if (squaresTargetTile == null){
            return false;
        }
        else{
            return true;
        }
    }

    public void removeTargetTileFromGridSquare(){
        this.squaresTargetTile = null;
    }

    /**
     * This method add a square edge barrier to the north edge of the square.
     */

    public void addNorthEdgeBarrier(){
        this.northEdgeBarrier = "NORTH_EDGE_BARRIER";
    }

    /**
     * This method add a square edge barrier to the south edge of the square.
     */

    public void addSouthEdgeBarrier(){
        this.southEdgeBarrier = "SOUTH_EDGE_BARRIER";
    }
    /**
     * This method add a square edge barrier to the east edge of the square.
     */

    public void addEastEdgeBarrier(){
        this.eastEdgeBarrier = "EAST_EDGE_BARRIER";
    }
    /**
     * This method add a square edge barrier to the west edge of the square.
     */

    public void addWestEdgeBarrier(){
        this.westEdgeBarrier = "WEST_EDGE_BARRIER";
    }
    /**
     * This method returns true if square has a north edge barrier, false if not.
     * @return
     */

    public boolean doesSquareHaveNorthEdgeBarrier(){
        if (this.northEdgeBarrier.equals("NORTH_EDGE_BARRIER")) {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This method returns true if square has south edge barrier, false if not.
     * @return
     */

    public boolean doesSquareHaveSouthEdgeBarrier(){
        if (this.southEdgeBarrier.equals("SOUTH_EDGE_BARRIER")) {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This method returns true if square has east edge barrier, false if not.
     * @return
     */
    public boolean doesSquareHaveEastEdgeBarrier(){
        if (this.eastEdgeBarrier.equals("EAST_EDGE_BARRIER")) {
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * This method returns true if a square has a west edge barrier, false if not.
     * @return
     */
    public boolean doesSquareHaveWestEdgeBarrier(){
        if (this.westEdgeBarrier.equals("WEST_EDGE_BARRIER")) {
            return true;
        }
        else{
            return false;
        }
    }

    public void setSquaresRowCoordinate(int newRowCoordinate){
        this.rowCoord = newRowCoordinate;
    }

    public void setSquaresColumnCoordinate(int newColumnCoordinate){
        this.columnCoord = newColumnCoordinate;
    }

    /**
     * This method returns the row coordinate of a square.
     * @return
     */
    public int getSquaresRowCoordinate(){
        return this.rowCoord;
    }

    /**
     * This method returns the column coordinate of a square.
     */

    public int getSquaresColumnCoordinate(){
        return this.columnCoord;
    }

/* UNUSED METHODS
    public void setSquareToUnvisted(){
        this.wasSquareVisited = false;
    }

    public void setSquareToVisited(){
        this.wasSquareVisited = true;
    }

    public void setSquareAsCurrentNodeInDA(){
        this.isSquareTheCurrentNodeForDA = true;
    }

    public void setSquareAsNOTCurrentNodeInDA(){
        this.isSquareTheCurrentNodeForDA = false;
    }

    public void changeActualDistanceValueForDA(int newDistanceValue){
        this.actualDistanceValue = newDistanceValue;
    }

    public void changePreliminaryDistanceValueForDA(int preliminaryDistanceValue){
        this.preliminaryDistanceValue = preliminaryDistanceValue;
    }*/

    /*
    /**
     * This method accepts a string direction "NORTH" "SOUTH" "EAST" "WEST" and returns if the player or user can move
     * in that
     * @param directionIWishToMove
     * @return
     */
/*
    public boolean canIMoveNorth(String directionIWishToMove){
        if (doesSquareHaveNorthEdgeBarrier() == true){
            return false;
        }
        else{
            return true;
        }

    }

    public boolean canIMoveSouth(String directionIWishToMove){
        if (doesSquareHaveSouthEdgeBarrier() == true){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean canIMoveEast(String directionIWishToMove){
        if (doesSquareHaveEastEdgeBarrier() == true){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean canIMoveWest(String directionIWishToMove){
        if (doesSquareHaveWestEdgeBarrier() == true){
            return false;
        }
        else{
            return true;
        }
    }

*/




}