package org.example;

import java.util.*;

public class SMAstar {
    private static int[][] solution = {{0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}};
    private static int solutionHash = 0;


    static {
        solutionHash = State.hashCode(solution);
    }

    public static void search(State state) {
        state.calculateV1();
        int iterations = 0;

        PriorityQueue<State> queue = new PriorityQueue<>(
                Comparator.comparingInt(State::getPriorityV1)
        );
        Set<Integer> visited = new HashSet<>();

        queue.add(state);

        while (!queue.isEmpty()) {
            State current = queue.poll();
            iterations++;

            if(current.getPriorityV1() == 0){
                System.out.println("Fonded solution: " + current.getMove() + " .");
                current.printTestBoard();
                System.out.println("BFS iterations = " + iterations);
                return;
            }

            for(int k = 0; k < State.SIZE; k++) {
                State tmp;

                tmp = new State(current.getBoard(), current.getMove());
                tmp.MoveRowLeft(k);
                tmp.calculateV1();
                if(!visited.contains(tmp.hashCode())){
                    queue.add(tmp);
                    visited.add(tmp.hashCode());
                }

                tmp = new State(current.getBoard(), current.getMove());
                tmp.MoveColUp(k);
                tmp.setPriorityV1(current.getPriorityV1());
                if(!visited.contains(tmp.hashCode())){
                    queue.add(tmp);
                    visited.add(tmp.hashCode());
                }
            }
        }
    }
}
