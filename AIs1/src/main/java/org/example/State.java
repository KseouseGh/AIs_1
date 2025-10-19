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

    int priorityV1;


    public State(int[][] board, String move) {// Copy array for non-conflict refs!
        this.board = new int[SIZE][SIZE];
        this.move = move;
        for (int i = 0; i < SIZE; i++) {
            this.board[i] = Arrays.copyOf(board[i], SIZE);
        }
        this.hashCode=hashCode();
    }

    public void calculateV1 () {
        int[] aim = {0, 1, 2, 3};
        priorityV1 = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(aim[i] != board[i][j]) {
                    priorityV1++;
                }
            }
        }
    }

    public int getPriorityV1() {
        return priorityV1;
    }

    public int setPriorityV1(int priorityV1) {
        this.priorityV1 = priorityV1;
    }
    public int[][] getBoard() {
        return board;
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
        for(int col = 0; col < this.SIZE; col++) {
            Map<Integer, Integer> step = new HashMap<>();
            int color = board[0][col];
            for (int row = 0; row < this.SIZE; row++) {
                step.put(board[row][col], (step.getOrDefault(board[row][col], 0) + 1));
            }

            if (step.size() > 1) {
                int max_frequency = Collections.max(step.values());
                int min_frequency = Collections.min(step.values());
                h = h + ((step.size() - 1) + (SIZE - max_frequency));
            }

            if (step.size() == 1) {
                int colColor = board[0][col];
                if (colColor != col) {
                    h = h + SIZE;
                }
            }
        }
        for(int row = 0; row < SIZE; row++){
            Map<Integer, Integer> step = new HashMap<>();
            for(int col = 0; col < SIZE; col++){
                step.put(board[row][col], step.getOrDefault(board[row][col], 0) + 1);
            }

            if(step.size() > 1){
                int max_frequency = Collections.max(step.values());//h = h+(SIZE - step.size());
                h = h+(SIZE - step.size()) * (row + 1);
            }
        }
        return h;
    }

    public int getLeftHash() {
        int hash = 0;
        int pos = 0;
        // 0 and 1!
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < 2; j++) {
                hash |= (board[i][j] & 0b11) << (pos * 2);
                pos++;
            }
        }

        return hash;
    }

    public int getRightHash() {
        int hash = 0;
        int pos = 0;
        // 2 and 3!
        for (int i = 0; i < SIZE; i++) {
            for (int j = 2; j < SIZE; j++) {
                hash |= (board[i][j] & 0b11) << (pos * 2);
                pos++;
            }
        }

        return hash;
    }

    public int calc_PDB_method_heuristic(AStarPatternDB.PatternDB pdb01l, AStarPatternDB.PatternDB pdb23r) {
        int h = 0;
        int leftKey = getLeftHash();
        int rightKey = getRightHash();
        int h1 = pdb01l.pdb.getOrDefault(leftKey, 0);
        int h2 = pdb23r.pdb.getOrDefault(rightKey, 0);

        h=h+(h1+h2);
        return h;
    }
}