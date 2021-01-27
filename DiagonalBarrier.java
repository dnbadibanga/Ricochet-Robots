
/**
 * This class creates a diagonal barrier that is found on a complex board. It has a color "RED", "GREEN", "BLUE", "YELLOW", and it has one orientation, either "TOPLEFTBOTTOMRIGHT" or 
 * "TOPRIGHTBOTTOMLEFT".
 */
public class DiagonalBarrier {

    private String color;
    private String orientation;

    
    /**
     * This class creates a diagonal barrier that is found on a complex board. It has a color "RED", "GREEN", "BLUE", "YELLOW", and it has one orientation, either "TOPLEFTBOTTOMRIGHT" or 
     * "TOPRIGHTBOTTOMLEFT".
     * @return
     */
    public DiagonalBarrier(String newColor, String newOrientation){
        this.color = newColor;
        this.orientation = newOrientation;
    }

    /**
     * This method returns a string value of the diagonal barrier's color, either "RED", "GREEN", "BLUE", or "YELLOW".
     * @return
     */

    public String getDiagonalBarriersColor(){
        return this.color;
    }

    /**
     * This method returns a string value of the diagonal barrier's orientation, either "TOPLEFTBOTTOMRIGHT" or 
     * "TOPRIGHTBOTTOMLEFT".
     */
    public String getDiagonalBarriersOrientation(){
        return this.orientation;
    }



}