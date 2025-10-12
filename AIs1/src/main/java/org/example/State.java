package org.example;
import java.util.*;

public class State {
    private int[][] board;// Field 4x4!
    private State parent;// Previous condition (or state!!)!
    private String move;// Full path to current state!
    public static final int SIZE = 4;
    int hashCode;
    int depth = 0;
    int g=0;//Price from start state!
    int h=0;//Informal estimate to solution!
    int f=0;//Cal. value = g+h!

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

    public String getMoveR() {
        List<String> lines = new ArrayList<>(Arrays.asList(move.trim().split("\n")));
        Collections.reverse(lines);

        return "\n" + String.join("\n", lines);
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
        int hash = 0;
        int pos = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                hash |= (board[i][j] & 0b11) << (pos * 2);
                pos++;
            }
        }
        return hash;
    }

    public static int hashCode(int [][] obj) {
        int hash = 0;
        int pos = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                hash |= (obj[i][j] & 0b11) << (pos * 2);
                pos++;
            }
        }
        return hash;
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

        board = new int[][] {{1, 0, 1, 2}, {0, 1, 2, 3}, {2, 3, 0, 3}, {0, 1, 2, 3}};

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
        int board_elem = upBoard[0][col];
        for (int i = 0; i < (upBoard.length - 1); i++) {
            upBoard[i][col] = upBoard[i + 1][col];
        }
        upBoard[upBoard.length - 1][col] = board_elem;
    }

    protected void MoveRowLeft(int[][] leftBoard, int row) {
        int board_elem = leftBoard[row][0];
        for (int i = 0; i < (leftBoard[row].length - 1); i++) {
            leftBoard[row][i] = leftBoard[row][i + 1];
        }
        leftBoard[row][leftBoard[row].length - 1] = board_elem;
    }

    protected void MoveColDown(int[][] downBoard, int col) {
        int board_elem = downBoard[downBoard.length - 1][col];
        for (int i = downBoard.length - 1; i > 0; i--) {
            downBoard[i][col] = downBoard[i - 1][col];
        }
        downBoard[0][col] = board_elem;
    }

    protected void MoveRowRight(int[][] rightBoard, int row) {
        int board_elem = rightBoard[row][rightBoard[row].length - 1];
        for (int i = rightBoard[row].length - 1; i > 0; i--) {
            rightBoard[row][i] = rightBoard[row][i - 1];
        }
        rightBoard[row][0] = board_elem;
    }

    protected void MoveColUp(int col) {
        MoveColUp(getBoard(), col);
        move += "\nMove Up Column " + col;
    }

    protected void MoveRowLeft(int row) {
        MoveRowLeft(getBoard(), row);
        move += "\nMove Left Row " + row;
    }

    protected void MoveColDown(int col) {
        MoveColDown(getBoard(), col);
        move += "\nMove Up Column " + col;
    }

    protected void MoveRowRight(int row) {
        MoveRowRight(getBoard(), row);
        move += "\nMove Left Row " + row;
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

    int getDepth() {
        return depth;
    }

    void setDepth(int depth) {
        this.depth = depth;
    }

    public int calc_heuristic_free_decision(){
        int h=0;
        for(int col = 0; col < this.SIZE; col++){
            int color = board[0][col];
            for(int row = 0; row < this.SIZE; row++){
                if(board[row][col]!=color){
                    h=h+1;
                }
            }
        }
        return h;
    }
}