package org.example;
import java.util.*;

public class BFS {
    private int[][] solution = {{0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}};
    private int solutionHash = 0;

    BFS() {
        this.solutionHash = Arrays.deepHashCode(solution);
    }

    public void search(State state) {
        Queue<State> queue = new LinkedList<State>();
        Set<Integer> visited = new HashSet<>();
        queue.add(state);
        visited.add(state.hashCode());
        while (queue.size() > 0) {//Taking value from the head of queue!
            State current = queue.poll();
            if(solutionHash == current.hashCode()) {

                System.out.println("Fonded solution: " + current.getMove() + " .");
                return;
                //System.exit(1);
            }
            for (int i = 0; i < State.SIZE; i++) {
                State tmp = new State(current.getBoard(), current.getMove());
                tmp.MoveColUp(i);
                if(!visited.contains(tmp.hashCode())){
                    queue.add(tmp);
                    visited.add(tmp.hashCode());
                }
                tmp = new State(current.getBoard(), current.getMove());
                tmp.MoveRowLeft(i);
                if(!visited.contains(tmp.hashCode())){
                    queue.add(tmp);
                    visited.add(tmp.hashCode());
                }
            }
        }
        System.out.println("Queue is empty!");
    }
}