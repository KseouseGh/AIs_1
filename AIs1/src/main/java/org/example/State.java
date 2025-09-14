package org.example;
import java.util.*;

public class State {
    private int[][] board;// Field 4x4!
    private State parent;// Previous condition (or state!!)!
    //0 - row, 1 - column
    //0, 1, 2, 3 - index
    //values storage row/col + index
    private String move;// Full path to current state!
    public static final int SIZE = 4;


    int hashCode;

    public State(int[][] board, String move) {// Copy array for non-conflict refs!
        this.board = new int[SIZE][SIZE];
        this.move = move;
        for (int i = 0; i < SIZE; i++) {
            this.board[i] = Arrays.copyOf(board[i], SIZE);
        }
        this.hashCode=hashCode();
    }

    public int[][] getBoard() {
        return board;
    }
    public State getParent() {
        return parent;
    }
    public int getHashCode(){
        return hashCode;
    }

    public void setParent(State parent, String move) {
        this.parent = parent;
        this.move = move;
    }

    public String getMove() {
        return move;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof State)) return false;
        State state = (State) obj;
        return Arrays.deepEquals(this.board, state.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    public String encode() {
        StringBuilder str = new StringBuilder();
        for (int[] row : board) {
            for (int val : row) str.append(val);
        }
        return str.toString();
    }

    public static State RandomState() {
        List<Integer> balls = new ArrayList<>();
        for (int color = 0; color < 4; color++) {
            for (int i = 0; i < 4; i++) {
                balls.add(color);
            }
        }

        Collections.shuffle(balls);
        int[][] board = new int[SIZE][SIZE];


        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = balls.remove(0);
            }
        }




        return new State(board, "");
    }

    private int[][] copyBoard() {
        int[][] copy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            copy[i] = Arrays.copyOf(board[i], SIZE);
        }
        return copy;
    }
    //Generating every state as children-state of parent-state!
    public List<State> GenerateChildren() {
        List<State> children = new ArrayList<>();
        //Rows!
        for (int row = 0; row < SIZE; row++) {
            int[][] leftBoard = copyBoard();
            MoveRowLeft(leftBoard, row);
            State leftChild = new State(leftBoard, move);
            leftChild.setParent(this, "Row " + row + " Left");
            children.add(leftChild);
        }//Columns!
        for (int col = 0; col < SIZE; col++) {
            int[][] upBoard = copyBoard();
            MoveColUp(upBoard, col);
            State upChild = new State(upBoard, move);
            upChild.setParent(this, "Col " + col + " Up");
            children.add(upChild);
        }
        return children;
    }

    protected void MoveColUp(int[][] upBoard, int col) {
        int board_elem=upBoard[0][col];
        for(int i = 0; i < (upBoard.length-1); i++){
            upBoard[i][col]=upBoard[i+1][col];//Processing row's elem-s from the end!
        }
        upBoard[(upBoard.length)-1][col]=board_elem;
    }

    protected void MoveRowLeft(int[][] leftBoard, int row) {
        int board_elem=leftBoard[row][0];
        for(int i = 0; i < (leftBoard.length-1); i++) {
            leftBoard[row][i]=leftBoard[row][i+1];
        }
        leftBoard[row][(leftBoard.length)-1]=board_elem;
    }

    protected void MoveColUp(int col) {
        MoveColUp(getBoard(), col);
        move += "1" + col;
    }

    protected void MoveRowLeft(int row) {
        MoveRowLeft(getBoard(), row);
        move += "0" + row;
    }

    public void printTestBoard() {
        System.out.println("---------");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("---------");
    }

}