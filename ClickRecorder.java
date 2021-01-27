
import java.util.ArrayList;

/*
This class records the gridsquares that the player has clicked.
 */

public class ClickRecorder {
    ArrayList<GridSquare> clickedGridSquares;


    public ClickRecorder(){
        this.clickedGridSquares = new ArrayList<GridSquare>();
    }

    public void recordClickedGridSquare(GridSquare newClickedGridSquare){
        this.clickedGridSquares.add(newClickedGridSquare);
    }

    public GridSquare[] getArrayOfClickedSquares(){
        return convertSquareArrayListToSquareArray(this.clickedGridSquares);
    }

    /**
     * This method takes an arrayList with Square elements and returns as array of Square elements from the
     * ArrayList in the same order as in the ArrayList.
     *
     * @param
     * @return
     */

    private GridSquare[] convertSquareArrayListToSquareArray(ArrayList<GridSquare> newSquareArrayList) {

        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //This section here removes duplicate square error.
        ArrayList<GridSquare> squareArrayList = new ArrayList<GridSquare>();

        for (GridSquare square : newSquareArrayList){
            if (!squareArrayList.contains(square)){
                squareArrayList.add(square);
            }
        }
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//test

        GridSquare[] squareArray = new GridSquare[squareArrayList.size()];
        for (int i = 0; i < squareArrayList.size(); i++) {
            squareArray[i] = squareArrayList.get(i);
        }
        return squareArray;
    }

    public void clearClickedSquares(){
        this.clickedGridSquares = new ArrayList<GridSquare>();
    }


}