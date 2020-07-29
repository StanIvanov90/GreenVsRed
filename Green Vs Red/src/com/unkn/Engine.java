package com.unkn;


import java.util.Arrays;
import java.util.Scanner;

public class Engine {

    final static int SURROUNDED_BY_TWO = 2;
    final static int SURROUNDED_BY_THREE = 3;
    final static int SURROUNDED_BY_SIX = 6;
    final static int INITIAL_ZERO = 0;
    final static int INITIAL_ONE = 1;
    final static int CHANGED_ZERO = 2;
    final static int CHANGED_ONE = 3;
    final static int ADD_GREEN_NEIGHBOUR = 1;


    private Field field;
    private int generations;
    private int pointWidth;
    private int pointHeight;
    private int greenOccurrences;

    Scanner scan = new Scanner(System.in);

    //Getting the dimensions of the board.
    void initializeBoard() {
        int[] input = Arrays.stream(scan.nextLine().split(", ")).mapToInt(Integer::parseInt).toArray();
        int rows = input[0];
        int cols = input[1];
        this.field = new Field(rows, cols);


    }

    //Setting the initial values of the board
    void setGenerationZero() {
        for (int i = 0; i < this.field.getHeight(); i++) {
            //taking the next lines of input and mapping them as a long array.
            long[] values = Arrays.stream(scan.nextLine().split("")).mapToLong(Long::parseLong).toArray();
            for (int j = 0; j < values.length; j++) {
                this.field.getBoard()[i][j] = values[j];
            }

        }
    }

    void start() {
        //taking the last line of input, in order to get the coordinates of the desired cell in the grid
        // and number of generations.
        int[] coordinates = Arrays.stream(scan.nextLine().split(", ")).mapToInt(Integer::parseInt).toArray();
        this.pointWidth = coordinates[0];
        this.pointHeight = coordinates[1];
        this.generations = coordinates[2];

        for (int i = 0; i <= this.generations; i++) {
            //monitoring the desired cell and checking if is it green, so we can add a green occurrence.
            if (this.field.getBoard()[this.pointHeight][this.pointWidth] == INITIAL_ONE) {
                this.greenOccurrences++;
            }

            for (int row = 0; row < this.field.getHeight(); row++) {
                for (int col = 0; col < this.field.getWidth(); col++) {
                    //checking to see if the current cell is equal to 0.
                    if (this.field.getBoard()[row][col] == INITIAL_ZERO) {
                        //counting the green neighbours.
                        countGreenNeighbours(row, col);
                        //applying the appropriate rule for a red cell.
                        this.redRuleApplication(row, col);
                        //checking to see if the current cell is equal to 1.
                    } else if (this.field.getBoard()[row][col] == INITIAL_ONE) {
                        //counting the green neighbours.
                        countGreenNeighbours(row, col);
                        //applying the appropriate rule for a green cell.
                        this.greenRuleApplication(row, col);

                    }
                }
            }
            //resetting the board back to 0 and 1 values.
            this.resetBoardFromChangedValues();

        }

        System.out.println(greenOccurrences);
    }
// Method to print the matrix for testing purposes.
//    public void printMatrix() {
//        for (int i = 0; i < this.field.getHeight(); i++) {
//            for (int j = 0; j < this.field.getWidth(); j++) {
//                System.out.print(this.field.getBoard()[i][j] + " ");
//            }
//            System.out.println();
//        }
//
//    }

    //Method to count the green neighbours.
    //If the current cell value is equal to 1 or 3, we are increasing the count by 1.
    private int countGreenNeighbours(int row, int col) {


        int count = 0;


        if (inBounds(this.field.getBoard(), row - 1, col - 1)) {
            if (this.field.getBoard()[row - 1][col - 1] == INITIAL_ONE ||
                    this.field.getBoard()[row - 1][col - 1] == CHANGED_ONE) {
                count += ADD_GREEN_NEIGHBOUR;
            }
        }

        if (inBounds(this.field.getBoard(), row, col - 1)) {
            if (this.field.getBoard()[row][col - 1] == INITIAL_ONE ||
                    this.field.getBoard()[row][col - 1] == CHANGED_ONE) {
                count += ADD_GREEN_NEIGHBOUR;
            }
        }
        if (inBounds(this.field.getBoard(), row + 1, col - 1)) {
            if (this.field.getBoard()[row + 1][col - 1] == INITIAL_ONE ||
                    this.field.getBoard()[row + 1][col - 1] == CHANGED_ONE) {
                count += ADD_GREEN_NEIGHBOUR;
            }
        }

        if (inBounds(this.field.getBoard(), row - 1, col)) {
            if (this.field.getBoard()[row - 1][col] == INITIAL_ONE ||
                    this.field.getBoard()[row - 1][col] == CHANGED_ONE) {
                count += ADD_GREEN_NEIGHBOUR;
            }
        }

        if (inBounds(this.field.getBoard(), row + 1, col)) {
            if (this.field.getBoard()[row + 1][col] == INITIAL_ONE ||
                    this.field.getBoard()[row + 1][col] == CHANGED_ONE) {
                count += ADD_GREEN_NEIGHBOUR;
            }
        }

        if (inBounds(this.field.getBoard(), row - 1, col + 1)) {
            if (this.field.getBoard()[row - 1][col + 1] == INITIAL_ONE ||
                    this.field.getBoard()[row - 1][col + 1] == CHANGED_ONE) {
                count += ADD_GREEN_NEIGHBOUR;
            }
        }

        if (inBounds(this.field.getBoard(), row, col + 1)) {
            if (this.field.getBoard()[row][col + 1] == INITIAL_ONE ||
                    this.field.getBoard()[row][col + 1] == CHANGED_ONE) {
                count += ADD_GREEN_NEIGHBOUR;
            }
        }

        if (inBounds(this.field.getBoard(), row + 1, col + 1)) {
            if (this.field.getBoard()[row + 1][col + 1] == INITIAL_ONE ||
                    this.field.getBoard()[row + 1][col + 1] == CHANGED_ONE) {
                count += ADD_GREEN_NEIGHBOUR;
            }
        }

        return count;


    }

    //In this method, once we have counted the green neighbours of a red cell, we are changing the value of the cell
    //from 0 to 2 if the green neighbours are 3 or 6.
    private void redRuleApplication(int row, int col) {

        int totalGreenNeighbours = countGreenNeighbours(row, col);

        if (totalGreenNeighbours == SURROUNDED_BY_THREE ||
                totalGreenNeighbours == SURROUNDED_BY_SIX) {
            this.field.getBoard()[row][col] = CHANGED_ZERO;
        }


    }
    //In this method, once we have counted the green neighbours of a green cell, we are changing the value of the cell
    //from 1 to 3 if the green neighbours are 2, 3 or 6.
    private void greenRuleApplication(int row, int col) {

        int totalGreenNeighbours = countGreenNeighbours(row, col);

        if (totalGreenNeighbours != SURROUNDED_BY_TWO &&
                totalGreenNeighbours != SURROUNDED_BY_THREE &&
                totalGreenNeighbours != SURROUNDED_BY_SIX) {
            this.field.getBoard()[row][col] = CHANGED_ONE;
        }


    }

    //Since we are using methods to change 0s and 1s to 2s and 3s accordingly,
    //this method helps reset the grid back to 0s and 1s from the changed values.
    private void resetBoardFromChangedValues() {
        for (int row = 0; row < this.field.getHeight(); row++) {
            for (int col = 0; col < this.field.getWidth(); col++) {
                if (this.field.getBoard()[row][col] == CHANGED_ZERO) {
                    this.field.getBoard()[row][col] = INITIAL_ONE;
                } else if (this.field.getBoard()[row][col] == CHANGED_ONE) {
                    this.field.getBoard()[row][col] = INITIAL_ZERO;
                }
            }
        }
    }


    //Method to find whether the current element is in bounds, so that we don't get IndexOutOfBounds exception.
    private boolean inBounds(long[][] field, int row, int col) {
        return row >= 0 && col >= 0 && row < field.length && col < field[row].length;

    }
}
