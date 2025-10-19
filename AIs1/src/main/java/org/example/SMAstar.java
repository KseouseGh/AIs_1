package org.example;

import java.util.*;

public class SMAstar {
    private static int[][] solution = {{0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}};
    private static int solutionHash = 0;

    static {
        solutionHash = State.hashCode(solution);
    }

    public static void search(State state) {
        state.calculateH();
        int iterations = 0;

        PriorityQueue<State> queue = new PriorityQueue<>(
                Comparator.comparingInt(State::getPriority)
        );
        Map<Integer, State> visited = new HashMap<>();

        queue.add(state);

        while (!queue.isEmpty()) {
            State current = queue.poll();
            iterations++;

            if(current.hashCode() == solutionHash){
                System.out.println("Found solution: " + current.getMove() + " .");
                current.printTestBoard();
                System.out.println("Astar new iterations = " + iterations);
                return;
            }

            for(int i = 0; i < State.SIZE; i++) {
                State tmp;

                tmp = new State(current.getBoard(), current.getMove());
                tmp.MoveRowLeft(i);
                tmp.calculateH();
                tmp.g = current.g + 1;
                if(!visited.containsKey(tmp.hashCode()) || visited.get(tmp.hashCode()).g > tmp.g){
                    queue.add(tmp);
                    visited.put(tmp.hashCode(), tmp);
                }

                tmp = new State(current.getBoard(), current.getMove());
                tmp.MoveColUp(i);
                tmp.h = current.h;
                tmp.g = current.g + 1;
                if(!visited.containsKey(tmp.hashCode()) || visited.get(tmp.hashCode()).g > tmp.g){
                    queue.add(tmp);
                    visited.put(tmp.hashCode(), tmp);
                }
            }
        }
    }
}
