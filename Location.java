//package com.group2.physicalgameobjects;

/**
 * A class for working with locations.
 */



public class Location {

    private int rowCoordinate;
    private int columnCoordinate;

    public Location(int newRowCoordinate, int newColumnCoordinate){
        this.rowCoordinate=newRowCoordinate;
        this.columnCoordinate=newColumnCoordinate;
        }

        public int getRowCoordinate(){
            return this.rowCoordinate;
        }

        public int getColumnCoordinate(){
            return this.rowCoordinate;
        }

        public void changeRowCoordinate(int newRowCoordinate){
            this.rowCoordinate = newRowCoordinate;
        }
        public void changeColumnCoordinate(int newColumnCoordinate){
            this.columnCoordinate = newColumnCoordinate;
        }
}
