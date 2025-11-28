package org.example;
import java.util.*;

public class BFS {
    private int[][] solution = {{0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}};
    private int solutionHash = 0;

    BFS() {
        this.solutionHash = State.hashCode(solution);
    }

    public void search(State state) {
        Queue<State> queue = new LinkedList<State>();
        Set<Integer> visited = new HashSet<>();
        int iterations = 0;
        int peakOpen = 0;

        queue.add(state);
        visited.add(state.hashCode());

        while (queue.size() > 0) {
            State current = queue.poll();
            iterations++;
            peakOpen = Math.max(peakOpen, queue.size());

            if(solutionHash == current.hashCode()) {
                System.out.println("Fonded solution: " + current.getMove() + " .");
                current.printTestBoard();
                System.out.println("BFS iterations = " + iterations);
                System.out.println("Peak OPENED storage size = " + peakOpen);
                return;
            }

            for (int i = 0; i < State.SIZE; i++) {
                State tmp;

                tmp = new State(current.getBoard(), current.getMove());
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