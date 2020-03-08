package hilapartuk.dev;

import java.util.ArrayList;

class Cell {
    Integer row;
    Integer coll;

    Cell(Integer row, Integer coll) {
        this.row = row;
        this.coll = coll;
    }

    // This is needed for the *contains* in the main function
    @Override
    public boolean equals(Object obj) {
        return cellsEquals((Cell) obj);
    }

    private boolean cellsEquals(Cell otherCell) {
        boolean retVal = false;

        if (otherCell != null &&
                row.equals(otherCell.row) &&
                coll.equals(otherCell.coll)) {
            retVal = true;
        }

        return retVal;
    }
}

public class Main {

    private static final int rowNumber = 6;
    private static final int colNumber = 8;
    private static final Integer[][] numbersGrid = {
            {1, 4, 4, 4, 4, 3, 3, 1},
            {2, 1, 1, 4, 3, 3, 1, 1},
            {3, 2, 1, 1, 2, 3, 2, 1},
            {3, 3, 2, 1, 2, 2, 2, 2},
            {3, 1, 3, 1, 1, 4, 4, 4},
            {1, 1, 3, 1, 1, 4, 4, 4}};
    private static Integer[][] visitedGrid = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}};

    public static void main(String[] args) {
        ArrayList<Cell> largestConnectedComponent =
                new ArrayList<Cell>();

        ArrayList<Cell> currentConnectedComponent =
                new ArrayList<Cell>();

        // Running over the grid
        for (int rowsIndex = 0; rowsIndex < rowNumber; rowsIndex++) {
            for (int colsIndex = 0; colsIndex < colNumber; colsIndex++) {

                // Checking if the current cell is already part of a component
                if (visitedGrid[rowsIndex][colsIndex].equals(0)) {

                    // finding in recursion the component
                    currentConnectedComponent = findConnectedComponent(
                            rowsIndex,
                            colsIndex,
                            currentConnectedComponent);


                    // Printing the found component
                    printGridWithHighlightComponent(currentConnectedComponent);

                    // Checking if the current component is bigger than the largest
                    if (currentConnectedComponent.size() > largestConnectedComponent.size()) {
                        largestConnectedComponent.clear();
                        largestConnectedComponent.addAll(currentConnectedComponent);
                    }

                    // Resetting the current component
                    currentConnectedComponent.clear();
                }
            }
        }

        // Printing the largest component
        printGridWithHighlightComponent(largestConnectedComponent);
    }

    private static void printGridWithHighlightComponent(ArrayList<Cell> component) {
        String RESET = "\033[0m";
        String RED_BACKGROUND = "\033[41m";
        String currColor;
        Cell currCell = new Cell(0, 0);

        for (int rowsIndex = 0; rowsIndex < rowNumber; rowsIndex++) {
            System.out.print("              ");

            for (int colsIndex = 0; colsIndex < colNumber; colsIndex++) {

                currCell.row = rowsIndex;
                currCell.coll = colsIndex;

                currColor = RESET;

                if (component.contains(currCell)) {
                    currColor = RED_BACKGROUND;
                }

                System.out.print(currColor + " " + numbersGrid[rowsIndex][colsIndex] + " " + RESET + "  ");
            }

            System.out.println();
            System.out.println();
        }
        System.out.println("            ---------------------------------------");

    }

    private static ArrayList<Cell> findConnectedComponent(int currRow, int currCol,
                                                          ArrayList<Cell> groupCells) {

        // Adding myself
        groupCells.add(new Cell(currRow, currCol));

        Integer[] rowsAddition = {0, -1, 0, 1};
        Integer[] colsAddition = {-1, 0, 1, 0};
        int directions = 4;

        // Checking for each of the four directions (left, up, right and down)
        // if it's part of the component
        for (int directionsIndex = 0; directionsIndex < directions; directionsIndex++) {

            int nextRow = (currRow + rowsAddition[directionsIndex]);
            int nextCol = (currCol + colsAddition[directionsIndex]);

            if (checkIsNextValid(currRow, currCol, nextRow, nextCol)) {

                // Recursion
                findConnectedComponent(nextRow, nextCol, groupCells);
            }
        }

        return groupCells;
    }

    private static boolean checkIsNextValid(int currRow, int currCol,
                                            int nextRow, int nextCol) {
        boolean retVal = false;

        // Checking that the next cell is in borders of grid
        if (nextCol >= 0 &&
                nextCol < colNumber &&
                nextRow >= 0 &&
                nextRow < rowNumber) {

            // Checking that the next cell is new
            if (visitedGrid[nextRow][nextCol].equals(0)) {

                // Checking that the next cell and current cell is the same color
                if (numbersGrid[currRow][currCol].equals(numbersGrid[nextRow][nextCol])) {
                    visitedGrid[nextRow][nextCol] = 1;
                    retVal = true;
                }
            }
        }

        return retVal;
    }
}

