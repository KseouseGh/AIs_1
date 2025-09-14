package org.example;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    private int[][] solution = {{0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}};
    private int solutionHash = 0;

    BFS() {
        this.solutionHash = Arrays.deepHashCode(solution);
    }

    public void search(State state) {
        Queue<State> queue = new LinkedList<State>();

        queue.add(state);

        while (queue.size() > 0) {
            //take value from head
            State current = queue.poll();
            if(solutionHash == current.getHashCode()) {

                System.out.println("goida: " + current.getMove());
                System.exit(1);
            }

            for (int i = 0; i < State.SIZE; i++) {
                State tmp = new State(current.getBoard(), current.getMove());
                tmp.MoveColUp(i);
                queue.add(tmp);

                tmp = new State(current.getBoard(), current.getMove());
                tmp.MoveRowLeft(i);
                queue.add(tmp);
            }
        }
        System.out.println("Queue is empty");
    }
}
